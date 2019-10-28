package com.wentuo.crab.core.wx.model.common;

import lombok.Data;

import java.util.Date;

import static java.util.UUID.randomUUID;

/**
 * 功能描述：
 *
 * @author wangbencheng
 * @version 1.0
 * @className InterfaceRecord
 * @since 2019/10/4 18:20
 */
@Data
public class InterfaceRecord {

    private long id;

    /**
     * 用户id
     */
    private String uuid;

    /**
     * 接口
     */
    private String name;

    /**
     * 结果 0 失败, 1 成功, 2 出错
     */
    private int result = 0;

    private String note;

    private Date createTime;

    public InterfaceRecord() {
        super();
        uuid = randomUUID().toString();
        createTime = new Date();
    }

    @Override
    public String toString() {
        return "InterfaceRecord{" +
                "uuid='" + uuid  +
                ", name='" + name +
                ", result=" + result +
                ", note='" + note +
                ", createTime=" + createTime +
                '}';
    }
}
