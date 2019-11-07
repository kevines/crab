package com.wentuo.crab.modular.mini.model.param.appuser;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
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
public class AppUserParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户业务编号
     */
    private String userId;
    /**
     * 系统角色
     */
    private String roleId;
    private String roleNames;

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
     * M-男性 W-女性 N-未知
     */
    private String sex;

    /**
     * 出生日期
     */
    private Date birthday;
    private Date birthBeginTime;
    private Date birthEndTime;

    /**
     * 用户头像
     */
    private String photo;

    /**
     * 微信头像
     */
    private String wxPhoto;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户绑定的邮箱
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
    private String userDistrictName;

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
     * 创建时间
     */
    private Date gmtCreated;
    private Date gmtStartDate;
    private Date gmtEndDate;
    /**
     * 修改时间
     */
    private Date gmtModified;
    private Date gmtBeginTime;
    private Date gmtEndTime;
    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 是否删除
     */
    private Boolean isDeleted;


    @Override
    public String checkParam() {
        return null;
    }
}
