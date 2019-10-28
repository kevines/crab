package com.wentuo.crab.modular.mini.service.wechat;

import com.wentuo.crab.modular.mini.entity.appuser.AppUser;

public interface TokenService {

    String getAppUserToken(AppUser appUser);
}
