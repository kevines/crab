package com.wentuo.crab.appm.api.gop.appuser;


import cn.stylefeng.roses.core.base.controller.BaseController;
import com.wentuo.crab.core.common.annotion.NoPermission;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.modular.mini.model.param.appuser.AppUserParam;
import com.wentuo.crab.modular.mini.model.result.appuser.AppUserResult;
import com.wentuo.crab.modular.mini.service.appuser.AppUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangbencheng
 * @date 2019/8/14 3:04
 */
@RestController
@RequestMapping("/appUser")
public class AppUserController extends BaseController {

    @Resource
    private AppUserService appUserService;

    /**
     * 功能描述: 查看详情
     * @author wangbencheng
     * @since 2019/8/16 15:27
     * @param param
     * @return
     */
    @NoPermission
    @ResponseBody
    @PostMapping("/detail.do")
    public AppUserResult queryAppUserInfo(AppUserParam param) {
        return appUserService.findBySpec(param);
    }

    /**
     * 功能描述: 查询分页列表
     * @author wangbencheng
     * @since 2019/8/16 15:27
     * @param param
     * @return
     */
    @NoPermission
    @PostMapping("/list.do")
    @ResponseBody
    public WTPageResponse getAppUserList(AppUserParam param) {
        return appUserService.findPageBySpec(param);
    }



}
