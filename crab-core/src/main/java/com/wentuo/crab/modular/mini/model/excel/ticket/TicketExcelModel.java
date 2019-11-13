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
}
