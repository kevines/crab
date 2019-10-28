package com.wentuo.crab.core.pay.common.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 转账订单
 * @author egan
 * <pre>
 * email egzosn@gmail.com
 * date 2018/1/31
 * </pre>
 */
@Data
public class TransferOrder {

    /**
     * 转账订单单号
     */
    private String outNo;

    /**
     * 收款方账户, 用户openid
     */
    private String  payeeAccount ;

    /**
     * 转账金额
     */
    private BigDecimal amount ;

    /**
     * 付款人名称
     */
    private String payerName;

    /**
     * 收款人名称
     */
    private String payeeName;
    /**
     * 备注
     */
    private String remark;

    /**
     * 收款开户行
      */
    private Bank bank;

    /**
     * 币种
     */
    private CurType curType;
    /**
     * 转账类型，收款方账户类型，比如支付宝账户或者银行卡
     */
    private TransferType transferType;


}
