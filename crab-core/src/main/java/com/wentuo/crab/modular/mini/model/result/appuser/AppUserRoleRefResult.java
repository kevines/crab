package com.wentuo.crab.modular.mini.model.result.appuser;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述：用户角色记录表封装返回实体类
 * @author wangbencheng
 * @version 1.0
 * @className AppUserRoleRefParam
 * @since 2019/8/14 22:26
 */
@Data
public class AppUserRoleRefResult implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
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

    /**
     * 1是，0否
     */
    private Boolean isDeleted;
}
