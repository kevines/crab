package com.wentuo.crab.modular.system.service;


import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.util.MD5Util;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wentuo.crab.core.common.constant.Const;
import com.wentuo.crab.core.common.constant.state.ManagerStatus;
import com.wentuo.crab.core.common.exception.BizExceptionEnum;
import com.wentuo.crab.core.common.node.MenuNode;
import com.wentuo.crab.core.common.page.WTPageFactory;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.shiro.ShiroKit;
import com.wentuo.crab.core.shiro.ShiroUser;
import com.wentuo.crab.core.shiro.service.UserAuthService;
import com.wentuo.crab.core.util.ApiMenuFilter;
import com.wentuo.crab.core.util.RedisUtil;
import com.wentuo.crab.modular.mini.entity.appuser.AppUser;
import com.wentuo.crab.modular.mini.service.appuser.AppUserService;
import com.wentuo.crab.modular.system.entity.User;
import com.wentuo.crab.modular.system.mapper.UserMapper;
import com.wentuo.crab.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    private MenuService menuService;

    @Resource
    private AppUserService appUserService;

    @Resource
    private UserAuthService userAuthService;

    @Resource
    private RoleService roleService;

    /**
     * 功能描述: 添加管理员用户
     * @author wangbencheng
     * @since 2019/8/14 17:37
     * @param user
     * @return void
     */
    public void addUser(User user) {
        // 判断账号是否重复
        User theUser = this.getByAccount(user.getAccount());
        if (theUser != null) {
            throw new ServiceException(BizExceptionEnum.ERROR.getCode(), "账号重复");
        }
        // 完善账号信息
        String password = MD5Util.encrypt(user.getPassword());  //md5加密
        user.setPassword(password);
        user.setUserId(RedisUtil.getServiceKeyHaveDateByType(""));
        this.save(user);
    }

    /**
     * 功能描述: 删除管理员用户
     * @author wangbencheng
     * @since 2019/8/14 17:36
     * @param userId
     * @return void
     */
    public void deleteUser(Long userId) {
        //不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new ServiceException(BizExceptionEnum.ERROR);
        }
        this.assertAuth(userId);
        this.setStatus(userId, ManagerStatus.DELETED.getCode());
    }



    /**
     * 功能描述: 修改管理员用户密码
     * @author wangbencheng
     * @since 2019/8/14 17:49
     * @param oldPassword
     * @param newPassword
     * @return com.wentuo.bcs.core.common.page.WTResponse
     */
    public WTPageResponse changePwd(String oldPassword, String newPassword) {
        Long userId = ShiroKit.getUserNotNull().getId();
        User user = this.getById(userId);
        oldPassword = MD5Util.encrypt(oldPassword);  //原密码
        newPassword = MD5Util.encrypt(newPassword);
        if (user.getPassword().equals(oldPassword)) {
            if (!user.getPassword().equals(newPassword)) {
                user.setPassword(newPassword);
                if (this.updateById(user)) {
                    return WTPageResponse.success("修改密码成功");
                }
                return WTPageResponse.success("修改密码失败");
            } else {
                return WTPageResponse.error("原密码与新密码不能相同");
            }
        }
        return WTPageResponse.error("原密码填写不对");
    }

    /**
     * 功能描述: 获取用户菜单列表
     * @author wangbencheng
     * @since 2019/8/14 17:36
     * @param roleList
     * @return java.util.List<com.wentuo.bcs.core.common.node.MenuNode>
     */
    public List<MenuNode> getUserMenuNodes(List<Long> roleList) {
        if (roleList == null || roleList.size() == 0) {
            return new ArrayList<>();
        } else {
            List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
            List<MenuNode> titles = MenuNode.buildTitle(menus);
            return ApiMenuFilter.build(titles);
        }
    }

    /**
     * 功能描述: 根据条件查询用户列表
     * @author wangbencheng
     * @since 2019/8/15 11:39
     * @param user
     * @return
     */
    public WTPageResponse selectUsers(User user) {
        Page pageContext = WTPageFactory.defaultPage();
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        if(StringUtil.isNotEmpty(user.getRoleName())){
            objectQueryWrapper.like("ACCOUNT",user.getRoleName());
        }
        objectQueryWrapper.lambda().orderByDesc(User::getCreateTime);
        IPage<User> page = this.page(pageContext, objectQueryWrapper);
        page.getRecords().forEach(o -> {
            o.setRoleName(roleService.getById(Long.valueOf(o.getRoleId())).getName());
        });
        return WTPageFactory.createPageInfo(page);
    }

    /**
     * 功能描述: 设置用户角色
     * @author wangbencheng
     * @since 2019/8/15 11:39
     * @param userId
     * @param roleIds
     * @return
     */
    public int setRoles(Long userId, String roleIds) {
        return this.baseMapper.setRoles(userId, roleIds);
    }

    /**
     * 功能描述: 刷新用户状态
     * @author wangbencheng
     * @since 2019/8/14 17:36
     * @param
     * @return void
     */
    public void refreshCurrentUser() {
        ShiroUser user = ShiroKit.getUserNotNull();
        Long id = user.getId();
        AppUser currentUser = appUserService.getById(id);
        ShiroUser shiroUser = userAuthService.shiroUser(currentUser);
        ShiroUser lastUser = ShiroKit.getUser();
        BeanUtil.copyProperties(shiroUser, lastUser);
    }

    /**
     * 功能描述: 通过账号查询账户信息
     * @author wangbencheng
     * @since 2019/8/14 17:36
     * @param account
     * @return com.wentuo.bcs.system.entity.User
     */
    public User getByAccount(String account) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(User::getAccount, account);
        return this.getOne(userQueryWrapper);
    }

    /**
     * 功能描述: 判断当前登录的用户是否有操作这个用户的权限
     * @author wangbencheng
     * @since 2019/8/14 17:35
     * @param userId
     * @return void
     */
    public void assertAuth(Long userId) {
        if (ShiroKit.isAdmin()) {
            return;
        }
        List<Long> deptDataScope = ShiroKit.getDeptDataScope();
        User user = this.getById(userId);
        Long deptId = user.getDeptId();
        if (deptDataScope.contains(deptId)) {
            return;
        } else {
            throw new ServiceException(BizExceptionEnum.ERROR);
        }
    }

    /**
     * 功能描述: 修改用户状态
     * @author wangbencheng
     * @since 2019/8/14 17:35
     * @param userId 用户id
     * @param status 用户状态
     * @return int
     */
    private int setStatus(Long userId, String status) {
        return this.baseMapper.setStatus(userId, status);
    }


}
