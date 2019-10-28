package com.wentuo.crab.modular.mini.service.appuser;


import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wentuo.crab.core.common.page.WTPageFactory;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.core.util.RedisUtil;
import com.wentuo.crab.enums.SexTypeEnum;
import com.wentuo.crab.enums.WechatLoginTypeEnum;
import com.wentuo.crab.modular.mini.entity.appuser.AppUser;
import com.wentuo.crab.modular.mini.mapper.appuser.AppUserMapper;
import com.wentuo.crab.modular.mini.model.param.appuser.AppUserParam;
import com.wentuo.crab.modular.mini.model.result.appuser.AppUserResult;
import com.wentuo.crab.util.EntityConvertUtils;
import com.wentuo.crab.util.MD5;
import com.wentuo.crab.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

import static cn.hutool.core.date.DateTime.now;

/**
 * 服务实现类
 * @author wangbencheng
 * @since 2019-08-14
 */
@Service
public class AppUserService extends ServiceImpl<AppUserMapper, AppUser> {

    private static final Logger logger = LoggerFactory.getLogger(AppUserService.class);


    /*
     * 功能描述: 添加app用户
     * @author wangbencheng
     * @since 2019/8/14 21:59
     * @param param
     * @return void
     */
    public void add(AppUserParam param) {
        AppUser entity = getEntity(param);
        this.save(entity);
    }

