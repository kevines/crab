package com.wentuo.crab.modular.mini.entity.appuser;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 用户收获地址表
 * </p>
 *
 * @author wangbencheng
 * @since 2019-10-31
 */
@Data
@TableName("app_user_address")
public class AppUserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 收货人姓名
     */
    private String userName;

    /**
     * 行政区域表的省ID
     */
    private Integer provinceId;

    /**
     * 行政区域表的市ID
     */
    private Integer cityId;

    /**
     * 行政区域表的区县ID
     */
    private Integer areaId;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 是否是默认地址
     */
    private Boolean isDefault;

    /**
     * 创建日期
     */
    private Date gmtCreated;

    /**
     * 修改日期
     */
    private Date gmtModified;


    @Override
    public String toString() {
        return "AppUserAddress{" +
        "id=" + id +
        ", userId=" + userId +
        ", userName=" + userName +
        ", provinceId=" + provinceId +
        ", cityId=" + cityId +
        ", areaId=" + areaId +
        ", address=" + address +
        ", mobile=" + mobile +
        ", isDefault=" + isDefault +
        ", gmtCreated=" + gmtCreated +
        ", gmtModified=" + gmtModified +
        "}";
    }
}
