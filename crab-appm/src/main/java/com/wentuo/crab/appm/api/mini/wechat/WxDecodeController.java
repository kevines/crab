package com.wentuo.crab.appm.api.mini.wechat;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.wentuo.crab.core.common.annotion.NoPermission;
import com.wentuo.crab.core.common.page.WTPageResponse;
import com.wentuo.crab.modular.mini.service.wechat.WxDecodeService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 功能描述：
 *
 * @author wangbencheng
 * @version 1.0
 * @className WxDecodeController
 * @since 2019/8/14 20:55
 */
@RestController
public class WxDecodeController extends BaseController{

    private static final Logger LOGGER = LoggerFactory.getLogger(WxDecodeController.class);


    @Resource
    private WxDecodeService wxDecodeService;


    /**
     * 功能描述: 小程序授权用户基本信息
     * @author wangbencheng
     * @since 2019/8/14 21:09
     * @param code
     * @param iv
     * @param encryptedData
     */
    @NoPermission
    @ResponseBody
    @RequestMapping(value = "/wxDecode.do", method = RequestMethod.POST)
    public WTPageResponse<Map<String,Object>> wxDecode(String code, String iv, String encryptedData) {
        if (StringUtils.isBlank(code)|| StringUtils.isBlank(iv) || StringUtils.isBlank(encryptedData)) {
            LOGGER.error("小程序端传参有误[code:"+code+"/iv:"+iv+"/encryptedData:"+encryptedData+"]");
            return new WTPageResponse<>(WTPageResponse.FAIL, "输入内容为空", null);
        }
        return wxDecodeService.wxDecode(code,iv,encryptedData);
    }

    /**
     * 功能描述: 小程序授权手机号
     * @author wangbencheng
     * @since 2019/8/14 23:57
     * @param code
     * @param iv
     * @param encryptedData
     * @return
     */
    @NoPermission
    @ResponseBody
    @RequestMapping(value = "/wxDecodePhone.do", method = RequestMethod.POST)
    public WTPageResponse<String> wxDecodePhone(String code, String iv, String encryptedData) {
        return wxDecodeService.wxDecodePhone(code,iv,encryptedData);
    }
}
