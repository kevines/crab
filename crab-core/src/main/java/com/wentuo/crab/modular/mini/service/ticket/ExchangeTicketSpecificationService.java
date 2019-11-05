package com.wentuo.crab.modular.mini.service.ticket;

import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wentuo.crab.core.common.page.WTPageFactory;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.core.util.RedisUtil;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicket;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicketSpecification;
import com.wentuo.crab.modular.mini.mapper.ticket.ExchangeTicketSpecificationMapper;
import com.wentuo.crab.modular.mini.model.param.ticket.ExchangeTicketParam;
import com.wentuo.crab.modular.mini.model.param.ticket.ExchangeTicketSpecificationParam;
import com.wentuo.crab.modular.mini.model.result.ticket.ExchangeTicketSpecificationResult;
import com.wentuo.crab.util.EntityConvertUtils;
import com.wentuo.crab.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 兑换券规格表 服务实现类
 * </p>
 *
 * @author wangbencheng
 * @since 2019-10-31
 */
@Service
public class ExchangeTicketSpecificationService extends ServiceImpl<ExchangeTicketSpecificationMapper, ExchangeTicketSpecification> {

    @Resource
    private ExchangeTicketService exchangeTicketService;
    /**
     * 添加兑换卷规券记录
     * @param param
     */
    public WTResponse add(ExchangeTicketSpecificationParam param){
        ExchangeTicketSpecification entity = getEntity(param);
        String specificationId = RedisUtil.getServiceKeyHaveDateByType("SPE");
        entity.setSpecificationId(specificationId);
        boolean flag = this.save(entity);
        if (!flag) {
            return WTResponse.error("新增蟹券失败");
        }
        return WTResponse.success("新增蟹券成功",specificationId);
    }


    /**
     * 根据输入蟹券规格批量生成兑换券
     * @param param
     * @return
     */
    public WTResponse createExchangeTicket(ExchangeTicketSpecificationParam param) {
        //添加此种规格的兑换券记录入库
        WTResponse wtResponse = this.add(param);
        if (wtResponse.getCode() != 200) {
            return wtResponse;
        }
        //兑换券记录已经成功添加
        String specificationId = (String) wtResponse.getData();
        if (StringUtil.isNotEmpty(specificationId)) {
            ExchangeTicketSpecification exchangeTicketSpecification = this.findBySpecificationId(specificationId);
            Integer number = param.getStock();  //生成兑换券数量
            String timeStamp = RedisUtil.getServiceKeyHaveDateByType("");
            long exchangeCode = Long.parseLong(timeStamp);
            for (int i = 0; i < number; i ++) {  //批量生产此规格蟹券
                ExchangeTicketParam exchangeTicketParam = new ExchangeTicketParam();
                exchangeTicketParam.setIsSend(false);
                exchangeTicketParam.setIsExchange(false);
                if (exchangeTicketSpecification != null) {
                    exchangeTicketParam.setSpecificationId(exchangeTicketSpecification.getId());
                }
                exchangeTicketParam.setTicketNo(Long.toString(exchangeCode + i));
                this.exchangeTicketService.add(exchangeTicketParam);
            }
        }
        return WTResponse.success();
    }

    /**
     * 删除兑换券规格记录
     * @param param
     */
    public void delete(ExchangeTicketSpecificationParam param){
        //批量删除该规格类型下的蟹券
        QueryWrapper<ExchangeTicket> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ExchangeTicket::getSpecificationId, param.getId());
        List<ExchangeTicket> list = this.exchangeTicketService.getBaseMapper().selectList(queryWrapper);
        list.forEach(exchangeTicket -> {
            ExchangeTicketParam exchangeTicketParam = new ExchangeTicketParam();
            exchangeTicketParam.setId(exchangeTicket.getId());
            this.exchangeTicketService.delete(exchangeTicketParam);
        });
        this.removeById(getKey(param));
    }

    /**
     * 更新兑换券规格记录
     * @param param
     */
    public void update(ExchangeTicketSpecificationParam param){
        ExchangeTicketSpecification oldEntity = getOldEntity(param);
        ExchangeTicketSpecification newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    /**
     * 通过规格编号查询兑换券规格详情
     * @param specificationId
     * @return
     */
    public ExchangeTicketSpecification findBySpecificationId(String specificationId) {
        QueryWrapper<ExchangeTicketSpecification> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ExchangeTicketSpecification::getSpecificationId, specificationId);
        return this.baseMapper.selectOne(queryWrapper);
    }

    /**
     * 查询兑换群规格详情
     * @param param
     * @return
     */
    public ExchangeTicketSpecificationResult findBySpec(ExchangeTicketSpecificationParam param){
        ExchangeTicketSpecification exchangeTicketSpecification = this.getById(param.getId());
        ExchangeTicketSpecificationResult result = EntityConvertUtils.convertAToB(exchangeTicketSpecification, ExchangeTicketSpecificationResult.class);
        return result;
    }

    /**
     * 查询兑换券列表信息
     * @param param
     * @return
     */
    public List<ExchangeTicketSpecificationResult> findListBySpec(ExchangeTicketSpecificationParam param){
        ExchangeTicketSpecification entity = getEntity(param);
        QueryWrapper<ExchangeTicketSpecification> queryWrapper = new QueryWrapper<>(entity);
        List<ExchangeTicketSpecification> specificationList = this.baseMapper.selectList(queryWrapper);
        List<ExchangeTicketSpecificationResult> resultList = EntityConvertUtils.convertAListToBList(specificationList, ExchangeTicketSpecificationResult.class);
        return resultList;
    }

    /**
     * 分页查询兑换券劣列表信息
     * @param param
     * @return
     */
    public WTPageResponse findPageBySpec(ExchangeTicketSpecificationParam param){
        Page pageContext = getPageContext();
        ExchangeTicketSpecification entity = getEntity(param);
        QueryWrapper<ExchangeTicketSpecification> objectQueryWrapper = new QueryWrapper<>(entity);
        IPage page = this.page(pageContext, objectQueryWrapper);
        return WTPageFactory.createPageInfo(page);
    }

    private Serializable getKey(ExchangeTicketSpecificationParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return WTPageFactory.defaultPage();
    }

    private ExchangeTicketSpecification getOldEntity(ExchangeTicketSpecificationParam param) {
        return this.getById(getKey(param));
    }

    private ExchangeTicketSpecification getEntity(ExchangeTicketSpecificationParam param) {
        param = EntityConvertUtils.setNullValue(param);
        ExchangeTicketSpecification entity = new ExchangeTicketSpecification();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}