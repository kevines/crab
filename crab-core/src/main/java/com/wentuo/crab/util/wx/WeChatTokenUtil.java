package com.wentuo.crab.util.wx;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.wentuo.crab.core.wx.NoAuthorizedException;
import com.wentuo.crab.core.wx.model.common.InterfaceRecord;
import com.wentuo.crab.core.wx.model.common.WeChatUserInfo;
import com.wentuo.crab.modular.mini.entity.wechat.WeiXinToken;
import com.wentuo.crab.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 获取微信相关token
 * @author wangbencheng
 */
public class WeChatTokenUtil {
    /**
     * 获取网页授权凭证
     *
     * @param appId 公众账号的唯一标识
     * @param appSecret 公众账号的密钥
     * @param code
     * @return WeixinAouth2Token
     */
    private static Logger logger = (Logger) LoggerFactory.getLogger(WeChatTokenUtil.class);

//    public static String getOpenId(String appId, String appSecret, String code) {
//        // 拼接请求地址
//        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
//        requestUrl = requestUrl.replace("APPID", appId);
//        requestUrl = requestUrl.replace("SECRET", appSecret);
//        requestUrl = requestUrl.replace("CODE", code);
//        String openId = null;
//        // 获取网页授权凭证
//        JsonObject jsonObject = HttpUtils.httpsRequest(requestUrl, "GET", null);
//        if (null != jsonObject) {
//            try {
//                openId = jsonObject.get("openid").getAsString();
//            } catch (Exception e) {
//                logger.error("requestUrl:"+requestUrl);
//                logger.error("jsonObject:"+jsonObject);
//                int errorCode = jsonObject.get("errcode").getAsInt();
//                String errorMsg = jsonObject.get("errmsg").getAsString();
//                logger.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
//            }
//        }
//        return openId;
//    }
    
    /**
     * 通过网页授权获取Token
     */
    public static WeiXinToken getOauth2AccessToken(String appId, String appSecret, String code) {
        Logger logger = (Logger) LoggerFactory.getLogger(WeChatTokenUtil.class);
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        WeiXinToken weixin = null;
        // 获取网页授权凭证
        JsonObject jsonObject = HttpUtils.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                weixin = new WeiXinToken();
                weixin.setOpenId(jsonObject.get("openid").getAsString());
                weixin.setAccessToken(jsonObject.get("access_token").getAsString());
                weixin.setExpiresIn(jsonObject.get("expires_in").getAsInt());
                weixin.setRefreshToken(jsonObject.get("refresh_token").getAsString());
                weixin.setScope(jsonObject.get("scope").getAsString());
                logger.debug("获取accessToken {}", weixin.getAccessToken());
            } catch (Exception e) {
                logger.error("requestUrl:"+requestUrl);
                logger.error("jsonObject:"+jsonObject);
                int errorCode = jsonObject.get("errcode").getAsInt();
                String errorMsg = jsonObject.get("errmsg").getAsString();
                logger.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return weixin;
    }

