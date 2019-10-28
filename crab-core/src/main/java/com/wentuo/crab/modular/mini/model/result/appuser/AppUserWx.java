package com.wentuo.crab.modular.mini.model.result.appuser;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能描述：
 *
 * @author wangbencheng
 * @version 1.0
 * @className AppUserWx
 * @since 2019/8/14 21:06
 */
@Data
public class AppUserWx {

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户业务主键id
     */
    private String userId;

    /**
     * 所属上级id（属于该上级的粉丝）
     */
    private String masterId;

    /**
     * 用户登录名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 店铺id
     */
    private String shopId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * M-男性 W-女性 N-未知 1男2女(其他数)未知
     */
    private String sex;

    /**
     * 出生日期
     */
    private Date birthdate;

    /**
     * 用户头像
     */
    private String photo;

    /**
     * 用户角色
     */
    private String role;

    /**
     * 微信头像
     */
    private String wxPhoto;

    /**
     * 用户手机号
     */
    private String phone;

    private String email;

    private String qq;
    private String wx;

    /**
     * 登录密码
     */
    private String pwd;

    /**
     * 支付密码
     */
    private String payPwd;

    /**
     * 用户地区
     */
    private String userDistrictCode;

    /**
     * APP内openId(app)
     */
    private String openId;

    /**
     * 公众号openId(h5)
     */
    private String openIdH5;

    /**
     * 小程序openId(mini)
     */
    private String openIdMini;

    /**
     * unionId
     */
    private String unionId;

    /**
     * 0、未知 1、普通用户账号 2、管理员账号
     */
    private String userType;

    /**
     * 1是，0否
     */
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    private Date gmtCreated;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 登录次数
     */
    private Integer loginNum;

    /**
     * 余额
     */
    private BigDecimal amount;
    /**
     * 工分
     */
    private Long workPoints;

    private String loginType;

    private Date tokenExpireTime;

    /**
     * 是不是合伙人 -1/0
     */
    private String partner;
    /**
     * 是不是商家 -1/0
     */
    private String business;
    /**
     * 有没有手机号 -1/0
     */
    private String tip;
}