    /**
     * 功能描述: 通过用户主键查询用户id
     * @author wangbencheng
     * @since 2019/8/14 17:57
     * @param userId
     * @return com.wentuo.bcs.mini.entity.appuser.AppUser
     */
    public AppUser queryUserByUserId(String userId) {
        QueryWrapper<AppUser> queryWrapper = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(userId)) {
            queryWrapper.lambda().eq(AppUser::getUserId, userId);
            return this.baseMapper.selectOne(queryWrapper);
        }
        return null;
    }

    /**
     * 功能描述: 更新app用户信息
     * @author wangbencheng
     * @since 2019/8/16 17:10
     * @param param
     * @param userId
     * @return
     */
    public WTResponse<Boolean> update(AppUserParam param, String userId) {
        UpdateWrapper<AppUser> updateWrapper = new UpdateWrapper<>();
        if (StringUtil.isNotEmpty(userId)) {
            updateWrapper.lambda().eq(AppUser::getUserId, param.getUserId());
        }
        AppUser appUser = this.queryUserByUserId(userId);
        appUser = this.getAppuser(appUser, param);
        int result = this.baseMapper.update(appUser, updateWrapper);
        if (result == 1) {
            return new WTResponse<>(WTResponse.SUCCESS, "更新成功", Boolean.TRUE);
        } else {
            return new WTResponse<>(WTResponse.FAIL, "更新失败", Boolean.FALSE);
        }
    }

    /**
     * 更新app用户信息
     * @param param
     */
    public void updateUserInfo(AppUserParam param) {
        AppUser oldEntity = getOldEntity(param);
        AppUser newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    /**
     * 功能描述: 查询app用户个人信息
     * @author wangbencheng
     * @since 2019/8/14 17:57
     * @param param
     * @return com.wentuo.bcs.mini.model.result.appuser.AppUserResult
     */
    public AppUserResult findBySpec(AppUserParam param) {
        QueryWrapper<AppUser> queryWrapper = new QueryWrapper<>();
        queryWrapper = this.getQueryWrapper(queryWrapper, param);
        AppUser appUser = this.baseMapper.selectOne(queryWrapper);
        AppUserResult appUserResult = EntityConvertUtils.convertAToB(appUser, AppUserResult.class);
        return appUserResult;
    }

    /**
     * 功能描述: 查询用户列表
     * @author wangbencheng
     * @since 2019/8/14 17:58
     * @param param
     * @return java.util.List<com.wentuo.bcs.mini.model.result.appuser.AppUserResult>
     */
    public List<AppUserResult> findListBySpec(AppUserParam param) {
        QueryWrapper<AppUser> queryWrapper = new QueryWrapper<>();
        queryWrapper = this.getQueryWrapper(queryWrapper, param);
        List<AppUser> appUserList = this.baseMapper.selectList(queryWrapper);
        List<AppUserResult> appUserResultList = EntityConvertUtils.convertAListToBList(appUserList, AppUserResult.class);
        return appUserResultList;
    }

    /**
     * 功能描述: 分页查询app用户信息列表
     * @author wangbencheng
     * @since 2019/8/14 18:18
     * @param param
     * @return com.wentuo.bcs.core.common.page.WTResponse
     */
    public WTResponse findPageBySpec(AppUserParam param) {
        Page pageContext = getPageContext();
        QueryWrapper<AppUser> queryWrapper = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(param.getUserId())) {
            queryWrapper.lambda().eq(AppUser::getUserId, param.getUserId());
        }
        if (StringUtil.isNotEmpty(param.getPhone())) {
            queryWrapper.lambda().eq(AppUser::getPhone, param.getPhone());
        }
        if (StringUtil.isNotEmpty(param.getQq())) {
            queryWrapper.lambda().eq(AppUser::getQq, param.getQq());
        }
        if (StringUtil.isNotEmpty(param.getWx())) {
            queryWrapper.lambda().eq(AppUser::getWx, param.getWx());
        }
        if (StringUtil.isNotEmpty(param.getSex())) {
            queryWrapper.lambda().eq(AppUser::getSex, param.getSex());
        }
        if (StringUtil.isNotEmpty(param.getName())) {
            queryWrapper.lambda().eq(AppUser::getName, param.getName());
        }
        IPage<AppUser> page = this.page(pageContext, queryWrapper);
        return WTPageFactory.createPageInfo(page);
    }


    /**
     * 功能描述: 用户通过微信进行登录
     * @author wangbencheng
     * @since 2019/8/14 21:15
     * @param valueByType 登录方式
     * @param param 用户相关信息参数
     * @return com.wentuo.bcs.mini.entity.appuser.AppUser
     */
    public AppUser loginWeChat(Integer valueByType, AppUserParam param) {
        param.setUserType("1");  //默认为普通用户
        param.setIsDeleted(false);  //，默认未逻辑删除
        QueryWrapper<AppUser> queryWrapper = new QueryWrapper<>();
        UpdateWrapper<AppUser> updateWrapper = new UpdateWrapper<>();
        if (StringUtil.isNotEmpty(param.getOpenId())) {
            queryWrapper.lambda().eq(AppUser::getOpenId, param.getOpenId());
        }
        if (StringUtil.isNotEmpty(param.getOpenIdH5())) {
            queryWrapper.lambda().eq(AppUser::getOpenIdH5, param.getOpenIdH5());
        }
        if (StringUtil.isNotEmpty(param.getOpenIdMini())) {
            queryWrapper.lambda().eq(AppUser::getOpenIdMini, param.getOpenIdMini());
        }
        if (StringUtil.isNotEmpty(param.getUnionId())) {
            queryWrapper.lambda().eq(AppUser::getUnionId, param.getUnionId());
        }
        AppUser appUserResult = this.baseMapper.selectOne(queryWrapper);
        if (appUserResult != null) {
            updateWrapper.lambda().eq(AppUser::getUserId, appUserResult.getUserId());
            //判断是否一致,不一致修改
            switch (valueByType) {
                case 1:
                    //Mini
                    if (appUserResult.getOpenIdMini() == null || !appUserResult.getOpenIdMini().equals(param.getOpenIdMini())) {
                        updateWrapper.lambda().set(AppUser::getOpenIdMini, param.getOpenIdMini());
                        this.update(updateWrapper);
                    }
                    //用户头像
                    if (StringUtil.isEmpty(appUserResult.getWxPhoto()) || !appUserResult.getWxPhoto().equals(param.getWxPhoto())) {
                        updateWrapper.lambda().set(AppUser::getWxPhoto, param.getWxPhoto());
                        this.update(updateWrapper);
                    }
                    appUserResult.setWxPhoto(param.getWxPhoto());
                    appUserResult.setOpenIdMini(param.getOpenIdMini());
                    break;
                case 2:
                    //app
                    if (appUserResult.getOpenId() == null || !appUserResult.getOpenId().equals(param.getOpenId())) {
                        updateWrapper.lambda().set(AppUser::getOpenId, param.getOpenId());
                        this.update(updateWrapper);
                    }
                    appUserResult.setOpenId(param.getOpenId());
                    break;
                case 3:
                    //H5
                    if (appUserResult.getOpenIdH5() == null || !appUserResult.getOpenIdH5().equals(param.getOpenIdH5())) {
                        updateWrapper.lambda().set(AppUser::getOpenIdH5, param.getOpenIdH5());
                        this.update(updateWrapper);
                    }
                    appUserResult.setOpenIdH5(param.getOpenIdH5());
                    break;
                default:
                    break;
            }
            //修改昵称
            if (StringUtils.isNotBlank(param.getNickName()) || StringUtils.isNotBlank(param.getName())) {
                if (!appUserResult.getNickName().equals(param.getNickName())) {
                    String name = StringUtils.isNotBlank(param.getNickName()) ? param.getNickName() : param.getName();
                    updateWrapper.lambda().set(AppUser::getNickName, name);
                    this.update(updateWrapper);
                    appUserResult.setNickName(name);
                }
            }
            return appUserResult;
        }
        //没有unionId的,根据手机号进行判断(判断当前unionId是空或者为UserId则覆盖当前UnionId)查询不到后新增用户数据
        else {
            if (valueByType.equals(WechatLoginTypeEnum.APP.getValue())) {
                QueryWrapper<AppUser> appUserQueryWrapperByPhone = new QueryWrapper<>();
                String phone = param.getPhone();
                appUserQueryWrapperByPhone.lambda().eq(AppUser::getPhone, phone);
                appUserQueryWrapperByPhone.lambda().eq(AppUser::getIsDeleted, 0);
                appUserQueryWrapperByPhone.last("limit 1");
                AppUser appUser = this.getOne(appUserQueryWrapperByPhone);
                if (appUser == null) {
                    logger.info("---------------" + JSON.toJSONString(param));
                    if (StringUtils.isBlank(param.getPhone())) {
                        return null;
                    }
                    //如果手机号找不到，则新增用户
                    return addUser(param);
                } else {
                    //如果找到手机号了，则判断该数据下unionId是否有字段，没有覆盖，存在则提示异常，但都返回根据手机号找到的数据
                    if (StringUtils.isBlank(appUser.getUnionId()) || appUser.getUserId().equals(appUser.getUnionId())) {
                        UpdateWrapper<AppUser> appUserUpdateUnionIdWrapper = new UpdateWrapper<>();
                        appUserUpdateUnionIdWrapper.lambda().set(AppUser::getUnionId, param.getUnionId());
                        appUserUpdateUnionIdWrapper.lambda().eq(AppUser::getUserId, appUser.getUserId());
                        this.update(appUserUpdateUnionIdWrapper);
                        return appUser;
                    }
                    return appUser;
                }
            } else {
                //mini 和 h5 都先创建账号，再对手机号进行操作
                return addUser(param);
            }
        }
    }

    /**
     * 功能描述: 更新用户微信头像
     * @author wangbencheng
     * @since 2019/8/14 23:44
     * @param userId
     * @param fileName
     * @return
     */
    public Boolean updateWxPhoto(String userId, String fileName) {
        UpdateWrapper<AppUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(AppUser::getGmtModified, now());
        updateWrapper.lambda().set(AppUser::getWxPhoto, fileName);
        updateWrapper.lambda().eq(AppUser::getUserId, userId);
        if (this.update(updateWrapper)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param sex
     * @return
     */
    private String handleSex(String sex) {
        if ("1".equals(sex)) {
            sex = "男";
        } else if ("2".equals(sex)) {
            sex = "女";
        } else {
            sex = "未知";
        }
        return sex;
    }


    /**
     * 功能描述: 设置用户实体类
     * @author wangbencheng
     * @since 2019/8/14 21:50
     * @param appUserParam 用户实体类参数
     * @return
     */
    private AppUser addUser(AppUserParam appUserParam) {
        String userId = RedisUtil.getServiceKeyHaveDateByType("AU");
        appUserParam.setUserId(userId);
        appUserParam.setSex(SexTypeEnum.getCodeByName(handleSex(appUserParam.getSex())));
        appUserParam.setPwd(MD5.GetMD5Code("123456"));
        appUserParam.setPhoto("https://daikin-mini.oss-cn-hangzhou.aliyuncs.com/daikin-logo.jpeg");
        appUserParam.setUserType("1");   //默认是普通用户(该用户部分商品可分销)
        appUserParam.setCreator(userId);
        appUserParam.setModifier(userId);
        this.add(appUserParam);   //添加新用户
        return EntityConvertUtils.convertAToB(appUserParam, AppUser.class);
    }

    /**
     * 功能描述: 获取用户相关的查询QueryWrapper
     * @author wangbencheng
     * @since 2019/8/14 17:58
     * @param param
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<com.wentuo.bcs.mini.entity.appuser.AppUser>
     */
    public QueryWrapper<AppUser> getQueryWrapper(QueryWrapper<AppUser> queryWrapper, AppUserParam param) {
        if (param.getId() != null) {
            queryWrapper.lambda().eq(AppUser::getId, param.getId());
        }
        if (StringUtil.isNotEmpty(param.getUserId())) {
            queryWrapper.lambda().eq(AppUser::getUserId, param.getUserId());
        }
        if (StringUtil.isNotEmpty(param.getName())) {
            queryWrapper.lambda().eq(AppUser::getName, param.getName());
        }
        if (StringUtil.isNotEmpty(param.getNickName())) {
            queryWrapper.lambda().eq(AppUser::getNickName, param.getNickName());
        }
        if (StringUtil.isNotEmpty(param.getRealName())) {
            queryWrapper.lambda().eq(AppUser::getRealName, param.getRealName());
        }
        if (StringUtil.isNotEmpty(param.getSex())) {
            queryWrapper.lambda().eq(AppUser::getSex, param.getSex());
        }
        //传入详细的用户日期
        if (param.getBirthday() != null) {
            queryWrapper.lambda().eq(AppUser::getBirthday, param.getBirthday());
        }
        //按照时间区间查询用户生日日期
        if (param.getBirthBeginTime() != null && param.getBirthEndTime() != null) {
            queryWrapper.lambda().between(AppUser::getBirthday, param.getBirthBeginTime(), param.getBirthEndTime());
        }
        if (StringUtil.isNotEmpty(param.getPwd())) {
            queryWrapper.lambda().eq(AppUser::getPwd, param.getPwd());
        }
        if (StringUtil.isNotEmpty(param.getPayPwd())) {
            queryWrapper.lambda().eq(AppUser::getPayPwd, param.getPayPwd());
        }
        if (StringUtil.isNotEmpty(param.getUserDistrictCode())) {
            queryWrapper.lambda().eq(AppUser::getUserDistrictCode, param.getUserDistrictCode());
        }
        if (StringUtil.isNotEmpty(param.getOpenId())) {
            queryWrapper.lambda().eq(AppUser::getOpenId, param.getOpenId());
        }
        if (StringUtil.isNotEmpty(param.getOpenIdH5())) {
            queryWrapper.lambda().eq(AppUser::getOpenIdH5, param.getOpenIdH5());
        }
        if (StringUtil.isNotEmpty(param.getOpenIdMini())) {
            queryWrapper.lambda().eq(AppUser::getOpenIdMini, param.getOpenIdMini());
        }
        if (StringUtil.isNotEmpty(param.getUnionId())) {
            queryWrapper.lambda().eq(AppUser::getUnionId, param.getUnionId());
        }
        if (param.getIsExam() != null) {
            queryWrapper.lambda().eq(AppUser::getIsExam, param.getIsExam());
        }
        if (param.getGmtCreated() != null) {
            queryWrapper.lambda().eq(AppUser::getGmtCreated, param.getGmtCreated());
        }
        if (param.getGmtStartDate() != null && param.getGmtEndDate() != null) {
            queryWrapper.lambda().between(AppUser::getGmtCreated, param.getGmtStartDate(), param.getGmtEndDate());
        }
        if (param.getGmtModified() != null) {
            queryWrapper.lambda().eq(AppUser::getGmtModified, param.getGmtModified());
        }
        if (param.getGmtBeginTime() != null && param.getGmtEndTime() != null) {
            queryWrapper.lambda().between(AppUser::getGmtModified, param.getGmtBeginTime(), param.getGmtEndTime());
        }
        if (param.getIsDeleted() != null) {
            queryWrapper.lambda().eq(AppUser::getIsDeleted, param.getIsDeleted());
        }
        return queryWrapper;
    }

    /**
     * 功能描述: 获取更新时的用户实体类
     * @author wangbencheng
     * @since 2019/8/16 17:21
     * @param appUser
     * @param param
     * @return
     */
    public AppUser getAppuser(AppUser appUser, AppUserParam param) {
        if (StringUtil.isNotEmpty(param.getName())) {
            appUser.setName(param.getName());
        }
        if (StringUtil.isNotEmpty(param.getNickName())) {
            appUser.setNickName(param.getNickName());
        }
        if (StringUtil.isNotEmpty(param.getRealName())) {
            appUser.setRealName(param.getRealName());
        }
        if (StringUtil.isNotEmpty(param.getSex())) {
            appUser.setSex(param.getSex());
        }
        //传入详细的用户日期
        if (param.getBirthday() != null) {
            appUser.setBirthday(param.getBirthday());
        }
        if (StringUtil.isNotEmpty(param.getPwd())) {
            appUser.setPwd(param.getPwd());
        }
        if (StringUtil.isNotEmpty(param.getPayPwd())) {
            appUser.setPayPwd(param.getPayPwd());
        }
        if (StringUtil.isNotEmpty(param.getUserDistrictCode())) {
            appUser.setUserDistrictCode(param.getUserDistrictCode());
        }
        if (StringUtil.isNotEmpty(param.getOpenId())) {
            appUser.setOpenId(param.getOpenId());
        }
        if (StringUtil.isNotEmpty(param.getOpenIdH5())) {
            appUser.setOpenIdH5(param.getOpenIdH5());
        }
        if (StringUtil.isNotEmpty(param.getOpenIdMini())) {
            appUser.setOpenIdMini(param.getOpenIdMini());
        }
        if (StringUtil.isNotEmpty(param.getUnionId())) {
            appUser.setUnionId(param.getUnionId());
        }
        if (param.getIsExam() != null) {
            appUser.setIsExam(param.getIsExam());
        }
        if (param.getGmtModified() != null) {
            appUser.setGmtModified(param.getGmtModified());
        }
     return appUser;
    }


    private Serializable getKey(AppUserParam param) {
        return param.getId();
    }

    private AppUser getOldEntity(AppUserParam param) {
        if (param.getId() == null) {
            param.setId(queryUserByUserId(param.getUserId()).getId());
        }
        return this.getById(getKey(param));
    }

    private AppUser getEntity(AppUserParam param) {
        param = EntityConvertUtils.setNullValue(param);
        AppUser entity = new AppUser();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    private Page getPageContext() {
        return WTPageFactory.defaultPage();
    }
}
