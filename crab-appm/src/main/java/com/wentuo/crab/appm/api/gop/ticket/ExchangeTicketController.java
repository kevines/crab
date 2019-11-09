package com.wentuo.crab.appm.api.gop.ticket;

import cn.stylefeng.roses.core.base.controller.BaseController;

import javax.annotation.Resource;

import com.wentuo.crab.core.common.annotion.NoPermission;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.modular.mini.model.param.ticket.ExchangeTicketParam;
import com.wentuo.crab.modular.mini.model.result.ticket.ExchangeTicketResult;
import com.wentuo.crab.modular.mini.service.ticket.ExchangeTicketService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 兑换券详情列表控制器
 *
 * @author wangbencheng
 * @Date 2019-10-31 19:06:54
 */
@RestController
@RequestMapping("/exchange/ticket")
public class ExchangeTicketController extends BaseController {

    @Resource
    private ExchangeTicketService exchangeTicketService;


    /**
     * 删除蟹券
     * @author wangbencheng
     * @Date 2019-10-31
     */
    @PostMapping("/delete.do")
    public WTResponse delete(ExchangeTicketParam exchangeTicketParam) {
        return this.exchangeTicketService.delete(exchangeTicketParam);
    }

    /**
     * 查看蟹券详情
     *
     * @author wangbencheng
     * @Date 2019-10-31
     */
    @RequestMapping("/detail.do")
    @NoPermission
    public WTResponse detail(ExchangeTicketParam exchangeTicketParam) {
        ExchangeTicketResult detail = this.exchangeTicketService.findBySpec(exchangeTicketParam);
        return WTResponse.success(detail);
    }

    /**
     * 分页查询列表
     *
     * @author wangbencheng
     * @Date 2019-10-31
     */
    @RequestMapping("/page/list.do")
    @NoPermission
    public WTPageResponse list(ExchangeTicketParam exchangeTicketParam) {
        return this.exchangeTicketService.findPageBySpec(exchangeTicketParam);
    }

}


