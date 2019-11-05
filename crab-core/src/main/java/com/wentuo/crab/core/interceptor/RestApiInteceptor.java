/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wentuo.crab.core.interceptor;

import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.util.RenderUtil;
import com.wentuo.crab.core.common.annotion.AvoidRepeatCommit;
import com.wentuo.crab.core.common.annotion.NoPermission;
import com.wentuo.crab.core.common.constant.RedisKeys;
import com.wentuo.crab.core.common.exception.BizExceptionEnum;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.util.RedisUtil;
import com.wentuo.crab.util.HttpContext;
import io.jsonwebtoken.JwtException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Rest Api接口鉴权
 *
 * @author stylefeng
 * @Date 2018/7/20 23:11
 */
public class RestApiInteceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof org.springframework.web.servlet.resource.ResourceHttpRequestHandler) {
            return true;
        }
        return check(request, response,
                 handler);
    }

    private boolean check(HttpServletRequest request, HttpServletResponse response,
                          Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        NoPermission noPermission = handlerMethod.getMethodAnnotation(NoPermission.class);

        //是否重复提交
        String url  =  HttpContext.getRequest().getRequestURL().toString();
        AvoidRepeatCommit avoidRepeatCommit = handlerMethod.getMethodAnnotation(AvoidRepeatCommit.class);
        if (avoidRepeatCommit != null || url.contains("add")) {
            String userId = HttpContext.getUserId();
            //核保防止同一时间多次点击
            String key = RedisKeys.repeatCommit + userId;
            String flag = RedisUtil.get(key);
            if ("1".equals(flag)) {
                RenderUtil.renderJson(response, new ErrorResponseData(BizExceptionEnum.ERROR.getCode(), "操作频繁,请稍候再试"));
                return false;
            }
            RedisUtil.setForTimeS(key, "2", 1);
        }

        if (noPermission!=null || (url.indexOf("/error") > -1 )) {
            return true;
        }
        try {
            //token验证
            boolean flag = HttpContext.isTokenExpired();

            if (flag) {
                RenderUtil.renderJson(response, new WTPageResponse(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_EXPIRED.getMessage()));
                return false;
            }
        } catch (JwtException e) {
            //有异常就是token解析失败
            RenderUtil.renderJson(response, new ErrorResponseData(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
            return false;
        }
        return true;
    }

}
