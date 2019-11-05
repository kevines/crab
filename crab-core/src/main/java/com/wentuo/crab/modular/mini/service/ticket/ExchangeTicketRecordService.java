package com.wentuo.crab.modular.mini.service.ticket;


import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wentuo.crab.core.common.page.WTPageFactory;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicketRecord;
import com.wentuo.crab.modular.mini.mapper.ticket.ExchangeTicketRecordMapper;
import com.wentuo.crab.modular.mini.model.param.ticket.ExchangeTicketRecordParam;
import com.wentuo.crab.modular.mini.model.result.ticket.ExchangeTicketRecordResult;
import com.wentuo.crab.util.EntityConvertUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 兑换交易记录表(发货用) 服务实现类
 * </p>
 *
 * @author wangbencheng
 * @since 2019-10-31
 */
@Service
public class ExchangeTicketRecordService extends ServiceImpl<ExchangeTicketRecordMapper, ExchangeTicketRecord> {

    /**
     * 添加兑换券兑换记录
     * @param param
     */
    public void add(ExchangeTicketRecordParam param){
        ExchangeTicketRecord entity = getEntity(param);
        this.save(entity);
    }

    /**
     * 删除兑换券兑换记录
     * @param param
     */
    public WTResponse delete(ExchangeTicketRecordParam param){
        boolean flag = this.removeById(getKey(param));
        if (!flag) {
            return WTResponse.error("删除失败");
        }
        return WTResponse.success();
    }

    /**
     * 更新兑换券兑换记录信息
     * @param param
     */
    public void update(ExchangeTicketRecordParam param){
        ExchangeTicketRecord oldEntity = getOldEntity(param);
        ExchangeTicketRecord newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    /**
     * 通过兑换券号查询对应兑换卷交易记录详情
     * @param ticketNo
     * @return
     */
    public ExchangeTicketRecord findByTicketNo(String ticketNo) {
        QueryWrapper<ExchangeTicketRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ExchangeTicketRecord::getTicketNo, ticketNo);
        return this.baseMapper.selectOne(queryWrapper);
    }
    /**
     * 查询兑换群记录兑换详情
     * @param id 主键id
     * @return
     */
    public ExchangeTicketRecordResult findById(Long id){
        ExchangeTicketRecord exchangeTicketRecord = this.getById(id);
        ExchangeTicketRecordResult exchangeTicketRecordResult = EntityConvertUtils.convertAToB(exchangeTicketRecord, ExchangeTicketRecordResult.class);
        return exchangeTicketRecordResult;
    }

    /**
     * 查询兑换券记录列表
     * @param param
     * @return
     */
    public List<ExchangeTicketRecordResult> findListBySpec(ExchangeTicketRecordParam param){
        ExchangeTicketRecord exchangeTicketRecord = getEntity(param);
        QueryWrapper<ExchangeTicketRecord> queryWrapper = new QueryWrapper<>(exchangeTicketRecord);
        List<ExchangeTicketRecord> exchangeTicketRecordList = this.baseMapper.selectList(queryWrapper);
        List<ExchangeTicketRecordResult> exchangeTicketRecordResultList = EntityConvertUtils.convertAListToBList(exchangeTicketRecordList, ExchangeTicketRecordResult.class);
        return exchangeTicketRecordResultList;
    }

    /**
     * 分页查询兑换券记录列表
     * @param param
     * @return
     */
    public WTPageResponse findPageBySpec(ExchangeTicketRecordParam param){
        Page pageContext = getPageContext();
        ExchangeTicketRecord entity = getEntity(param);
        QueryWrapper<ExchangeTicketRecord> queryWrapper = new QueryWrapper<>(entity);
        IPage page = this.page(pageContext, queryWrapper);
        return WTPageFactory.createPageInfo(page);
    }

    private Serializable getKey(ExchangeTicketRecordParam param){
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
