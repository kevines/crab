package com.wentuo.crab.modular.mini.service.appuser;


import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wentuo.crab.core.common.page.WTPageFactory;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.modular.mini.entity.appuser.AppUserAddress;
import com.wentuo.crab.modular.mini.mapper.appuser.AppUserAddressMapper;
import com.wentuo.crab.modular.mini.model.param.appuser.AppUserAddressParam;
import com.wentuo.crab.modular.mini.model.result.appuser.AppUserAddressResult;
import com.wentuo.crab.util.EntityConvertUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户收获地址表 服务实现类
 * </p>
 *
 * @author wangbencheng
 * @since 2019-10-31
 */
@Service
public class AppUserAddressService extends ServiceImpl<AppUserAddressMapper, AppUserAddress> {

    /**
     * 添加收货地址
     * @param param
     */
    public void add(AppUserAddressParam param){
        AppUserAddress entity = getEntity(param);
        this.save(entity);
    }

    /**
     * 删除收货地址，param中主键id必须传入
     * @param param
     */
    public void delete(AppUserAddressParam param){
        this.removeById(getKey(param));
    }

    /**
     * 更新收获地址， param中必须传入主键id
     * @param param
     */
    public void update(AppUserAddressParam param){
        AppUserAddress oldEntity = getOldEntity(param);
        AppUserAddress newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    /**
     * 查询用户收获地址详情，传入主键id,不能为空
     * @param param
     * @return
     */
    public AppUserAddressResult findBySpec(AppUserAddressParam param){
        AppUserAddress appUserAddress = this.getById(param.getId());
        AppUserAddressResult appUserAddressResult = EntityConvertUtils.convertAToB(appUserAddress, AppUserAddressResult.class);
        return appUserAddressResult;
    }

    /**
     * 查询用户收货地址
     * @param param
     * @return
     */
    public List<AppUserAddressResult> findListBySpec(AppUserAddressParam param){
        AppUserAddress entity = getEntity(param);
        QueryWrapper<AppUserAddress> queryWrapper = new QueryWrapper(entity);
        List<AppUserAddress> addressList = this.baseMapper.selectList(queryWrapper);
        return EntityConvertUtils.convertAListToBList(addressList, AppUserAddressResult.class);
    }

    /**
     * 分页查询用户收获地址
     * @param param
     * @return
     */
    public WTPageResponse findPageBySpec(AppUserAddressParam param){
        Page pageContext = getPageContext();
        AppUserAddress entity = getEntity(param);
        QueryWrapper<AppUserAddress> objectQueryWrapper = new QueryWrapper<>(entity);
        IPage page = this.page(pageContext, objectQueryWrapper);
        return WTPageFactory.createPageInfo(page);
    }

    private Serializable getKey(AppUserAddressParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return WTPageFactory.defaultPage();
    }

    private AppUserAddress getOldEntity(AppUserAddressParam param) {
        return this.getById(getKey(param));
    }

    private AppUserAddress getEntity(AppUserAddressParam param) {
        param = EntityConvertUtils.setNullValue(param);
        AppUserAddress entity = new AppUserAddress();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
