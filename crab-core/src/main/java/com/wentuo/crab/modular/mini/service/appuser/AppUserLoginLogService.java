package com.wentuo.crab.modular.mini.service.appuser;

import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wentuo.crab.core.common.page.WTPageFactory;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.modular.mini.entity.appuser.AppUserLoginLog;
import com.wentuo.crab.modular.mini.mapper.appuser.AppUserLoginLogMapper;
import com.wentuo.crab.util.EntityConvertUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * app登录记录表 服务实现类
 * </p>
 *
 * @author wangbencheng
 * @since 2019-08-14
 */
@Service
public class AppUserLoginLogService extends ServiceImpl<AppUserLoginLogMapper, AppUserLoginLog> {

    //添加登录记录
    public void add(AppUserLoginLog appUserLoginLog){
        this.save(appUserLoginLog);
    }

    public void delete(AppUserLoginLog param){
        this.removeById(getKey(param));
    }

    public void update(AppUserLoginLog param){


        this.updateById(param);
    }

    //查看一条记录
    public AppUserLoginLog findBySpec(String userId){
        QueryWrapper<AppUserLoginLog> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(AppUserLoginLog::getUserId,userId).orderByDesc(AppUserLoginLog::getLoginTime).last("limit 1");
        return this.getOne(queryWrapper);
    }

    public List<AppUserLoginLog> findListBySpec(AppUserLoginLog param){
        return null;
    }

    public WTPageResponse findPageBySpec(AppUserLoginLog param){
        Page pageContext = getPageContext();
        QueryWrapper<AppUserLoginLog> objectQueryWrapper = new QueryWrapper<>(param);
        IPage page = this.page(pageContext, objectQueryWrapper);
        return WTPageFactory.createPageInfo(page);
    }

    private Serializable getKey(AppUserLoginLog param){
        return param.getId();
    }

    private Page getPageContext() {
        return WTPageFactory.defaultPage();
    }

    private AppUserLoginLog getOldEntity(AppUserLoginLog param) {
        return this.getById(getKey(param));
    }

    private AppUserLoginLog getEntity(AppUserLoginLog param) {
        param = EntityConvertUtils.setNullValue(param);
        AppUserLoginLog entity = new AppUserLoginLog();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    public List<AppUserLoginLog> queryByAppUserLogin(List<String> userIds){
        QueryWrapper<AppUserLoginLog> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().in(AppUserLoginLog::getUserId,userIds);
        return this.baseMapper.selectList(queryWrapper);
    }

}
