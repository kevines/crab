package com.wentuo.crab.modular.mini.service.wechat;


import com.wentuo.crab.modular.mini.entity.appuser.AppUser;

import java.util.Map;

/**
 * 功能描述: app用户登录服务接口
 * @author wangbencheng
 * @since 2019/8/14 22:06
 */
public interface LoginService {

    /**
     * 功能描述: 判断用户是否可以登录
     * @author wangbencheng
     * @since 2019/8/14 22:20
     * @param appUser 用户实体类
     * @param type 登录类型除了app就是微信
     * @return
     */
    Map<String, String> userCanLogin(AppUser appUser, String type);

}
