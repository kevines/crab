package com.wentuo.crab.modular.mini.service.ticket;


import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wentuo.crab.core.common.page.WTPageFactory;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicket;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicketRecord;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicketSpecification;
import com.wentuo.crab.modular.mini.mapper.ticket.ExchangeTicketMapper;
import com.wentuo.crab.modular.mini.model.param.ticket.ExchangeTicketParam;
import com.wentuo.crab.modular.mini.model.param.ticket.ExchangeTicketRecordParam;
import com.wentuo.crab.modular.mini.model.result.ticket.ExchangeTicketResult;
import com.wentuo.crab.util.DateUtils;
import com.wentuo.crab.util.EntityConvertUtils;
import com.wentuo.crab.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 兑换券详情列表 服务实现类
 * </p>
 *
 * @author wangbencheng
 * @since 2019-10-31
 */
@Service
public class ExchangeTicketService extends ServiceImpl<ExchangeTicketMapper, ExchangeTicket> {

    @Resource
    private ExchangeTicketRecordService exchangeTicketRecordService;

    @Resource
    private ExchangeTicketSpecificationService exchangeTicketSpecificationService;

    /**
     * 添加兑换券信息
     *
     * @param param
     */
    public void add(ExchangeTicketParam param) {
        ExchangeTicket entity = getEntity(param);
        this.save(entity);
    }

    /**
     * 删除兑换券记录
     *
     * @param param 删除所需参数
     */
    public WTResponse delete(ExchangeTicketParam param) {
        boolean flag = this.removeById(getKey(param));
        if (!flag) {
            return WTResponse.error("删除失败");
        }
        return WTResponse.success();
    }

    /**
     * 更新兑换券信息
     *
     * @param param
     */
    public WTResponse update(ExchangeTicketParam param) {
        ExchangeTicket oldEntity = getOldEntity(param);
        ExchangeTicket newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        boolean flag = this.updateById(newEntity);
        if (!flag) {
            return WTResponse.error("更新兑换券信息失败");
        }
        return WTResponse.success("更新兑换券信息成功");
    }

    /**
     * 小程序端进行兑换券兑换
     *
     * @param userId   用户编号
     * @param ticketNo 兑换券号
     * @param userName 用户姓名
     * @param mobile   联系方式
     * @param address  收货地址信息
     * @return
     */
    public WTResponse exchangeTicket(String userId, String ticketNo, String userName, String mobile, String address) {
        if (StringUtil.isNotEmpty(ticketNo)) {  //判断兑换券号是否为空
            ticketNo = ticketNo.toUpperCase();
            //查询兑换券号是否存在
            QueryWrapper<ExchangeTicket> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(ExchangeTicket::getTicketNo, ticketNo);
            ExchangeTicket exchangeTicket = this.baseMapper.selectOne(queryWrapper);
            if (exchangeTicket == null) {  //查询记录为空
                return WTResponse.nullResult("未查询到该兑换券记录");
            }
            if (exchangeTicket.getIsExchange()) {
                return WTResponse.error("该蟹券已经兑换，无法重复进行兑换操作");
            }
            ExchangeTicketSpecification exchangeTicketSpecification = this.exchangeTicketSpecificationService.getById(exchangeTicket.getSpecificationId());
            int dateResult = DateUtils.compareTimes(exchangeTicketSpecification.getExpiryDate(), new Date());
            if (dateResult != 1) {
                return WTResponse.error("已超过兑换截止日期，无法进行兑换操作");
            }
            //查询得到记录,更新兑换券兑换状态
            ExchangeTicketParam exchangeTicketParam = new ExchangeTicketParam();
            exchangeTicketParam.setId(exchangeTicket.getId());
            exchangeTicketParam.setIsExchange(true);
            exchangeTicketParam.setExchangeDate(new Date());
            WTResponse wtResponse = this.update(exchangeTicketParam);
            if (wtResponse.getCode() != 200) {
                return wtResponse;
            }
            //添加兑换记录到数据库中
            ExchangeTicketRecordParam exchangeTicketRecordParam = new ExchangeTicketRecordParam();
            exchangeTicketRecordParam.setAddress(address);
            exchangeTicketRecordParam.setMobile(mobile);
            exchangeTicketRecordParam.setUserName(userName);
            exchangeTicketRecordParam.setUserId(userId);
            exchangeTicketRecordParam.setTicketNo(ticketNo);
            this.exchangeTicketRecordService.add(exchangeTicketRecordParam);
            return WTResponse.success("兑换成功");
        } else {
            return WTResponse.error("传入的兑换券号不为空");
        }
    }

