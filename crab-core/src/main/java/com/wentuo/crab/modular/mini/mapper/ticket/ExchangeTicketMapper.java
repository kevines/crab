package com.wentuo.crab.modular.mini.mapper.ticket;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicket;
import com.wentuo.crab.modular.mini.model.result.ticket.ExchangeTicketResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 兑换券详情列表 Mapper 接口
 * </p>
 *
 * @author wangbencheng
 * @since 2019-10-31
 */
public interface ExchangeTicketMapper extends BaseMapper<ExchangeTicket> {

    /**
     * 分页关联蟹券表查询全部列表数据
     * @param ticketName
     * @param isExchange
     * @param isSend
     * @param ticketNo
     * @param current
     * @param size
     * @return
     */
    List<ExchangeTicketResult> selectTicketListPage(@Param("ticketName")String ticketName,
                                                    @Param("isExchange")Boolean isExchange,
                                                    @Param("isSend")Boolean isSend,
                                                    @Param("ticketNo")String ticketNo,
                                                    @Param("current") long current, @Param("size") long size);

    /**
     * 关联蟹券表查询全部列表数据数量
     * @param ticketName
     * @param isExchange
     * @param isSend
     * @param ticketNo
     * @return
     */
    Long selectTicketListCount(@Param("ticketName")String ticketName,
                               @Param("isExchange")Boolean isExchange,
                               @Param("isSend")Boolean isSend,
                               @Param("ticketNo")String ticketNo);


    /**
     * 根据蟹券属性编号删除蟹券列表
     * @param specificationId
     * @return
     */
    Integer deleteTicketBySpecificationId(@Param("specificationId")Long specificationId);

}
