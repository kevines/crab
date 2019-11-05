package com.wentuo.crab.modular.mini.service.wechat.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.enums.WechatLoginTypeEnum;
import com.wentuo.crab.modular.mini.entity.appuser.AppUser;
import com.wentuo.crab.modular.mini.model.param.appuser.AppUserParam;
import com.wentuo.crab.modular.mini.model.result.appuser.AppUserWx;
import com.wentuo.crab.modular.mini.service.appuser.AppUserService;
import com.wentuo.crab.modular.mini.service.wechat.LoginService;
import com.wentuo.crab.modular.mini.service.wechat.WxDecodeService;
import com.wentuo.crab.util.EntityConvertUtils;
import com.wentuo.crab.util.HttpContext;
import com.wentuo.crab.util.StringUtil;
import com.wentuo.crab.util.aes.Pkcs7Encoder;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：微信小程序授权相关服务接口实现
 * @author wangbencheng
 * @version 1.0
 * @className WxDecodeServiceImpl
 * @since 2019/8/14 21:01
 */
@Service
public class WxDecodeServiceImpl implements WxDecodeService {

    private static final Logger logger = LoggerFactory.getLogger(WxDecodeServiceImpl.class);

    @Resource
    private Environment environment;

    @Resource
    private AppUserService appUserService;

    @Resource
    private LoginService loginService;

