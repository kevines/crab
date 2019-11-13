package com.wentuo.crab.appm.api.mini.ticket;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.wentuo.crab.core.common.annotion.NoPermission;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicketRecord;
import com.wentuo.crab.modular.mini.service.ticket.ExchangeTicketRecordService;
import com.wentuo.crab.modular.mini.service.ticket.ExchangeTicketService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 功能描述：小程序端兑换券功能
 *
 * @author wangbencheng
 * @version 1.0.0
 * @claaName TicketController
 * @since 2019/10/31 20:58
 */
@RestController
@RequestMapping("/mini/ticket")
public class TicketController extends BaseController {

    @Resource
    private ExchangeTicketService exchangeTicketService;

    @Resource
    private ExchangeTicketRecordService exchangeTicketRecordService;


    /**
     * 小程序进行蟹券兑换券兑换
     * @param userId 用户编号
     * @param ticketNo 兑换券编号
     * @param mobile 联系手机号
     * @param address 地址
     * @return
     */
    @PostMapping("/convert.do")
    @NoPermission
    public WTResponse exchangeTicket(String userId, String ticketNo, String userName, String mobile, String address) {
        return this.exchangeTicketService.exchangeTicket(userId, ticketNo, userName, mobile, address);
    }

    /**
     * 小程序端点击兑换查询商品详情
     * @param ticketNo 兑换券编号
     * @return
     */
    @RequestMapping("/detail.do")
    @NoPermission
    public WTResponse queryTicketInfo(String ticketNo) {
        return WTResponse.success(this.exchangeTicketService.findResultByTicketNo(ticketNo));
    }

    /**
     * 我的蟹券列表
     * @param userId 用户编号
     * @return
     */
    @RequestMapping("/my/crab/list.do")
    @NoPermission
    public WTResponse queryTicketByUserId(String userId) {
        return this.exchangeTicketService.queryTicketByUserId(userId);
    }

    /**
     * 查询我的蟹券列表中兑换券详情
     * @param ticketNo
     * @return
     */
    @RequestMapping("/my/crab/detail" +
            "" +
            ".do")
    @NoPermission
    public WTResponse queryTicketExchangeDetail(String ticketNo) {
        return WTResponse.success(this.exchangeTicketRecordService.findDetailByTicketNo(ticketNo));
    }

}
