package com.wentuo.crab.modular.mini.model.param.ticket;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
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
public class ExchangeTicketParam implements Serializable, BaseValidatingParam {

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
     * 兑换码
     */
    private String ticketNo;

    /**
     * 物品名称
     */
    private String goodName;

    /**
     * 兑换券名称
     */
    private String ticketName;

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
    public String checkParam() {
        return null;
    }

}
