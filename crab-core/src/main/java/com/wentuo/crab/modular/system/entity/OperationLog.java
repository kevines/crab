package com.wentuo.crab.modular.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@TableName("sys_operation_log")
@Data
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "OPERATION_LOG_ID", type = IdType.ID_WORKER)
    private Long operationLogId;
    /**
     * 日志类型(字典)
     */
    @TableField("LOG_TYPE")
    private String logType;
    /**
     * 日志名称
     */
    @TableField("LOG_NAME")
    private String logName;

    /**
     * 服务器端口
     */
    @TableField("PORT")
    private String port;
    /**
    /**
     * 用户id
     */
    @TableField("USER_ID")
    private Object userId;
    /**
     * 类名称
     */
    @TableField("CLASS_NAME")
    private String className;
    /**
     * 方法名称
     */
    @TableField("METHOD")
    private String method;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 是否成功(字典)
     */
    @TableField("SUCCEED")
    private String succeed;
    /**
     * 备注
     */
    @TableField("MESSAGE")
    private String message;

}
