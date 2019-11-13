package com.wentuo.crab.modular.mini.entity.ticket;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 兑换交易记录表(发货用)
 * </p>
 *
 * @author wangbencheng
 * @since 2019-10-31
 */
@Data
@TableName("exchange_ticket_record")
public class ExchangeTicketRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 兑换券编号
     */
    private String ticketNo;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 联系人姓名
     */
    private String userName;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 用户地址信息
     */
    private String address;

    /**
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 物流公司名称
     */
    private String logisticsName;

    /**
     * 兑换日期
     */
    private Date gmtCreated;


    @Override
    public String toString() {
        return "ExchangeTicketRecord{" +
        "id=" + id +
        ", ticketNo=" + ticketNo +
        ", userId=" + userId +
        ", userName=" + userName +
        ", mobile=" + mobile +
        ", logisticsNo=" + logisticsNo +
        ", logisticsName=" + logisticsName +
        ", address=" + address +
        ", gmtCreated=" + gmtCreated +
        "}";
    }
}
