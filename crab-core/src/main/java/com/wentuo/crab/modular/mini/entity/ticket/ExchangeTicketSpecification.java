package com.wentuo.crab.modular.mini.entity.ticket;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 兑换券规格表
 * </p>
 *
 * @author wangbencheng
 * @since 2019-10-31
 */
@Data
@TableName("exchange_ticket_specification")
public class ExchangeTicketSpecification implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 蟹券规格编号
     */
    private String specificationId;

    /**
     * 兑换券名称
     */
    private String ticketName;

    /**
     * 对应螃蟹的规格详情
     */
    private String specification;

    /**
     * 兑换券备注信息
     */
    private String remark;

    /**
     * 兑换券库存
     */
    private Integer stock;

    /**
     * 截止日期
     */
    private Date expiryDate;

    /**
     * 创建日期
     */
    private Date gmtCreated;

    /**
     * 商品名称
     */
    private String goodName;

    /**
     * 商品图片
     */
    private String goodPic;

    /**
     * 商品个数
     */
    private Integer goodNum;

    @Override
    public String toString() {
        return "ExchangeTicketSpecification{" +
        "id=" + id +
        ", ticketName=" + ticketName +
        ", specification=" + specification +
        ", remark=" + remark +
        ", stock=" + stock +
        ", expiryDate=" + expiryDate +
        ", gmtCreated=" + gmtCreated +
        ", goodName=" + goodName +
        ", goodPic=" + goodPic +
        ", goodNum=" + goodNum +
        "}";
    }
}
