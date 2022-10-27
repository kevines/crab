package com.wentuo.crab.modular.mini.service.ticket;


import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wentuo.crab.core.common.page.WTPageFactory;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicket;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicketRecord;
import com.wentuo.crab.modular.mini.mapper.ticket.ExchangeTicketRecordMapper;
import com.wentuo.crab.modular.mini.model.param.ticket.ExchangeTicketRecordParam;
import com.wentuo.crab.modular.mini.model.param.ticket.ExchangeTicketSpecificationParam;
import com.wentuo.crab.modular.mini.model.result.ticket.ExchangeTicketRecordResult;
import com.wentuo.crab.modular.mini.model.result.ticket.ExchangeTicketSpecificationResult;
import com.wentuo.crab.util.EntityConvertUtils;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 兑换交易记录表(发货用) 服务实现类
 * </p>
 *
 * @author wangbencheng
 * @since 2019-10-31
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Service
public class ExchangeTicketRecordService extends ServiceImpl<ExchangeTicketRecordMapper, ExchangeTicketRecord> {

    @Resource
    private ExchangeTicketService exchangeTicketService;

    @Resource
    private ExchangeTicketSpecificationService exchangeTicketSpecificationService;

    /**
     * 添加兑换券兑换记录
     *
     * @param param param
     */
    public void add(ExchangeTicketRecordParam param) {
        ExchangeTicketRecord entity = getEntity(param);
        this.save(entity);
    }

    /**
     * 删除兑换券兑换记录
     *
     * @param param param
     */
    public WTResponse delete(ExchangeTicketRecordParam param) {
        boolean flag = this.removeById(getKey(param));
        if (!flag) {
            return WTResponse.error("删除失败");
        }
        return WTResponse.success();
    }

    /**
     * 更新兑换券兑换记录信息
     *
     * @param param param
     */
    public void update(ExchangeTicketRecordParam param) {
        ExchangeTicketRecord oldEntity = getOldEntity(param);
        ExchangeTicketRecord newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    /**
     * 通过兑换券号查询对应兑换卷交易记录详情
     *
     * @param ticketNo 卡号
     * @return 兑换卷详情
     */
    public ExchangeTicketRecord findByTicketNo(String ticketNo) {
        QueryWrapper<ExchangeTicketRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ExchangeTicketRecord::getTicketNo, ticketNo);
        return this.baseMapper.selectOne(queryWrapper);
    }

    /**
     * 查询兑换群记录兑换详情
     *
     * @param id 主键id
     * @return 兑换券详情
     */
    public ExchangeTicketRecordResult findById(Long id) {
        ExchangeTicketRecord exchangeTicketRecord = this.getById(id);
        return this.setObjectResult(exchangeTicketRecord);
    }

    /**
     * 通过兑换码查询兑换记录详情
     *
     * @param ticketNo 卡号
     * @return 兑换卷详情
     */
    public ExchangeTicketRecordResult findDetailByTicketNo(String ticketNo) {
        ExchangeTicketRecord exchangeTicketRecord = this.findByTicketNo(ticketNo);
        return this.setObjectResult(exchangeTicketRecord);
    }

    /**
     * 查询兑换券记录列表
     *
     * @param param params
     * @return 兑换卷列表
     */
    public List<ExchangeTicketRecordResult> findListBySpec(ExchangeTicketRecordParam param) {
        ExchangeTicketRecord exchangeTicketRecord = getEntity(param);
        QueryWrapper<ExchangeTicketRecord> queryWrapper = new QueryWrapper<>(exchangeTicketRecord);
        List<ExchangeTicketRecord> exchangeTicketRecordList = this.baseMapper.selectList(queryWrapper);
        return EntityConvertUtils.convertAListToBList(exchangeTicketRecordList, ExchangeTicketRecordResult.class);
    }

    /**
     * 分页查询兑换券记录列表
     *
     * @param param param
     * @return 分页查询
     */
    public WTPageResponse findPageBySpec(ExchangeTicketRecordParam param) {
        Page pageContext = getPageContext();
        ExchangeTicketRecord entity = getEntity(param);
        QueryWrapper<ExchangeTicketRecord> queryWrapper = new QueryWrapper<>(entity);
        IPage page = this.page(pageContext, queryWrapper);
        List<ExchangeTicketRecord> recordList = page.getRecords();
        List<ExchangeTicketRecordResult> collect = recordList.stream().map(this::setObjectResult).collect(Collectors.toList());
        page.setRecords(collect);
        return WTPageFactory.createPageInfo(page);
    }

    /**
     * 给对应兑换卷进行备注
     *
     * @param ticketNo 卡号
     * @param remark   备注
     */
    public WTResponse remark(String ticketNo, String remark) {
        ExchangeTicketRecord one = new ExchangeTicketRecord();
        one.setRemark(remark);
        this.update(one, Wrappers.lambdaQuery(new ExchangeTicketRecord()).eq(ExchangeTicketRecord::getTicketNo, ticketNo));
        return WTResponse.success();
    }


    /**
     * 设置蟹券相关属性
     *
     * @param exchangeTicketRecord DO
     * @return Result
     */
    private ExchangeTicketRecordResult setObjectResult(ExchangeTicketRecord exchangeTicketRecord) {
        if (exchangeTicketRecord == null) {
            return null;
        }
        ExchangeTicketRecordResult exchangeTicketRecordResult = EntityConvertUtils.convertAToB(exchangeTicketRecord, ExchangeTicketRecordResult.class);
        String ticketNo = exchangeTicketRecordResult.getTicketNo();
        ExchangeTicket exchangeTicket = this.exchangeTicketService.findByTicketNo(ticketNo);
        if (exchangeTicket != null) {
            Long ticketSpecificationId = exchangeTicket.getSpecificationId();
            ExchangeTicketSpecificationParam exchangeTicketSpecificationParam = new ExchangeTicketSpecificationParam();
            exchangeTicketSpecificationParam.setId(ticketSpecificationId);
            ExchangeTicketSpecificationResult exchangeTicketSpecificationResult = this.exchangeTicketSpecificationService
                .findBySpec(exchangeTicketSpecificationParam);
            if (exchangeTicketSpecificationResult != null) {
                exchangeTicketRecordResult.setExchangeTicketSpecificationResult(exchangeTicketSpecificationResult);
            }
            exchangeTicketRecordResult.setExchangeTicket(exchangeTicket);
            exchangeTicketRecordResult.setCardNo("NO." + String.format("%09d", exchangeTicket.getId()));
        }
        return exchangeTicketRecordResult;

    }

    private Serializable getKey(ExchangeTicketRecordParam param) {
        return param.getId();
    }

    private Page getPageContext() {
        return WTPageFactory.defaultPage();
    }

    private ExchangeTicketRecord getOldEntity(ExchangeTicketRecordParam param) {
        return this.getById(getKey(param));
    }

    private ExchangeTicketRecord getEntity(ExchangeTicketRecordParam param) {
        param = EntityConvertUtils.setNullValue(param);
        ExchangeTicketRecord entity = new ExchangeTicketRecord();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
