package com.wentuo.crab.modular.mini.mapper.appuser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wentuo.crab.modular.mini.entity.appuser.AppUserRoleRef;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 功能描述:
 * @author wangbencheng
 * @since 2019/8/14 22:29
 */
@Component
public interface AppUserRoleRefMapper extends BaseMapper<AppUserRoleRef> {

    /**
     * 根据userId查找角色
     * @param userId
     * @return
     */
    List<AppUserRoleRef> selectByUserId(@Param("userId") String userId);
}
