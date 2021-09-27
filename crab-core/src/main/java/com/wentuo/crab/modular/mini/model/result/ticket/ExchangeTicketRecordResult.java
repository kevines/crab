package com.wentuo.crab.modular.mini.model.result.ticket;

import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicket;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicketSpecification;
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
public class ExchangeTicketRecordResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    private Long id;

    /**
     * 兑换券编号
     */
    private String ticketNo;

    /**
     * 卡号('NO.' + id的9位0充)
     */
    private String cardNo;

    /**
     * 兑换券属性相关信息
     */
    private ExchangeTicketSpecificationResult exchangeTicketSpecificationResult;

    /**
     * 兑换券信息
     */
    private ExchangeTicket exchangeTicket;

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
     * 物流单号
     */
    private String logisticsNo;

    /**
     * 物流公司名称
     */
    private String logisticsName;

    /**
     * 用户地址信息
     */
    private String address;

    /**
     * 兑换日期
     */
    private Date gmtCreated;

}
