package com.wentuo.crab.appm.api.mini.appuser;

import cn.stylefeng.roses.core.base.controller.BaseController;
import javax.annotation.Resource;

import com.wentuo.crab.core.common.annotion.NoPermission;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.modular.mini.entity.appuser.AppUserAddress;
import com.wentuo.crab.modular.mini.model.param.appuser.AppUserAddressParam;
import com.wentuo.crab.modular.mini.service.appuser.AppUserAddressService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户收获地址表控制器
 *
 * @author wangbencheng
 * @Date 2019-10-31 19:06:54
 */
@RestController
@RequestMapping("/mini/app/user/address")
public class AppUserAddressController extends BaseController {

    @Resource
    private AppUserAddressService appUserAddressService;


    /**
     * 新增收获地址
     *
     * @author wangbencheng
     * @Date 2019-10-31
     */
    @PostMapping("/add.do")
    @NoPermission
    public WTResponse addItem(AppUserAddressParam appUserAddressParam) {
        return this.appUserAddressService.add(appUserAddressParam);
    }

    /**
     * 编辑用户地址信息
     * @param param
     * @return
     */
    @PostMapping("/edit.do")
    @NoPermission
    public WTResponse editAddress(AppUserAddressParam param) {
        return this.appUserAddressService.update(param);
    }
    /**
     * 删除收获地址
     *
     * @author wangbencheng
     * @Date 2019-10-31
     */
    @PostMapping("/delete.do")
    @NoPermission
    public WTResponse delete(AppUserAddressParam appUserAddressParam) {
        return this.appUserAddressService.delete(appUserAddressParam);
    }

    /**
     * 查看收获地址详情
     *
     * @author wangbencheng
     * @Date 2019-10-31
     */
    @PostMapping("/detail.do")
    @NoPermission
    public WTResponse detail(AppUserAddressParam appUserAddressParam) {
        return WTResponse.success(this.appUserAddressService.findBySpec(appUserAddressParam));
    }

    /**
     * 查询收货地址列表
     *
     * @author wangbencheng
     * @Date 2019-10-31
     */
    @RequestMapping("/list.do")
    @NoPermission
    public WTResponse list(AppUserAddressParam appUserAddressParam) {
        return WTResponse.success(this.appUserAddressService.findListBySpec(appUserAddressParam));
    }

}


