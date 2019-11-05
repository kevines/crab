package com.wentuo.crab.appm.api.gop.ticket;

import cn.stylefeng.roses.core.base.controller.BaseController;
import javax.annotation.Resource;
import javax.validation.constraints.Null;

import com.wentuo.crab.core.common.annotion.NoPermission;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicketRecord;
import com.wentuo.crab.modular.mini.model.param.ticket.ExchangeTicketRecordParam;
import com.wentuo.crab.modular.mini.model.result.ticket.ExchangeTicketRecordResult;
import com.wentuo.crab.modular.mini.service.ticket.ExchangeTicketRecordService;
import com.wentuo.crab.modular.mini.service.ticket.ExchangeTicketService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 兑换交易记录表(发货用)控制器
 *
 * @author wangbencheng
 * @Date 2019-10-31 19:06:54
 */
@RestController
@RequestMapping("/exchange/ticket/record")
public class ExchangeTicketRecordController extends BaseController {

    @Resource
    private ExchangeTicketRecordService exchangeTicketRecordService;

    @Resource
    private ExchangeTicketService exchangeTicketService;

    /**
     * 删除蟹券兑换记录
     *
     * @author wangbencheng
     * @Date 2019-10-31
     */
    @PostMapping("/delete.do")
    public WTResponse delete(ExchangeTicketRecordParam exchangeTicketRecordParam) {
        return this.exchangeTicketRecordService.delete(exchangeTicketRecordParam);
    }

    /**
     * 查看蟹券兑换详情接口
     *
     * @author wangbencheng
     * @Date 2019-10-31
     */
    @RequestMapping("/detail.do")
    @NoPermission
    public WTResponse detail(Long id) {
        ExchangeTicketRecordResult detail = this.exchangeTicketRecordService.findById(id);
        return WTResponse.success(detail);
    }

    /**
     * 分页蟹券兑换记录列表
     *
     * @author wangbencheng
     * @Date 2019-10-31
     */
    @RequestMapping("/page/list.do")
    @NoPermission
    public WTPageResponse list(ExchangeTicketRecordParam exchangeTicketRecordParam) {
        return this.exchangeTicketRecordService.findPageBySpec(exchangeTicketRecordParam);
    }

    /**
     * 后端发货按钮
     * @param ticketNo 兑换券号码
     * @param logisticsNo 物流单号
     * @param logisticsName 物流名称
     * @return
     */
    @PostMapping("/send.do")
    @NoPermission
    public WTResponse sendGood(String ticketNo, String logisticsNo, String logisticsName) {
        return this.exchangeTicketService.sendGood(ticketNo, logisticsNo, logisticsName);
    }

    /**
     * 后端撤销发货按钮
     * @param ticketNo 兑换券号码
     * @return
     */
    @PostMapping("/cancel/send.do")
    @NoPermission
    public WTResponse cancelSendGood(String ticketNo) {
        return this.exchangeTicketService.cancelSendGood(ticketNo);
    }

}


