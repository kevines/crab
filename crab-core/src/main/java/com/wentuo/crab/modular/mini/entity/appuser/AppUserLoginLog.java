package com.wentuo.crab.modular.mini.entity.appuser;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName AppUserLoginLog
 * @Description app用户登录记录表
 * @Author Wangbencheng
 * @Date 2019/8/14 15:50
 * @Version 1.0
 **/
@TableName("app_user_login_log")
@Data
public class AppUserLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String userId;

    private String deviceNumber;

    private String loginIp;

    private String loginType;

    private Date loginTime;

    @Override
    public String toString() {
        return "AppUserLoginLog{" +
                "id=" + id +
                ", userId='" + userId +
                ", deviceNumber='" + deviceNumber +
                ", loginIp='" + loginIp +
                ", loginType='" + loginType +
                ", loginTime=" + loginTime +
                '}';
    }

}