    /**
     * 通过兑换券号查询兑换卷信息（图片，规格、数量、名称等信息）
     * @param ticketNo 兑换券编号
     * @return
     */
    public WTResponse queryTicketInfoByTicketNo(String ticketNo) {
        ExchangeTicket exchangeTicket = this.findByTicketNo(ticketNo);
        if (exchangeTicket == null) {
            return WTResponse.nullResult("未查询到兑换券信息");
        }
        Long specificationId = exchangeTicket.getSpecificationId();
        ExchangeTicketSpecification exchangeTicketSpecification = this.exchangeTicketSpecificationService.getById(specificationId);
        if (exchangeTicketSpecification == null) {
            return WTResponse.nullResult("未查询到兑换券商品规格信息");
        }
        return WTResponse.success(exchangeTicketSpecification);
    }

    /**
     * 根据用户编号查询蟹券
     * @param userId
     * @return
     */
    public WTResponse queryTicketByUserId(String userId) {
        QueryWrapper<ExchangeTicketRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ExchangeTicketRecord::getUserId, userId);
        List<ExchangeTicketRecord> list = this.exchangeTicketRecordService.getBaseMapper().selectList(queryWrapper);
        List<ExchangeTicketResult> resultList = new ArrayList<>();
        list.forEach(exchangeTicketRecord -> {
            String ticketNo = exchangeTicketRecord.getTicketNo();
            ExchangeTicket exchangeTicket = this.findByTicketNo(ticketNo);
            ExchangeTicketResult exchangeTicketResult = this.setObjectProperty(exchangeTicket);
            resultList.add(exchangeTicketResult);
        });
        return WTResponse.success(resultList);
    }
    /**
     * pc端发货操作
     *
     * @param ticketNo      兑换券号
     * @param logisticsNo   物流单号
     * @param logisticsName 物流公司名称
     * @return
     */
    public WTResponse sendGood(String ticketNo, String logisticsNo, String logisticsName) {
        //查询兑换券信息
        ExchangeTicket exchangeTicket = this.findByTicketNo(ticketNo);
        if (exchangeTicket == null) {
            return WTResponse.nullResult("未查询到该兑换券记录信息");
        }
        if (StringUtil.isEmpty(logisticsName) || StringUtil.isEmpty(logisticsNo)) {
            return WTResponse.nullResult("物流相关信息不能为空");
        }
        //改变发货状态
        ExchangeTicketParam exchangeTicketParam = new ExchangeTicketParam();
        exchangeTicketParam.setId(exchangeTicket.getId());
        exchangeTicketParam.setIsSend(true);
        exchangeTicketParam.setSendDate(new Date());
        WTResponse updateExchangeTicket = this.update(exchangeTicketParam);
        if (updateExchangeTicket.getCode() == 200) {   //表示更改发货状态成功，将交易记录信息更新到交易记录表中
            //查询兑换券交易记录表中对应的兑换券交易记录信息
            ExchangeTicketRecord exchangeTicketRecord = this.exchangeTicketRecordService.findByTicketNo(ticketNo);
            if (exchangeTicketRecord == null) {
                return WTResponse.nullResult("未查询到该兑换券记录信息");
            }
            //查询到信息记录则更新信息到记录中去
            ExchangeTicketRecordParam exchangeTicketRecordParam = new ExchangeTicketRecordParam();
            exchangeTicketRecordParam.setId(exchangeTicketRecord.getId());
            exchangeTicketRecordParam.setLogisticsName(logisticsName);
            exchangeTicketRecordParam.setLogisticsNo(logisticsNo);
            this.exchangeTicketRecordService.update(exchangeTicketRecordParam);
        }
        return WTResponse.success();
    }

    /**
     * 撤销发货操作（因为物流信息填错或者误操作造成而创建的权限，必须确认是误操作造成的发货错误才能使用该操作）
     * @param ticketNo 兑换券号码
     * @return
     */
    public WTResponse cancelSendGood(String ticketNo) {
        //查询兑换券信息
        ExchangeTicket exchangeTicket = this.findByTicketNo(ticketNo);
        if (exchangeTicket == null) {
            return WTResponse.nullResult("未查询到该兑换券记录信息");
        }
        //将交易记录中的快递信息和快递单号置空
        ExchangeTicketRecord exchangeTicketRecord = this.exchangeTicketRecordService.findByTicketNo(ticketNo);
        if (exchangeTicketRecord == null) {
            return WTResponse.nullResult("未查询到该兑换券记录信息");
        }
        ExchangeTicketRecordParam exchangeTicketRecordParam = new ExchangeTicketRecordParam();
        exchangeTicketRecordParam.setId(exchangeTicketRecord.getId());
        exchangeTicketRecordParam.setLogisticsName(null);
        exchangeTicketRecordParam.setLogisticsNo(null);
        this.exchangeTicketRecordService.update(exchangeTicketRecordParam);
        //将兑换券状态变更为未发货状态
        ExchangeTicketParam exchangeTicketParam = new ExchangeTicketParam();
        exchangeTicketParam.setId(exchangeTicket.getId());
        exchangeTicketParam.setIsSend(false);
        exchangeTicketParam.setSendDate(null);
        return this.update(exchangeTicketParam);
    }

    /**
     * 通过兑换券号码查询详情信息
     *
     * @param ticketNo
     * @return
     */
    public ExchangeTicket findByTicketNo(String ticketNo) {
        ticketNo = ticketNo.toUpperCase();
        QueryWrapper<ExchangeTicket> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ExchangeTicket::getTicketNo, ticketNo);
        return this.baseMapper.selectOne(queryWrapper);
    }

    /**
     * 通过兑换券号查询被封装的兑换券详情
     * @param ticketNo
     * @return
     */
    public ExchangeTicketResult findResultByTicketNo(String ticketNo) {
        ticketNo = ticketNo.toUpperCase();
        ExchangeTicket exchangeTicket = this.findByTicketNo(ticketNo);
        return this.setObjectProperty(exchangeTicket);
    }
    /**
     * 查询兑换券详情信息
     *
     * @param param
     * @return
     */
    public ExchangeTicketResult findBySpec(ExchangeTicketParam param) {
        ExchangeTicket exchangeTicket = this.getById(param.getId());
        return this.setObjectProperty(exchangeTicket);
    }


    /**
     * 通过兑换码属性编号查询兑换券列表信息
     * @param specificationId
     * @return
     */
    public List<ExchangeTicket> findListBySpecificationId(Long specificationId) {
        QueryWrapper<ExchangeTicket> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ExchangeTicket::getSpecificationId, specificationId);
        return this.baseMapper.selectList(queryWrapper);
    }
    /**
     * 查询兑换券列表信息
     *
     * @param param
     * @return
     */
    public List<ExchangeTicketResult> findListBySpec(ExchangeTicketParam param) {
        ExchangeTicket entity = getEntity(param);
        QueryWrapper<ExchangeTicket> queryWrapper = new QueryWrapper<>(entity);
        List<ExchangeTicket> exchangeTicketList = this.baseMapper.selectList(queryWrapper);
        List<ExchangeTicketResult> exchangeTicketResultList = new ArrayList<>();
        for (ExchangeTicket exchangeTicket: exchangeTicketList) {
            exchangeTicketResultList.add(this.setObjectProperty(exchangeTicket));
        }
        return exchangeTicketResultList;
    }

    /**
     * 分页查询兑换券列表信息 查询条件兑换券名称 兑换券规格内容 兑换券码
     *
     * @param param
     * @return
     */
    public WTPageResponse findPageBySpec(ExchangeTicketParam param) {
        Page pageContext = getPageContext();
        QueryWrapper<ExchangeTicketSpecification> exchangeTicketSpecificationQueryWrapper = new QueryWrapper<>();
        //兑换券商品名称
        if (StringUtil.isNotEmpty(param.getGoodName())) {
            exchangeTicketSpecificationQueryWrapper.lambda().eq(ExchangeTicketSpecification::getGoodName, param.getGoodName());
        }
        //兑换券名称
        if (StringUtil.isNotEmpty(param.getTicketName())) {
            exchangeTicketSpecificationQueryWrapper.lambda().eq(ExchangeTicketSpecification::getTicketName, param.getTicketName());
        }
        //选择出兑换券类型
        List<ExchangeTicketSpecification> exchangeTicketSpecificationList = this.exchangeTicketSpecificationService.getBaseMapper().selectList(exchangeTicketSpecificationQueryWrapper);
        List<ExchangeTicketResult> list = new ArrayList<>();
        if (exchangeTicketSpecificationList.size() != 0) {
            for (ExchangeTicketSpecification exchangeTicketSpecification: exchangeTicketSpecificationList) {
                Long id = exchangeTicketSpecification.getId();
                param.setSpecificationId(id);
                ExchangeTicket entity = getEntity(param);
                QueryWrapper<ExchangeTicket> queryWrapper = new QueryWrapper<>(entity);
                List<ExchangeTicket> exchangeTicketList = this.baseMapper.selectList(queryWrapper);
                for (ExchangeTicket exchangeTicket: exchangeTicketList) {
                    ExchangeTicketResult exchangeTicketResult = this.setObjectProperty(exchangeTicket);
                    list.add(exchangeTicketResult);
                }
            }
        } else {
            ExchangeTicket entity = getEntity(param);
            QueryWrapper<ExchangeTicket> queryWrapper = new QueryWrapper<>(entity);
            List<ExchangeTicket> exchangeTicketList = this.baseMapper.selectList(queryWrapper);
            for (ExchangeTicket exchangeTicket: exchangeTicketList) {
                ExchangeTicketResult exchangeTicketResult = this.setObjectProperty(exchangeTicket);
                list.add(exchangeTicketResult);
            }
        }
        IPage page = this.page(pageContext, new QueryWrapper<>());
        page.setRecords(list);
        page.setTotal(list.size());
        return WTPageFactory.createPageInfo(page);
    }

    /**
     * 设置返回对象属性
     * @param exchangeTicket
     * @return
     */
    private ExchangeTicketResult setObjectProperty(ExchangeTicket exchangeTicket) {
        if (exchangeTicket == null) {
            return null;
        }
        ExchangeTicketResult exchangeTicketResult = EntityConvertUtils.convertAToB(exchangeTicket, ExchangeTicketResult.class);
        ExchangeTicketSpecification exchangeTicketSpecification = this.exchangeTicketSpecificationService.getById(exchangeTicketResult.getSpecificationId());
        exchangeTicketResult.setGoodName(exchangeTicketSpecification.getGoodName());
        exchangeTicketResult.setTicketName(exchangeTicketSpecification.getTicketName());
        exchangeTicketResult.setGoodNum(exchangeTicketSpecification.getGoodNum());
        exchangeTicketResult.setGoodPic(exchangeTicketSpecification.getGoodPic());
        exchangeTicketResult.setSpecification(exchangeTicketSpecification.getSpecification());
        exchangeTicketResult.setGmtCreated(exchangeTicketSpecification.getGmtCreated());
        exchangeTicketResult.setExpiryDate(exchangeTicketSpecification.getExpiryDate());
        return exchangeTicketResult;
    }

    private Serializable getKey(ExchangeTicketParam param) {
        return param.getId();
    }

    private Page getPageContext() {
        return WTPageFactory.defaultPage();
    }

    private ExchangeTicket getOldEntity(ExchangeTicketParam param) {
        return this.getById(getKey(param));
    }

    private ExchangeTicket getEntity(ExchangeTicketParam param) {
        param = EntityConvertUtils.setNullValue(param);
        ExchangeTicket entity = new ExchangeTicket();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
