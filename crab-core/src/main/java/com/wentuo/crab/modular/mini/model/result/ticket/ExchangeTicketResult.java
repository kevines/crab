package com.wentuo.crab.modular.mini.model.result.ticket;

import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicketSpecification;
import lombok.Data;
import java.util.Date;
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
public class ExchangeTicketResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    private Long id;

    /**
     * 兑换券规格编号
     */
    private Long specificationId;

    /**
     * 规格内容
     */
    private String specification;

    /**
     * 券名称
     */
    private String ticketName;

    /**
     * 物品名称
     */
    private String goodName;

    /**
     * 物品图片
     */
    private String goodPic;

    /**
     * 物品数量
     */
    private Integer goodNum;

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

}
