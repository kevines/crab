package com.wentuo.crab.modular.mini.service.wechat.impl;

import com.alibaba.fastjson.JSON;
import com.wentuo.crab.core.common.constant.RedisKeys;
import com.wentuo.crab.core.util.RedisUtil;
import com.wentuo.crab.modular.mini.entity.appuser.AppUser;
import com.wentuo.crab.modular.mini.entity.appuser.AppUserLoginLog;
import com.wentuo.crab.modular.mini.service.appuser.AppUserLoginLogService;
import com.wentuo.crab.modular.mini.service.wechat.TokenService;
import com.wentuo.crab.util.Constant;
import com.wentuo.crab.util.DateUtils;
import com.wentuo.crab.util.HttpContext;
import com.wentuo.crab.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * 功能描述：
 *
 * @author wangbencheng
 * @version 1.0.0
 * @claaName TokenServiceImpl
 * @since 2019/10/27 20:48
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private AppUserLoginLogService appUserLoginLogService;

    @Override
    public String getAppUserToken(AppUser appUser) {
        if (StringUtil.isEmpty(appUser.getLoginType())) {
            appUser.setLoginType(Constant.APP);
            AppUserLoginLog appUserLoginLog = new AppUserLoginLog();
            appUserLoginLog.setUserId(appUser.getUserId());
            appUserLoginLog.setLoginIp(HttpContext.getIp());
            appUserLoginLog.setLoginTime(new Date());
            appUserLoginLogService.add(appUserLoginLog);
        }
        String hashKey = RedisKeys.user.getKey(Constant.TOKEN);
        String token = UUID.randomUUID().toString();
        appUser.setTokenExpireTime(DateUtils.getBackDay(-1));
        RedisUtil.put(hashKey, token, JSON.toJSONString(appUser));
        return token;
    }
}
