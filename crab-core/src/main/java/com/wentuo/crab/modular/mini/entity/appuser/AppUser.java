package com.wentuo.crab.modular.mini.entity.appuser;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("app_user")
@Data
public class AppUser implements Serializable {

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableField(exist = false)
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
     * 用户账号类型
     */
    private String userType;

    /**
     * 登录密码
     */
    @JSONField(serialize = false)
    private String pwd;

    /**
     * 支付密码
     */
    @JSONField(serialize = false)
    private String payPwd;

    /**
     * 用户地区
     */
    private String userDistrictCode;

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
     * 是否有考试权限
     */
    private Boolean isExam;

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
    /**
     * 登录类型
     */
    @TableField(exist = false)
    private String loginType;

    /**
     * 登录token过期时间
     */
    @TableField(exist = false)
    private Date tokenExpireTime;

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", userId=" + userId +
                ", name=" + name +
                ", nickName=" + nickName +
                ", realName=" + realName +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", photo=" + photo +
                ", wxPhoto=" + wxPhoto +
                ", phone=" + phone +
                ", email=" + email +
                ", userType=" + userType +
                ", pwd=" + pwd +
                ", payPwd=" + payPwd +
                ", userDistrictCode=" + userDistrictCode +
                ", formId=" + formId +
                ", openId=" + openId +
                ", openIdH5=" + openIdH5 +
                ", openIdMini=" + openIdMini +
                ", unionId=" + unionId +
                ", is_exam=" + isExam +
                ", creator=" + creator +
                ", modifier=" + modifier +
                ", gmtCreated=" + gmtCreated +
                ", gmtModified=" + gmtModified +
                ", isDeleted=" + isDeleted +
                "}";
    }

}
