package com.wentuo.crab.modular.mini.entity.appuser;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述：用户角色表
 * @author wangbencheng
 * @version 1.0
 * @className AppUserRoleRef
 * @since 2019/8/14 22:24
 */
@TableName("app_user_role_ref")
@Data
public class AppUserRoleRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户业务id
     */
    private String userId;

    /**
     * 用户角色编码
     */
    private String roleCode;

    /**
     * 创建时间
     */
    private Date gmtCreated;
}
