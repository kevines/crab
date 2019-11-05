package com.wentuo.crab.modular.mini.model.result.ticket;

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
