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
package com.wentuo.crab.appm.api.gop.system;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSONObject;
import com.wentuo.crab.core.common.exception.BizExceptionEnum;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.modular.system.entity.Menu;
import com.wentuo.crab.modular.system.model.MenuResult;
import com.wentuo.crab.modular.system.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author fengshuonan
 * @Date 2017年2月12日21:59:14
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Resource
    private MenuService menuService;

    /**
     * 编辑菜单
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:53 PM
     */
    @RequestMapping(value = "/edit.do")
    @ResponseBody
    public WTResponse edit(Menu menu) {
        this.menuService.update(menu);
        return WTResponse.success();
    }

    /**
     * 获取菜单列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:53 PM
     */
    @RequestMapping(value = "/list.do")
    @ResponseBody
    public Object list() {
        List<MenuResult> menus = this.menuService.selectList();
        return WTResponse.success(menus);
    }

    /**
     * 新增菜单
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:53 PM
     */
    @RequestMapping(value = "/addItem.do")
    @ResponseBody
    public WTResponse add(Menu menu) {
        this.menuService.addMenu(menu);
        return WTResponse.success();
    }

    /**
     * 删除菜单
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:53 PM
     */
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public WTResponse delete(@RequestParam Long menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            throw new ServiceException(BizExceptionEnum.ERROR);
        }
        //删除菜单包含所有子菜单
        this.menuService.delMenuContainSubMenus(menuId);

        return WTResponse.success();
    }

    /**
     * 获取角色的菜单列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:54 PM
     */
    @RequestMapping(value = "/menuTreeListByRoleId.do")
    @ResponseBody
    public WTResponse menuTreeListByRoleId(Long roleId) {
        List<MenuResult> menuResults = this.menuService.getMenuIdsByRoleId();
        return WTResponse.success(menuResults);
    }

    /**
     * 获取角色的菜单列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:54 PM
     */
    @RequestMapping(value = "/editMenuTreeListByRoleId.do")
    @ResponseBody
    public WTPageResponse editMenuTreeListByRoleId(@RequestBody JSONObject jsonObject) {
        List<String> menu = (List<String>) jsonObject.get("menuIds");
        String roleid = jsonObject.get("roleId").toString();
        WTPageResponse ganjieResponse = this.menuService.editMenuTreeListByRoleId(menu,Long.valueOf(roleid));
        return ganjieResponse;
    }

}