    @Override
    public WTPageResponse<Map<String, Object>> wxDecode(String code, String iv, String encryptedData) {
        Map<String, Object> map = new HashMap<String, Object>(10);
        // 登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("errmsg", "code为空");
            map.put("msg", "解密失败");
            return new WTPageResponse<>(map);
        }
        String session_key = getSessionKey(code);
        String appid = environment.getProperty("pay.weixin.mini.appId");
        String result = Pkcs7Encoder.decrypt(appid, encryptedData, session_key, iv);
        /* {"openId":"odhm45SbvH_0kw6DxfnDqgVgScgw","nickName":"吕吕吕丶","gender":1,"language":"zh_CN","city":"","province":"","country":"Luxembourg","avatarUrl":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLFpPgsia91h3Niadnym6wW4BnzH7QKXo2vVaXv9riaqbqhqPicYdvicOWVHYHUshzicoVWzjvZ4YE9FMXQ/132","unionId":"oinwYwgyFfYxvMRL-1--B44f_nt0","watermark":{"timestamp":1552904981,"appid":"wx49e305c638e38ee2"}} */
        if (null != result && result.length() > 0) {
            map.put("status", 1);
            map.put("msg", "解密成功");
            JSONObject userInfoJSON = JSONObject.fromObject(result);
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("openId", userInfoJSON.get("openId"));
            //用户昵称
            userInfo.put("nickName", userInfoJSON.get("nickName"));
            //1时是男性，值为2时是女性，值为0时是未知
            userInfo.put("gender", userInfoJSON.get("gender"));
            //头像-下面提供方法转化到本地存储
            userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
            userInfo.put("unionId", "");
            // 开发个人账号没有权限拿到unionId留个坑
            if (userInfoJSON.has("unionId")) {
                userInfo.put("unionId", userInfoJSON.get("unionId"));
            }
            AppUserParam appUserParam = new AppUserParam();
            appUserParam.setUnionId(userInfo.get("unionId").toString());
            appUserParam.setOpenIdMini(userInfo.get("openId").toString());
            appUserParam.setName(userInfo.get("nickName").toString());
            appUserParam.setNickName(userInfo.get("nickName").toString());
            appUserParam.setSex(userInfo.get("gender").toString());
            //直接是用户的小程序头像，地址是wx的地址
            appUserParam.setPhoto(userInfoJSON.get("avatarUrl").toString());
            appUserParam.setWxPhoto(userInfoJSON.get("avatarUrl").toString());
            logger.info(JSON.toJSONString(appUserParam)+"appUserVO!");
            String LoginType = "mini";  //小程序登录方式
            AppUser appUser = appUserService.loginWeChat(WechatLoginTypeEnum.getValueByType(LoginType), appUserParam);
            AppUserWx appUserResult = EntityConvertUtils.convertAToB(appUser, AppUserWx.class);
            logger.warn("小程序登录result(如果为空则失败)" + JSON.toJSONString(appUserResult));
            if (appUserResult == null || StringUtils.isBlank(appUserResult.getUserId())) {
                return new WTPageResponse<>(500, "微信小程序登录出现错误", null);
            }
            handelWXPhoto(appUserParam.getWxPhoto(), appUserResult.getUserId(), appUserResult.getWxPhoto());   //处理微信头像
            userInfo.put("userId",appUserResult.getUserId());
            userInfo.put("phone", appUserResult.getPhone());
            userInfo.put("amount", appUserResult.getAmount());
            Map mapCan = loginService.userCanLogin(appUser,"");
            String token = mapCan.get("token").toString();
            logger.info("小程序userid---" + appUserResult.getUserId() + "/name---" + appUserResult.getName() + "/token---" + token);
            map.put("token", token);
            appUserResult.setBusiness(mapCan.get("business").toString());
            appUserResult.setTip(mapCan.get("tip").toString());
            appUserResult.setPhoto(userInfo.get("avatarUrl").toString());
            appUserResult.setWxPhoto(userInfo.get("avatarUrl").toString());
            map.put("userInfo", appUserResult);
            return new WTPageResponse<>(200, "微信小程序登录成功", map);
        }
        logger.error("解密失败：", JSON.toJSONString(result));
        map.put("status", 0);
        map.put("msg", "解密失败");
        return new WTPageResponse<>(map);
    }

    /**
     * 功能描述: 微信用户授权手机号
     * @author wangbencheng
     * @since 2019/8/14 23:46
     * @param code
     * @param iv
     * @param encryptedData
     * @return
     */
    @Override
    public WTPageResponse<String> wxDecodePhone(String code, String iv, String encryptedData) {
        String userId = HttpContext.getUserId();
        String phone = "";
        boolean flag = false;
        Map map = new HashMap();
        String session_key = getSessionKey(code);
        String appid = environment.getProperty("pay.weixin.mini.appId");
        String result1 = Pkcs7Encoder.decrypt(appid, encryptedData, session_key, iv);
        if (null != result1 && result1.length() > 0) {
            phone = JSONObject.fromObject(result1).get("phoneNumber").toString();
            if (StringUtil.isNotEmpty(userId)) {
                AppUser appUser = appUserService.queryUserByUserId(userId);
                appUser.setPhone(phone);
                UpdateWrapper<AppUser> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().eq(AppUser::getUserId, userId);
                boolean update = appUserService.update(appUser, updateWrapper);
                if (update) {
                    flag = true;
                }
            }
        }
        map.put("phone", phone);
        map.put("status", flag);   //表示获取成功更新用户绑定的手机号
        logger.info(JSON.toJSONString(result1) + "/" + phone);
        if (StringUtils.isBlank(phone)) {
            return new WTPageResponse(WTPageResponse.FAIL, "获取失败", "");
        }
        return new WTPageResponse(WTPageResponse.SUCCESS, "获取成功", map);
    }


    /**
     * 获取session_key
     * @param code
     * @return
     */
    private String getSessionKey(String code) {
        // 静态配置文件中获取
        String appid = environment.getProperty("pay.weixin.mini.appId");
        String secret = environment.getProperty("pay.weixin.mini.appSecret");
        String grantType = "authorization_code";
        // 请求参数
        String params = "appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=" + grantType;
        // 发送请求
        String sr = HttpContext.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        // 解析相应内容（转换成json对象）
        JSONObject json = JSONObject.fromObject(sr);
        String session_key = "";
        if (json.has("errcode")) {
            String errcode = json.get("errcode").toString();
            String errmsg = json.get("errmsg").toString();
            logger.error("得到session_key时解密失败" + errcode + ":" + errmsg);
        }
        try {
            // 获取会话密钥（session_key）
            session_key = json.get("session_key").toString();
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return session_key;
    }

    /**
     * 微信头像处理
     *
     * @param WxPhoto   微信的头像
     * @param userId    用户id
     * @param WxPhotoDB 在数据库中的微信头像
     */
    private void handelWXPhoto(String WxPhoto, String userId, String WxPhotoDB) {
        // 微信头像的处理。
        String resultPhoto = WxPhotoDB;
        String getUserWxPhoto = resultPhoto == null ? "" : resultPhoto;
        if (!getUserWxPhoto.equals(WxPhoto)) {
            logger.info(userId + "微信用户头像修改：" + (appUserService.updateWxPhoto(userId, WxPhoto)));
        }
    }

}
