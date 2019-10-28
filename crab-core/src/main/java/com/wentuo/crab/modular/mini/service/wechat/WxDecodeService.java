package com.wentuo.crab.modular.mini.service.wechat;

import com.wentuo.crab.core.common.page.WTResponse;

import java.util.Map;

/**
 * 功能描述: 微信小程序授权相关服务接口
 * @author wangbencheng
 * @since 2019/8/14 20:59
 */
public interface WxDecodeService {

    /**
     * 功能描述: 微信小程序授权
     * @author wangbencheng
     * @since 2019/8/14 20:59
     * @param code
     * @param iv
     * @param encryptedData
     * @return com.wentuo.bcs.core.common.page.WTResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    WTResponse<Map<String, Object>> wxDecode(String code, String iv, String encryptedData);

    /**
     * 功能描述: 微信小程序授权手机号码
     * @author wangbencheng
     * @since 2019/8/14 21:00
     * @param code
     * @param iv
     * @param encryptedData
     * @return com.wentuo.bcs.core.common.page.WTResponse<java.lang.String>
     */
    WTResponse<String> wxDecodePhone(String code, String iv, String encryptedData);
}