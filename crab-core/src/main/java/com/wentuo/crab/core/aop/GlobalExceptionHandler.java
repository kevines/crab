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
package com.wentuo.crab.core.aop;


import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSONObject;
import com.wentuo.crab.core.common.exception.BizExceptionEnum;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.log.LogManager;
import com.wentuo.crab.core.log.factory.LogTaskFactory;
import com.wentuo.crab.util.BizException;
import com.wentuo.crab.util.DingtalkRootUtil;
import com.wentuo.crab.util.HttpContext;
import com.wentuo.crab.util.StaticConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

import static cn.stylefeng.roses.core.util.HttpContext.getRequest;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DingtalkRootUtil dingtalkRootUtil;

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public WTPageResponse bussiness(ServiceException e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(HttpContext.getUserId(), e));
        getRequest().setAttribute("tip", e.getMessage());
        log.error("业务异常:", e);
        sendException2Ding(e);
        return new WTPageResponse(e.getCode(), e.getMessage());
    }

    /**
     * Biz业务异常
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public WTPageResponse bizException(Exception e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(HttpContext.getUserId(), e));
        log.error("业务异常:", e);
        sendException2Ding(e);
        return new WTPageResponse<>(BizExceptionEnum.ERROR.getCode(), e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData notFount(RuntimeException e) {
        getRequest().setAttribute("tip", "服务器未知运行时异常");
        log.error("运行时异常:", e);
        sendException2Ding(e);
        return new ErrorResponseData(BizExceptionEnum.SERVER_ERROR.getCode(), BizExceptionEnum.SERVER_ERROR.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponseData exception(RuntimeException e) {
        getRequest().setAttribute("tip", "运行时异常");
        log.error("运行时异常:", e);
        sendException2Ding(e);
        return new ErrorResponseData(BizExceptionEnum.SERVER_ERROR.getCode(), BizExceptionEnum.SERVER_ERROR.getMessage());
    }

    /**
     * 发送 钉钉异常提醒
     *
     * @param e
     */
    private void sendException2Ding(Exception e) {
        String env = StaticConfigUtil.getEnv();
        String path = env.substring(0, env.indexOf(",") == -1 ? env.length() : env.indexOf(","));
        path = path.equals("local") ? "local:" + HttpContext.getIp() : path;
        //请求参数
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String content = path + " " + e.getMessage() + "\n" + "请求参数 " + JSONObject.toJSONString(request.getParameterMap()) + "\n" + getExceptionText(e);
        dingtalkRootUtil.send("1", content , "", false);
    }

    /**
     * e.printStackTrace() to String
     *
     * @param e
     * @return
     */
    public static String getExceptionText(Exception e) {
        String text = "";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        text = sw.toString();
        text = text.replaceAll("\r", "");
        text = text.replaceAll("\n", "|");
        text = text.replaceAll("\t", "");
        text = text.replaceAll("`","-");
        text = text.replaceAll("'","-");
        return text;
    }
}
