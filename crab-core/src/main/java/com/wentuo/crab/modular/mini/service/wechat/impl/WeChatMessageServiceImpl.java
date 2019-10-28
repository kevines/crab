package com.wentuo.crab.modular.mini.service.wechat.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wentuo.crab.core.wx.model.message.SendMessageTemplate;
import com.wentuo.crab.core.wx.model.message.TemplateData;
import com.wentuo.crab.modular.mini.entity.wechat.WeiXinToken;
import com.wentuo.crab.modular.mini.service.wechat.WeChatMessageService;
import com.wentuo.crab.util.HttpUtils;
import com.wentuo.crab.util.wx.WeChatAPI;
import com.wentuo.crab.util.wx.WeChatTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 功能描述：微信模版消息相关接口服务实现类
 *
 * @author wangbencheng
 * @version 1.0.0
 * @claaName TemplateMessageServiceImpl
 * @since 2019/10/6 13:40
 */
@Service
public class WeChatMessageServiceImpl implements WeChatMessageService {

    private static Logger logger = LoggerFactory.getLogger(WeChatMessageServiceImpl.class);

    @Resource
    private Environment environment;

    /**
     * 发送模板消息sendTemplateMessage
     * 小程序模板消息,发送服务通知
     *
     * @param touser          接收者（用户）的 openid
     * @param templateId      所需下发的模板消息的id
     * @param page            点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
     * @param formId          表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
     * @param map             小程序模版内容
     * @param emphasisKeyword 小程序模版内容发大的字的内容
     * @return
     */
    @Override
    public JSONObject sendTemplateMessage(String touser, String templateId, String page, String formId, Map<String, TemplateData> map, String emphasisKeyword) {
        String appid = environment.getProperty("pay.weixin.mini.appId");
        String secret = environment.getProperty("pay.weixin.mini.appSecret");
        String accessToken = "";
        WeiXinToken weiXinToken = WeChatTokenUtil.getAccessToken(appid, secret);
        if (weiXinToken != null) {   //表示调用凭证接口请求成功
            accessToken = weiXinToken.getAccessToken();
        }
        //设置模版属性
        SendMessageTemplate sendMessageTemplate = new SendMessageTemplate();
        sendMessageTemplate.setTouser(touser);
        sendMessageTemplate.setTemplate_id(templateId);
        sendMessageTemplate.setPage(page);
        sendMessageTemplate.setForm_id(formId);
        sendMessageTemplate.setData(map);
        sendMessageTemplate.setEmphasis_keyword(emphasisKeyword);
        String json = JSONObject.toJSONString(sendMessageTemplate);
        logger.info("小程序模版发送内容： " + json);
        String result = HttpUtils.sendPost(WeChatAPI.SEND_TEMPLATE_MESSAGE + accessToken, json);
        return JSON.parseObject(result);
    }
}
