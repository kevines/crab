package com.wentuo.crab.modular.mini.mapper.ticket;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wentuo.crab.modular.mini.entity.ticket.ExchangeTicketRecord;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 兑换交易记录表(发货用) Mapper 接口
 * </p>
 *
 * @author wangbencheng
 * @since 2019-10-31
 */
public interface ExchangeTicketRecordMapper extends BaseMapper<ExchangeTicketRecord> {

    /**
     * 根据蟹券编号删除蟹券兑换记录
     * @param ticketNo
     * @return
     */
    Integer deleteTicketRecordByTicketNo(@Param("ticketNo")String ticketNo);

}
