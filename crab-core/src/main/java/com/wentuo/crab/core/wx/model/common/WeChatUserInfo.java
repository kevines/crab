package com.wentuo.crab.core.wx.model.common;

import lombok.Data;

import java.util.List;

/**
 * 功能描述：
 *
 * @author wangbencheng
 * @version 1.0
 * @className WeiXinUserInfo
 * @since 2019/10/4 18:26
 */
@Data
public class WeChatUserInfo {

    // 用户标识
    private String openId;
    // 用户昵称
    private String nickname;
    // 性别（1是男性，2是女性，0是未知）
    private int sex;
    // 国家
    private String country;
    // 省份
    private String province;
    // 城市
    private String city;
    // 用户头像链接
    private String headImgUrl;

    private String unionid;
    // 用户特权信息
    private List<String> privilegeList;

}