    /**
     * 通过网页授权获取Token
     */
    public static void refreshOauthAccessToken(String appId, String refreshToken, WeiXinToken weiXinToken,
                                               InterfaceRecord interfaceRecord) {
        // 拼接请求地址
        String requestUrl = String.format(
                "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s",
                appId, refreshToken);
        // 获取网页授权凭证
        JsonObject jsonObject = HttpUtils.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            JsonElement temp = jsonObject.get("access_token");
            if (temp == null) {
                String errcode = jsonObject.get("errcode").getAsString();
                if ("42002".equals(errcode) || "40030".equals(errcode)) {
                    logger.debug("weixin refresh access token fail uuid {} token {}", interfaceRecord.getUuid(),
                            refreshToken);
                    throw new NoAuthorizedException();
                } else {
                    interfaceRecord.setResult(0);
                    logger.error("weixin refresh access token fail uuid {} token {}", interfaceRecord.getUuid(),
                            refreshToken);
                }
            } else {
                weiXinToken.setAccessToken(temp.getAsString());
                weiXinToken.setExpiresIn(jsonObject.get("expires_in").getAsInt());
                weiXinToken.setScope(jsonObject.get("scope").getAsString());
                weiXinToken.setCreateTime(System.currentTimeMillis());
                interfaceRecord.setResult(1);
            }
        } else {
            interfaceRecord.setResult(2);
            interfaceRecord.setNote("刷新wenxinAccessToken 连接失败");
        }
    }

    /**
     * 通过网页授权获取Token
     */
    public static void getJsAccessToken(String appId, WeiXinToken weiXinToken, InterfaceRecord interfaceRecord) {
        // 拼接请求地址
        String requestUrl = String.format(
                "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi",
                weiXinToken.getSilenceAccessToken());
        // 获取网页授权凭证
        JsonObject jsonObject = HttpUtils.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            String errcode = jsonObject.get("errcode").getAsString();
            if (!errcode.equals("0")) {
                interfaceRecord.setNote(jsonObject.get("errmsg").getAsString());
                if ("42002".equals(errcode) || "40030".equals(errcode)) {
                    logger.debug("weixin refresh access token fail uuid {} token {}", interfaceRecord.getUuid());
                    throw new NoAuthorizedException();
                } else {
                    interfaceRecord.setResult(2);
                    logger.error("weixin refresh access token fail uuid {} token {}", interfaceRecord.getUuid());
                }
            } else {
                JsonElement temp = jsonObject.get("ticket");
                weiXinToken.setJsAccessToken(temp.getAsString());
                weiXinToken.setJsExpiresIn(jsonObject.get("expires_in").getAsInt());
                weiXinToken.setJsCreateTime(System.currentTimeMillis());
                interfaceRecord.setResult(1);
            }
        } else {
            interfaceRecord.setResult(2);
            interfaceRecord.setNote("刷新wenxinAccessToken 连接失败");
        }
    }

    /**
     * 通过网页授权获取用户信息
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId      用户标识
     * @return userInfo
     */
    public static WeChatUserInfo getUserInfo(String accessToken, String openId) {
        WeChatUserInfo userInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JsonObject jsonObject = HttpUtils.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                userInfo = new WeChatUserInfo();
                // 用户的标识
                userInfo.setOpenId(jsonObject.get("openid").getAsString());
                // 昵称
                userInfo.setNickname(jsonObject.get("nickname").getAsString());
                // 性别（1是男性，2是女性，0是未知）
                userInfo.setSex(jsonObject.get("sex").getAsInt());
                // 用户所在国家
                userInfo.setCountry(jsonObject.get("country").getAsString());
                // 用户所在省份
                userInfo.setProvince(jsonObject.get("province").getAsString());
                // 用户所在城市
                userInfo.setCity(jsonObject.get("city").getAsString());
                // 用户头像
                userInfo.setHeadImgUrl(jsonObject.get("headimgurl").getAsString());
                //用户unionid
                if (jsonObject.get("unionid")!=null){
                    userInfo.setUnionid(jsonObject.get("unionid").getAsString());
                }
                // 用户特权信息
                Gson gson = new Gson();
                TypeToken<List<String>> type = new TypeToken<List<String>>() {
                };
                userInfo.setPrivilegeList(gson.fromJson(jsonObject.get("privilege"), type.getType()));
            } catch (Exception e) {
                userInfo = null;
                e.printStackTrace();
                int errorCode = jsonObject.get("errcode").getAsInt();
                String errorMsg = jsonObject.get("errmsg").getAsString();
                logger.error(String.format("获取用户信息失败 errcode:%s errmsg:%s", errorCode, errorMsg), e);
            }
        }
        return userInfo;
    }

    // 获取调用接口凭证
    public static WeiXinToken getAccessToken(String appId, String appsecret) {
        // 凭证获取（GET）
        String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        WeiXinToken token = null;
        String requestUrl = token_url.replace("APPID", appId).replace("APPSECRET", appsecret);
        // 发起GET请求获取凭证
        JsonObject jsonObject = HttpUtils.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                token = new WeiXinToken();
                token.setAccessToken(jsonObject.get("access_token").getAsString());
                token.setExpiresIn(jsonObject.get("expires_in").getAsInt());
            } catch (JsonSyntaxException e) {
                token = null;
                // 获取token失败
                logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.get("errcode").getAsInt(),
                        jsonObject.get("errmsg").getAsString());
            }
        }
        return token;
    }
}
