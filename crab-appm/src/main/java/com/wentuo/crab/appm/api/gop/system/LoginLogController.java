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
package com.wentuo.crab.appm.api.gop.system;


import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.wentuo.crab.core.common.annotion.BusinessLog;
import com.wentuo.crab.core.common.annotion.Permission;
import com.wentuo.crab.core.common.constant.Const;
import com.wentuo.crab.core.common.page.WTPageFactory;
import com.wentuo.crab.modular.system.service.LoginLogService;
import com.wentuo.crab.modular.system.warpper.LogWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 日志管理的控制器
 *
 * @author fengshuonan
 * @Date 2017年4月5日 19:45:36
 */
@Controller
@RequestMapping("/loginLog")
public class LoginLogController extends BaseController {

    private static String PREFIX = "/modular/system/log/";

    @Resource
    private LoginLogService loginLogService;

    /**
     * 跳转到日志管理的首页
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:51 PM
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "login_log.html";
    }

    /**
     * 查询登录日志列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:51 PM
     */
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime,
                       @RequestParam(required = false) String endTime,
                       @RequestParam(required = false) String logName) {

        //获取分页参数
        Page page = WTPageFactory.defaultPage();

        //根据条件查询日志
        List<Map<String, Object>> result = loginLogService.getLoginLogs(page, beginTime, endTime, logName);
        page.setRecords(new LogWrapper(result).wrap());

        return WTPageFactory.createPageInfo(page);
    }

    /**
     * 清空日志
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:51 PM
     */
    @BusinessLog("清空登录日志")
    @RequestMapping("/delLoginLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        SqlRunner.db().delete("delete from sys_login_log");
        return SUCCESS_TIP;
    }
}
