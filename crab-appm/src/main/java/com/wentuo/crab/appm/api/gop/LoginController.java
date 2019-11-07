package com.wentuo.crab.appm.api.gop;

import cn.stylefeng.roses.core.util.MD5Util;
import com.wentuo.crab.core.common.annotion.NoPermission;
import com.wentuo.crab.core.common.page.WTResponse;
import com.wentuo.crab.modular.mini.entity.appuser.AppUser;
import com.wentuo.crab.modular.mini.service.wechat.TokenService;
import com.wentuo.crab.modular.system.entity.User;
import com.wentuo.crab.modular.system.service.UserService;
import com.wentuo.crab.util.Constant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：登录控制器
 * @author wangbencheng
 * @version 1.0
 * @className LoginController
 * @since 2019/8/15 0:59
 */

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;

    @PostMapping("/gop/login.do")
    @NoPermission
    @ResponseBody
    public Object gopLogin(@RequestParam("username") String username,
                           @RequestParam("password") String password) {


        if(StringUtils.isBlank(username)){
            return new WTResponse<>(WTResponse.ERROR,"用户名不能为空");
        }

        if(StringUtils.isBlank(password)){
            return new WTResponse<>(WTResponse.ERROR,"密码不能为空");
        }

        //获取数据库中的账号密码，准备比对
        User user = userService.getByAccount(username);

        password = MD5Util.encrypt(password);
        if(user==null){
            return new WTResponse<>(WTResponse.ERROR,"用户不存在");
        }
        if (password.equals(user.getPassword())) {
            AppUser appUser = new AppUser();
            appUser.setLoginType(Constant.GOP);
            appUser.setUserId(user.getUserId());
            String token = tokenService.getAppUserToken(appUser);
            Map<String, String> result = new HashMap<>(1);
            logger.info("token:{}",token);
            result.put("token", token);
            return new WTResponse(result);
        } else {
            return new WTResponse(WTResponse.ERROR,"账号密码错误");
        }
    }

}
