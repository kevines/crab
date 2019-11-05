package com.wentuo.crab.modular.mini.model.param.appuser;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
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
public class AppUserAddressParam implements Serializable, BaseValidatingParam {

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
    public String checkParam() {
        return null;
    }

}
