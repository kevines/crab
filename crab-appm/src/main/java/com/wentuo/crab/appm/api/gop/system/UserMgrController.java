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
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.MD5Util;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.wentuo.crab.core.common.annotion.BusinessLog;
import com.wentuo.crab.core.common.annotion.NoPermission;
import com.wentuo.crab.core.common.annotion.Permission;
import com.wentuo.crab.core.common.constant.Const;
import com.wentuo.crab.core.common.constant.dictmap.UserDict;
import com.wentuo.crab.core.common.exception.BizExceptionEnum;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.modular.mini.entity.appuser.AppUser;
import com.wentuo.crab.modular.mini.service.appuser.AppUserService;
import com.wentuo.crab.modular.system.entity.User;
import com.wentuo.crab.modular.system.service.UserService;
import com.wentuo.crab.util.EntityConvertUtils;
import com.wentuo.crab.util.HttpContext;
import com.wentuo.crab.util.OssUtil;
import com.wentuo.crab.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统管理员控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/mgr")
public class UserMgrController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private AppUserService appUserService;

    /**
     * 获取用户详情
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Object getUserInfo() {
        AppUser appUser = appUserService.queryUserByUserId(HttpContext.getUserId());
        Map<String, Object> map = new HashMap<>();

        map.put("menu", "*");
//        map.put("account", appUser.getRealName());
        map.put("avatar", "https://ganjie-oss.oss-cn-hangzhou.aliyuncs.com/ganjie-apppm/default.png");

        return WTResponse.success(map);
    }

    /**
     * 查询管理员列表
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:43
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public WTPageResponse list(User user) {
        return this.userService.selectUsers(user);
    }

    /**
     * 添加管理员
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/addItem.do")
    @ResponseBody
    public WTResponse add(User user) {
        this.userService.addUser(user);
        return WTResponse.success();
    }

    /**
     * 修改管理员
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/edit.do")
    @ResponseBody
    public WTResponse edit(User user) {
        if (StringUtil.isNotEmpty(user.getPassword())) {
            user.setPassword(MD5Util.encrypt(user.getPassword()));
        }
        // 判断账号是否重复
        User theUser = this.userService.getByAccount(user.getAccount());
        if (theUser != null && !theUser.getUserId().equals(user.getUserId())) {
            throw new ServiceException(BizExceptionEnum.ERROR.getCode(), "账号重复");
        }
        EntityConvertUtils.setNullValue(user);
        this.userService.updateById(user);
        return WTResponse.success();
    }

    /**
     * 删除管理员（逻辑删除）
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/delete.do")
    @BusinessLog(value = "删除管理员", key = "userId", dict = UserDict.class)
    @Permission
    @ResponseBody
    public ResponseData delete(@RequestParam Long userId) {

        this.userService.removeById(userId);
        return SUCCESS_TIP;
    }

    /**
     * 分配角色
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping("/setRole")
    @BusinessLog(value = "分配角色", key = "userId,roleIds", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public ResponseData setRole(@RequestParam("userId") Long userId, @RequestParam("roleIds") String roleIds) {
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new ServiceException(BizExceptionEnum.ERROR);
        }
        //不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new ServiceException(BizExceptionEnum.ERROR);
        }
        this.userService.assertAuth(userId);
        this.userService.setRoles(userId, roleIds);
        return SUCCESS_TIP;
    }

    /**
     * 上传图片
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    @NoPermission
    public Object upload(@RequestPart("file") MultipartFile picture) {
        String fileSavePath = "";
        String filename = picture.getOriginalFilename();
        try {
            boolean imageFile = OssUtil.checkFileSize(picture.getSize(), 2, "M");
            if (!imageFile) {
                return ResponseData.error(BizExceptionEnum.ERROR.getCode(), "上传图片不能超过2m");
            }
            if (filename.toUpperCase().indexOf(".JPG") > 0 || filename.toUpperCase().indexOf(".PNG") > 0 || filename.toUpperCase().indexOf(".JPEG") > 0) {
                fileSavePath = OssUtil.ossUpload(picture.getInputStream());
            } else {
                return ResponseData.error(BizExceptionEnum.ERROR.getCode(), "图片不符合格式");
            }
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.ERROR);
        }

        return ResponseData.success(fileSavePath);
    }

    /**
     * 上传图片
     *
     * @author fengshuonan
     * @Date 2018/12/24 22:44
     */
    @RequestMapping(method = RequestMethod.POST, path = "/uploadFile")
    @ResponseBody
    @NoPermission
    public Object uploadFile(@RequestPart("file") MultipartFile picture) {
        String fileSavePath = "";
        String filename = picture.getOriginalFilename();
        String prefix=filename.substring(filename.lastIndexOf(".")+1);
        try {
            boolean file = OssUtil.checkFileSize(picture.getSize(), 100, "M");
            if (!file) {
                return ResponseData.error(BizExceptionEnum.ERROR.getCode(), "上传文件不能超过100m");
            }
            fileSavePath = OssUtil.ossUploadFile(picture.getInputStream(),prefix);
        } catch (Exception e) {
            throw new ServiceException(BizExceptionEnum.ERROR);
        }

        return ResponseData.success(fileSavePath);
    }
}
