package com.wentuo.crab.modular.mini.service.wechat.impl;

import com.wentuo.crab.modular.mini.entity.appuser.AppUser;
import com.wentuo.crab.modular.mini.service.appuser.AppUserRoleRefService;
import com.wentuo.crab.modular.mini.service.wechat.LoginService;
import com.wentuo.crab.modular.mini.service.wechat.TokenService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：app微信用户登录服务接口实现
 * @author wangbencheng
 * @version 1.0
 * @className LoginServiceImpl
 * @since 2019/8/14 22:07
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    /**
     * 不行
     */
    private static final String UN = "-1";
    /**
     * 可以
     */
    private static final String CAN = "0";
    /**
     * 登录
     */
    private static final String CANLOGIN = "canLogin";



    @Resource
    private AppUserRoleRefService appUserRoleRefService;

    @Resource
    private TokenService tokenService;

    @Override
    public Map<String, String> userCanLogin(AppUser appUser, String type) {
        String result = "true";
        String msg = "调用信息校对方法成功";
        String token = "";
        //不能直接登录
        String canLogin = UN;
        //微信登录没有绑定手机号
        String tip = UN;
        //是不是商家用户
        String business = UN;
        //微信登录没有绑定微信
        String weChat = UN;
        if (appUser == null) {
            result = "false";
            msg = "该用户验证数据不一致";
            return getMap(result, msg, token, canLogin, tip, business, weChat);
        }
//        String userId = appUser.getUserId();
//        QueryWrapper<AppUserRoleRef> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_id", userId);
//        List<AppUserRoleRef> appUserRoleRefs = appUserRoleRefService.list(queryWrapper);
//        if (appUserRoleRefs == null && appUserRoleRefs.isEmpty()) {
//            result = "false";
//            msg = "用户没有角色";
//            return getMap(result, msg, token, canLogin, tip, business, weChat);
//        }
//        for (AppUserRoleRef appUserRoleRef : appUserRoleRefs) {
//            if (appUserRoleRef.getRoleCode().contains("Business")) {
//                canLogin = CAN;
//                if (appUserRoleRef.getRoleCode().contains("Business")) {
//                    business = CAN;
//                }
//            }
//        }
        if (StringUtils.isNotBlank(appUser.getPhone())) {
            tip = CAN;
        }
        if (StringUtils.isNotBlank(type) && type.equals("app")) {
            if (StringUtils.isNotBlank(appUser.getOpenId()) && StringUtils.isNotBlank(appUser.getUnionId())) {
                weChat = CAN;
            }
        } else {
            if (StringUtils.isNotBlank(appUser.getUnionId())) {
                weChat = CAN;
            }
        }
        token = tokenService.getAppUserToken(appUser);  //获取用户token
        return getMap(result, msg, token, canLogin, tip, business, weChat);
    }

    /**
     * 功能描述: 返回map方法
     * @author wangbencheng
     * @since 2019/8/14 22:22
     * @param result
     * @param msg
     * @param token
     * @param canLogin
     * @param tip
     * @param business
     * @param weChat
     * @return
     */
    private Map<String, String> getMap(String result, String msg, String token, String canLogin,
                                       String tip, String business, String weChat) {
        Map<String, String> map = new HashMap<>(10);
        map.put("result", result);
        map.put("msg", msg);
        map.put("token", token);
        map.put("canLogin", canLogin);
        map.put("tip", tip);
        map.put("business", business);
        map.put("weChat", weChat);
        return map;
    }
}
