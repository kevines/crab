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
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicketRecord;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicketSpecification;
import com.wentuo.crab.modular.mini.mapper.ticket.ExchangeTicketSpecificationMapper;
import com.wentuo.crab.modular.mini.model.excel.ticket.TicketExcelModel;
import com.wentuo.crab.modular.mini.model.param.ticket.ExchangeTicketParam;
import com.wentuo.crab.modular.mini.model.param.ticket.ExchangeTicketSpecificationParam;
import com.wentuo.crab.modular.mini.model.result.ticket.ExchangeTicketSpecificationResult;
import com.wentuo.crab.util.CodeUtil;
import com.wentuo.crab.util.EntityConvertUtils;
import com.wentuo.crab.util.StringUtil;
import com.wentuo.crab.util.excel.ExcelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private ExchangeTicketRecordService exchangeTicketRecordService;
    /**
     * 添加兑换卷规券记录
     * @param param
     */
    public WTResponse add(ExchangeTicketSpecificationParam param){
        //首选查询兑换券名称是否重复
        ExchangeTicketSpecificationParam exchangeTicketSpecificationParam = new ExchangeTicketSpecificationParam();
        exchangeTicketSpecificationParam.setTicketName(param.getTicketName());
        List<ExchangeTicketSpecificationResult> list = this.findListBySpec(exchangeTicketSpecificationParam);
        if (list.size() != 0) {
            return WTResponse.error("兑换券名称不可重复");
        }
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
            List<ExchangeTicket> exchangeTicketList = this.exchangeTicketService.list();
            for (int i = 0; i < number; i ++) {  //批量生产此规格蟹券
                ExchangeTicketParam exchangeTicketParam = new ExchangeTicketParam();
                exchangeTicketParam.setIsSend(false);
                exchangeTicketParam.setIsExchange(false);
                if (exchangeTicketSpecification != null) {
                    exchangeTicketParam.setSpecificationId(exchangeTicketSpecification.getId());
                }
                exchangeTicketParam = this.judgeExchangeCodeIsRepeat(exchangeTicketParam, exchangeTicketList, i);
                this.exchangeTicketService.add(exchangeTicketParam);
            }
        }
        return WTResponse.success();
    }

    /**
     * 判断生成的随机兑换码是否重复
     * @param exchangeTicketList
     * @return
     */
    private ExchangeTicketParam judgeExchangeCodeIsRepeat(ExchangeTicketParam exchangeTicketParam, List<ExchangeTicket> exchangeTicketList, Integer index) {
        //生成兑换券码
        String exchangeCode = CodeUtil.toSerialCode(index, 10).toUpperCase();
        boolean flag = true;
        for (ExchangeTicket exchangeTicket: exchangeTicketList) {
            if (exchangeCode.equals(exchangeTicket.getTicketNo())) {
                flag = false;
                break;
            }
        }
        if (flag) {
            exchangeTicketParam.setTicketNo(exchangeCode);
            return exchangeTicketParam;
        } else {
            return this.judgeExchangeCodeIsRepeat(exchangeTicketParam, exchangeTicketList, index);
        }
    }

    /**
     * 导出兑换券Excel
     * @param response
     * @param id
     * @return
     */
    public WTResponse exportExcel(HttpServletResponse response, Long id) {
        try {
            List<TicketExcelModel> list = this.findTicketModelList(id);
            String ticketNam = "";
            if (list.size() != 0) {
                ticketNam = list.get(0).getTicketName();
            }
            if (StringUtil.isNotEmpty(ticketNam)) {
                ExcelUtil.writeExcel(response, list, ticketNam + "-兑换码列表", new TicketExcelModel());
            } else {
                ExcelUtil.writeExcel(response, list, "蟹券兑换码列表", new TicketExcelModel());
            }

            return new WTResponse(WTResponse.SUCCESS, "导出成功", true);
        }catch (Exception e) {
            return new WTResponse(WTResponse.ERROR, "导出失败", false);
        }
    }

    /**
     * 查询导出Excel表格所需的蟹券兑换码数据格式
     * @param id
     * @return
     */
    private List<TicketExcelModel> findTicketModelList(Long id) {
        ExchangeTicketSpecification exchangeTicketSpecification = this.getById(id);
        List<TicketExcelModel> list = new ArrayList<>();
        if (exchangeTicketSpecification != null) {
            List<ExchangeTicket> ticketList = this.exchangeTicketService.findListBySpecificationId(exchangeTicketSpecification.getId());
            ticketList.forEach(exchangeTicket -> {
                TicketExcelModel ticketExcelModel = new TicketExcelModel();
                ticketExcelModel.setTicketName(exchangeTicketSpecification.getTicketName());
                ticketExcelModel.setTicketNo(exchangeTicket.getTicketNo());
                list.add(ticketExcelModel);
            });
        }
        return list;
    }

    /**
     * 删除兑换券规格记录
     * @param param
     */
    public void delete(ExchangeTicketSpecificationParam param){
        //批量删除该规格类型下的蟹券和相关交易记录
        QueryWrapper<ExchangeTicket> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ExchangeTicket::getSpecificationId, param.getId());
        List<ExchangeTicket> list = this.exchangeTicketService.getBaseMapper().selectList(queryWrapper);
        list.forEach(exchangeTicket -> {
            ExchangeTicketParam exchangeTicketParam = new ExchangeTicketParam();
            exchangeTicketParam.setId(exchangeTicket.getId());
            this.exchangeTicketService.delete(exchangeTicketParam);
            String ticketNo = exchangeTicket.getTicketNo();
            ExchangeTicketRecord exchangeTicketRecord = this.exchangeTicketRecordService.findByTicketNo(ticketNo);
            if (exchangeTicketRecord != null) {
                this.exchangeTicketRecordService.getBaseMapper().deleteById(exchangeTicketRecord.getId());
            }
        });


        this.removeById(getKey(param));
    }

    /**
     * 更新兑换券规格记录
     * @param param
     */
    public WTResponse update(ExchangeTicketSpecificationParam param){
        ExchangeTicketSpecification oldEntity = getOldEntity(param);
        ExchangeTicketSpecification newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        boolean flag = this.updateById(newEntity);
        if (!flag) {
            return WTResponse.error("更新失败");
        }
        return WTResponse.success("更新成功");
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
     * 查询兑换券名称下拉列表
     * @return
     */
    public WTResponse findSelectList() {
        List list = new ArrayList();
        ExchangeTicketSpecificationParam exchangeTicketSpecificationParam = new ExchangeTicketSpecificationParam();
        List<ExchangeTicketSpecificationResult> ticketSpecificationList = this.findListBySpec(exchangeTicketSpecificationParam);
        ticketSpecificationList.forEach(exchangeTicketSpecificationResult -> {
            Map map = new HashMap();
            map.put("code", exchangeTicketSpecificationResult.getId());
            map.put("name", exchangeTicketSpecificationResult.getTicketName());
            list.add(map);
        });
        return WTResponse.success(list);
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
