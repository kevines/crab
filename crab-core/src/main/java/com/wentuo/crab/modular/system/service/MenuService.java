package com.wentuo.crab.modular.system.service;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wentuo.crab.core.common.exception.BizExceptionEnum;
import com.wentuo.crab.core.common.node.MenuNode;
import com.wentuo.crab.core.common.node.ZTreeNode;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.listener.ConfigListener;
import com.wentuo.crab.core.util.RedisUtil;
import com.wentuo.crab.enums.ServiceKeyTypeEnum;
import com.wentuo.crab.modular.system.entity.Menu;
import com.wentuo.crab.modular.system.entity.Relation;
import com.wentuo.crab.modular.system.mapper.MenuMapper;
import com.wentuo.crab.modular.system.model.MenuResult;
import com.wentuo.crab.util.EntityConvertUtils;
import com.wentuo.crab.util.HttpContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu> {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RelationService relationService;

    /**
     * 添加菜单
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:59 PM
     */
    @Transactional
    public void addMenu(Menu menuDto) {

        if (ToolUtil.isOneEmpty(menuDto, menuDto.getName(), menuDto.getLevels(), menuDto.getUrl())) {
            throw new RequestEmptyException();
        }

        menuDto.setCreateUser(Long.valueOf(HttpContext.getUser().getUserId()));
        if (menuDto.getLevels() == 1) {
            menuDto.setPcode("0");
            menuDto.setCode(RedisUtil.getServiceKeyHaveDateByType(ServiceKeyTypeEnum.MENU.getValue()));
        }

        if (menuDto.getLevels() == 2) {
            menuDto.setCode(RedisUtil.getServiceKeyHaveDateByType(ServiceKeyTypeEnum.MENU2.getValue()));
        }

        //判断是否已经存在该编号
        Menu menu = selectByCode(menuDto.getCode());
        if (ToolUtil.isNotEmpty(menu)) {
            throw new ServiceException(BizExceptionEnum.ERROR.getCode(), "已存在code");
        }
        menuDto.setCreateUser(Long.valueOf(HttpContext.getUserId()));
        this.save(menuDto);
    }

    /**
     * 删除菜单
     *
     * @author stylefeng
     * @Date 2017/5/5 22:20
     */
    @Transactional
    public void delMenu(Long menuId) {

        //删除菜单
        this.menuMapper.deleteById(menuId);

        //删除关联的relation
        this.menuMapper.deleteRelationByMenu(menuId);
    }

    /**
     * 删除菜单包含所有子菜单
     *
     * @author stylefeng
     * @Date 2017/6/13 22:02
     */
    @Transactional
    public void delMenuContainSubMenus(Long menuId) {

        Menu menu = menuMapper.selectById(menuId);

        //删除当前菜单
        delMenu(menuId);

        //删除所有子菜单
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper = wrapper.like("PCODE", "%" + menu.getCode() + "%");
        List<Menu> menus = menuMapper.selectList(wrapper);
        for (Menu temp : menus) {
            delMenu(temp.getMenuId());
        }
    }

    /**
     * 查询所有的树
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    public List<MenuResult> getMenuIdsByRoleId() {
        List<MenuResult> menuResults = selectList();
        return menuResults;
    }

    public List<String> getMenuIdsByRoleId(Long roleId) {
        List<Long> menusId = this.baseMapper.getMenuIdsByRoleId(roleId);
        List<String> strings =  menuMapper.selectList(new QueryWrapper<Menu>().in("MENU_ID",menusId)).stream()
                        .map(Menu::getName).collect(Collectors.toList());
        return strings;
    }


    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    public List<ZTreeNode> menuTreeList() {
        return this.baseMapper.menuTreeList();
    }

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds) {
        return this.baseMapper.menuTreeListByMenuIds(menuIds);
    }

    /**
     * 删除menu关联的relation
     *
     * @param menuId
     * @return
     * @date 2017年2月19日 下午4:10:59
     */
    public int deleteRelationByMenu(Long menuId) {
        return this.baseMapper.deleteRelationByMenu(menuId);
    }

    /**
     * 获取资源url通过角色id
     *
     * @param roleId
     * @return
     * @date 2017年2月19日 下午7:12:38
     */
    public List<String> getResUrlsByRoleId(Long roleId) {
        return this.baseMapper.getResUrlsByRoleId(roleId);
    }

    /**
     * 根据角色获取菜单
     *
     * @param roleIds
     * @return
     * @date 2017年2月19日 下午10:35:40
     */
    public List<MenuNode> getMenusByRoleIds(List<Long> roleIds) {
        List<MenuNode> menus = this.baseMapper.getMenusByRoleIds(roleIds);

        //给所有的菜单url加上ctxPath
        for (MenuNode menuItem : menus) {
            menuItem.setUrl(ConfigListener.getConf().get("contextPath") + menuItem.getUrl());
        }

        return menus;
    }

    /**
     * 根据code查询菜单
     *
     * @author fengshuonan
     * @Date 2018/12/20 21:54
     */
    public Menu selectByCode(String code) {
        Menu menu = new Menu();
        menu.setCode(code);
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>(menu);
        return this.baseMapper.selectOne(queryWrapper);
    }

    public List<MenuResult> selectList() {
        List<Menu> menus = selectListByLevel(1, null);
        List<MenuResult> result1 = EntityConvertUtils.convertAListToBList(menus, MenuResult.class);
        if (result1 != null) {
            for (MenuResult result : result1) {
                menus = selectListByLevel(2, result.getCode());
                result.setChildren(EntityConvertUtils.convertAListToBList(menus, MenuResult.class));
            }
        }
        return result1;
    }

    public void update(Menu menu) {
        menu.setUpdateUser(Long.valueOf(HttpContext.getUser().getUserId()));
        menu = EntityConvertUtils.setNullValue(menu);
        this.updateById(menu);
    }


    public List<Menu> selectListByLevel(Integer levels, String pCode) {
        Menu menu = new Menu();
        menu.setLevels(levels);
        menu.setPcode(pCode);
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>(menu);
        return this.baseMapper.selectList(menuQueryWrapper);
    }

    public WTPageResponse editMenuTreeListByRoleId(List<String> menuIds, Long roleId) {
        //先删除后新增角色
        relationService.deleteByRole(roleId);
        Relation relation;
        for (String menuId : menuIds) {
            relation = new Relation();
            relation.setRoleId(roleId);
            relation.setMenuId(Long.valueOf(menuId));
            relationService.save(relation);
        }
        return WTPageResponse.success();
    }
}
