/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wentuo.crab.core.shiro.service.impl;

import cn.stylefeng.roses.core.util.SpringContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wentuo.crab.core.common.constant.factory.ConstantFactory;
import com.wentuo.crab.core.shiro.ShiroKit;
import com.wentuo.crab.core.shiro.ShiroUser;
import com.wentuo.crab.core.shiro.service.UserAuthService;
import com.wentuo.crab.modular.mini.entity.appuser.AppUser;
import com.wentuo.crab.modular.mini.mapper.appuser.AppUserMapper;
import com.wentuo.crab.modular.system.entity.User;
import com.wentuo.crab.modular.system.mapper.MenuMapper;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class UserAuthServiceServiceImpl implements UserAuthService {

    @Autowired
    private AppUserMapper appUserMapper;

    @Autowired
    private MenuMapper menuMapper;

    public static UserAuthService me() {
        return SpringContextHolder.getBean(UserAuthService.class);
    }

    @Override
    public AppUser user(String account) {

        AppUser appUser = new AppUser();
        appUser.setPhone(account);
        QueryWrapper<AppUser> appUserQueryWrapper = new QueryWrapper<>(appUser);
        appUser = appUserMapper.selectOne(appUserQueryWrapper);

        // 账号不存在
        if (null == appUser) {
            throw new CredentialsException();
        }

        return appUser;
    }

    @Override
    public ShiroUser shiroUser(AppUser user) {

        ShiroUser shiroUser = ShiroKit.createShiroUser(user);
        //用户角色数组
        Long[] roleArray = null;//Convert.toLongArray(user.getRoleId());

        //获取用户角色列表
        List<Long> roleList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        for (Long roleId : roleArray) {
            roleList.add(roleId);
            roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId));
        }
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);

        return shiroUser;
    }

    @Override
    public List<String> findPermissionsByRoleId(Long roleId) {
        return menuMapper.getResUrlsByRoleId(roleId);
    }

    @Override
    public String findRoleNameByRoleId(Long roleId) {
        return ConstantFactory.me().getSingleRoleTip(roleId);
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {
        String credentials = user.getPassword();
        return  null;
    }

}
