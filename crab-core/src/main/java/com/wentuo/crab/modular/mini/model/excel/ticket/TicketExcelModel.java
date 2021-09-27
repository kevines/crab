package com.wentuo.crab.modular.mini.model.excel.ticket;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.*;

/**
 * 功能描述：导出蟹券Excel封装格式
 *
 * @author wangbencheng
 * @version 1.0.0
 * @claaName TicketExcelModel
 * @since 2019/11/13 16:03
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TicketExcelModel extends BaseRowModel {

    /**
     * 蟹券名称
     */
    @ExcelProperty(value = "蟹券名称", index = 0)
    private String ticketName;

    /**
     * 蟹券兑换号码
     */
    @ExcelProperty(value = "兑换号码", index = 1)
    private String ticketNo;

    /**
     * 蟹券卡号('NO.' + id的9位0充)
     */
    @ExcelProperty(value = "卡号", index = 2)
    private String cardNo;

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
