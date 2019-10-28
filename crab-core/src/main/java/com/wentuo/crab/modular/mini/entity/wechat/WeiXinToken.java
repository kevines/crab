package com.wentuo.crab.modular.mini.entity.wechat;

import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述：
 *
 * @author wangbencheng
 * @version 1.0
 * @className WeiXinToken
 * @since 2019/10/4 18:14
 */
@Data
public class WeiXinToken implements Serializable {

    /**
     * 主键编号
     */
    private long id;

    private String UserId;

    /**
     * 网页授权接口调用凭证
     */
    private String accessToken;

    /**
     * access_token是公众号的全局唯一接口调用凭据
     */
    private String globalAccessToken;

    /**
     * 凭证有效时长
     */
    private int expiresIn;

    private long createTime;

    /**
     * 网页授权接口调用凭证
     */
    private String jsAccessToken;

    /**
     * 网页授权接口调用凭证有效时长
     */
    private int jsExpiresIn;

    private long jsCreateTime;

    /**
     * 静默授权接口调用凭证
     */
    private String silenceAccessToken;

    /**
     * 静默授权接口调用凭证有效时长
     */
    private int silenceExpiresIn;

    private long silenceCreateTime;

    /**
     * 用于刷新凭证
     */
    private String refreshToken;

    /**
     * 用户标识
     */
    private String openId;

    /**
     * 用户授权作用域
     */
    private String scope;

}
