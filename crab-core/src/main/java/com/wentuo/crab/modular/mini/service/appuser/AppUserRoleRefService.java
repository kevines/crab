package com.wentuo.crab.modular.mini.service.appuser;


import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wentuo.crab.core.common.page.WTPageFactory;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.enums.RoleEnum;
import com.wentuo.crab.modular.mini.entity.appuser.AppUserRoleRef;
import com.wentuo.crab.modular.mini.mapper.appuser.AppUserRoleRefMapper;
import com.wentuo.crab.modular.mini.model.param.appuser.AppUserRoleRefParam;
import com.wentuo.crab.modular.mini.model.result.appuser.AppUserRoleRefResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuwenbo
 * @since 2019-03-06
 */
@Service
public class AppUserRoleRefService extends ServiceImpl<AppUserRoleRefMapper, AppUserRoleRef> {

    @Resource
    private AppUserRoleRefMapper appUserRoleRefMapper;

    public void add(String userId, String roleCode) {
        AppUserRoleRef entity = new AppUserRoleRef();
        entity.setUserId(userId);
        entity.setRoleCode(roleCode);
        entity.setGmtCreated(new Date());
        this.save(entity);
    }

    public void delete(String userId, String roleCode) {
        AppUserRoleRef entity = new AppUserRoleRef();
        entity.setUserId(userId);
        entity.setRoleCode(roleCode);
        QueryWrapper<AppUserRoleRef> busniessApproveQueryWrapper = new QueryWrapper<>(entity);
        this.remove(busniessApproveQueryWrapper);
    }

    public void update(AppUserRoleRefParam param) {
        AppUserRoleRef oldEntity = getOldEntity(param);
        AppUserRoleRef newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    public AppUserRoleRefResult findBySpec(AppUserRoleRefParam param) {
        return null;
    }

    public List<AppUserRoleRef> findListBySpec(AppUserRoleRefParam param) {
        AppUserRoleRef appUserRoleRef = getEntity(param);
        QueryWrapper<AppUserRoleRef> objectQueryWrapper = new QueryWrapper<>(appUserRoleRef);
        List<AppUserRoleRef> appUserRoleRefs = this.baseMapper.selectList(objectQueryWrapper);
        return appUserRoleRefs;
    }

    public WTResponse findPageBySpec(AppUserRoleRefParam param) {
        Page pageContext = getPageContext();
        QueryWrapper<AppUserRoleRef> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return WTPageFactory.createPageInfo(page);
    }

    /**
     * 根据userId查找角色
     *
     * @param userId
     * @return
     */
    public List<AppUserRoleRef> selectByUserId(String userId) {
        return appUserRoleRefMapper.selectByUserId(userId);
    }

    /**
     * 根据userId查找角色
     *
     * @param userId
     * @return
     */
    public String selectRolesByUserId(String userId) {
        List<AppUserRoleRef> list = appUserRoleRefMapper.selectByUserId(userId);
        if (list.size() > 0) {
            List<String> strings = list.stream().map(AppUserRoleRef::getRoleCode).collect(Collectors.toList());
            return String.join(",", strings);
        }
        return null;
    }

    /**
     * 根据userIds查找角色
     *
     * @return
     */
    public Map<String, String> selectRolesByUserIds(List<String> userIds) {
        QueryWrapper<AppUserRoleRef> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(AppUserRoleRef::getUserId, userIds);
        List<AppUserRoleRef> appUserRoleRefs = this.baseMapper.selectList(queryWrapper);
        Map<String, String> map = new HashMap<>();
        if (appUserRoleRefs.size() > 0) {
            Map<String, List<AppUserRoleRef>> maps = appUserRoleRefs.stream().collect(Collectors.groupingBy(AppUserRoleRef::getUserId));
            maps.forEach((x, y) -> {
                List<String> roleCodes = y.stream().map(AppUserRoleRef::getRoleCode).collect(Collectors.toList());
                List<String> roleNames = RoleEnum.getName(roleCodes);
                map.put(x, String.join(",", roleNames));
            });
        }
        return map;
    }

    private Serializable getKey(AppUserRoleRefParam param) {
        return param.getId();
    }

    private Page getPageContext() {
        return WTPageFactory.defaultPage();
    }

    private AppUserRoleRef getOldEntity(AppUserRoleRefParam param) {
        return this.getById(getKey(param));
    }

    private AppUserRoleRef getEntity(AppUserRoleRefParam param) {
        AppUserRoleRef entity = new AppUserRoleRef();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    public List<String> queryCodeByUserId(String roleCode) {
        QueryWrapper<AppUserRoleRef> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AppUserRoleRef::getRoleCode, roleCode);
        List<String> list = this.baseMapper.selectList(queryWrapper).stream().map(AppUserRoleRef::getUserId).collect(Collectors.toList());
        return list;
    }

    /**
     * 是否是商家
     *
     * @param userId
     * @return true-是合伙人 false-不是
     */
    public Boolean isBusiness(String userId) {
        List<AppUserRoleRef> lists = selectByUserId(userId);
        for (AppUserRoleRef appUserRoleRef : lists) {
            if (appUserRoleRef.getRoleCode().contains("Business")) {
                return true;
            }
        }
        return false;
    }

}
