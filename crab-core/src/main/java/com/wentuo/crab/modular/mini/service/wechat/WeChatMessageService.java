package com.wentuo.crab.modular.mini.service.wechat;

import com.alibaba.fastjson.JSONObject;
import com.wentuo.crab.core.wx.model.message.TemplateData;

import java.util.Map;

/**
 * 微信模版消息相关服务接口
 *
 * @author wangbencheng
 */
public interface WeChatMessageService {

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
    JSONObject sendTemplateMessage(String touser, String templateId, String page, String formId, Map<String, TemplateData> map, String emphasisKeyword);
}
