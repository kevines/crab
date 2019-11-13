package com.wentuo.crab.appm.api.gop.ticket;
import cn.stylefeng.roses.core.base.controller.BaseController;
import javax.annotation.Resource;

import com.wentuo.crab.core.common.annotion.NoPermission;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.modular.mini.model.param.ticket.ExchangeTicketSpecificationParam;
import com.wentuo.crab.modular.mini.service.ticket.ExchangeTicketSpecificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 兑换券规格表控制器
 *
 * @author wangbencheng
 * @Date 2019-10-31 19:06:54
 */
@RestController
@RequestMapping("/exchange/ticket/specification")
public class ExchangeTicketSpecificationController extends BaseController {

    @Resource
    private ExchangeTicketSpecificationService exchangeTicketSpecificationService;

    /**
     * 批量生成兑换券功能
     *
     * @author wangbencheng
     * @Date 2019-10-31
     */
    @PostMapping("/create.do")
    @NoPermission
    public WTResponse addItem(ExchangeTicketSpecificationParam exchangeTicketSpecificationParam) {
        return this.exchangeTicketSpecificationService.createExchangeTicket(exchangeTicketSpecificationParam);
    }


    /**
     * 编辑兑换券属性信息
     * @param param
     * @return
     */
    @PostMapping("/edit.do")
    @NoPermission
    public WTResponse editTicketSpecification(ExchangeTicketSpecificationParam param) {
        return this.exchangeTicketSpecificationService.update(param);
    }

    /**
     * 批量删除蟹券
     *
     * @author wangbencheng
     * @Date 2019-10-31
     */
    @PostMapping("/delete.do")
    public WTResponse delete(ExchangeTicketSpecificationParam exchangeTicketSpecificationParam) {
        this.exchangeTicketSpecificationService.delete(exchangeTicketSpecificationParam);
        return WTResponse.success();
    }

    /**
     * 查询
     * @return
     */
    @RequestMapping("/select/list.do")
    public WTResponse queryTicketSpecificationNameList() {
        return this.exchangeTicketSpecificationService.findSelectList();
    }

    @RequestMapping("/page/list.do")
    public WTPageResponse findPageList(ExchangeTicketSpecificationParam param) {
        return this.exchangeTicketSpecificationService.findPageBySpec(param);
    }
}


