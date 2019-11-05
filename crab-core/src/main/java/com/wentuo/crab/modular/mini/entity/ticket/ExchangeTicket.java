package com.wentuo.crab.modular.mini.entity.ticket;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 兑换券详情列表
 * </p>
 *
 * @author wangbencheng
 * @since 2019-10-31
 */
@Data
@TableName("exchange_ticket")
public class ExchangeTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 兑换券规格编号
     */
    private Long specificationId;

    /**
     * 兑换码
     */
    private String ticketNo;

    /**
     * 是否兑换
     */
    private Boolean isExchange;

    /**
     * 是否发货
     */
    private Boolean isSend;

    /**
     * 兑换日期
     */
    private Date exchangeDate;

    /**
     * 发货日期
     */
    private Date sendDate;


    @Override
    public String toString() {
        return "ExchangeTicket{" +
        "id=" + id +
        ", specificationId=" + specificationId +
        ", ticketNo=" + ticketNo +
        ", isExchange=" + isExchange +
        ", isSend=" + isSend +
        ", exchangeDate=" + exchangeDate +
        ", sendDate=" + sendDate +
        "}";
    }
}
