package com.wentuo.crab.modular.mini.model.result.appuser;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author yuwenbo
 * @since 2019-03-06
 */
@Data
public class AppUserResult implements Serializable {

    private static final long serialVersionUID = 1254134634634L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户业务主键id
     */
    private String userId;

    /**
     * 用户登录名
     */
    private String name;

    /**
     * 昵称
     */
    private String nickName;

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
    private Date birthday;

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

    /**
     * 绑定的邮箱账号
     */
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
     * 0-未知 1-普通用户账号 2-总运营账号 3-县运营账号
     */
    private String userType;

    /**
     * 用来通知用户获奖信息模版消息FormId;
     */
    private String formId;

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
     * 登录类型
     */
    private String loginType;

    /**
     * 登录token过期时间
     */
    private Date tokenExpireTime;

    /**
     * 创建时间
     */
    public Date gmtCreated;

    /**
     * 修改时间
     */
    public Date gmtModified;

    /**
     * 创建人
     */
    public String creator;

    /**
     * 修改人
     */
    public String modifier;

    /**
     * 是否删除
     */
    public Boolean isDeleted;

}
