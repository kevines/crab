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
package com.wentuo.crab.core.common.page;

import cn.stylefeng.roses.core.util.HttpContext;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wentuo.crab.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Layui Table默认的分页参数创建
 *
 * @author fengshuonan
 * @date 2017-04-05 22:25
 */
public class WTPageFactory {

    /**
     * 获取layui table的分页参数
     *
     * @author fengshuonan
     * @Date 2019/1/25 22:13
     */
    public static Page defaultPage() {
        HttpServletRequest request = HttpContext.getRequest();

        String limits = request.getParameter("limit");
        String pages = request.getParameter("page");

        if(StringUtil.isEmpty(limits)){
            limits = "10";
        }

        if(StringUtil.isEmpty(pages)){
            pages = "1";
        }
        //每页多少条数据
        int limit = Integer.valueOf(limits);

        //第几页
        int page = Integer.valueOf(pages);

        return new Page(page, limit);
    }

    /**
     * 创建layui能识别的分页响应参数
     *
     * @author fengshuonan
     * @Date 2019/1/25 22:14
     */
    public static WTPageResponse createPageInfo(IPage page) {
        WTPageResponse result = new WTPageResponse();
        result.setCount(page.getTotal());
        result.setData(page.getRecords());
        if(page.getRecords() == null){
            result.setData(new ArrayList());
        }
        return result;
    }

    /**
     * 创建layui能识别的分页响应参数 ext增加拓展
     */
    public static WTPageResponse createPageInfoWithExt(IPage page, Object ext) {
        WTPageResponse result = new WTPageResponse();
        result.setCount(page.getTotal());
        result.setData(page.getRecords());
        result.setExt(ext);
        return result;
    }


    /**
     * 创建layui能识别的分页响应参数
     *
     * @author fengshuonan
     * @Date 2019/1/25 22:14
     */
    public static <T> WTPageResponse createPageByList(List<T> list, long total) {
        WTPageResponse result = new WTPageResponse();
        result.setCount(total);
        result.setData(list);
        return result;
    }
}
