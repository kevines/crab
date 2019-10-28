package com.wentuo.crab.core.wx.model.message;

import lombok.Data;

import java.util.Map;

/**
 * 功能描述：微信小程序发送消息模版
 *
 * @author wangbencheng
 * @version 1.0.0
 * @claaName WechatTemplate
 * @since 2019/10/6 13:08
 */
@Data
public class SendMessageTemplate {

    /**
     * 接受者（用户）openid
     */
    private String touser;

    /**
     * 下发消息模版id
     */
    private String template_id;

    /**
     * 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
     */
    private String page;

    /**
     * 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
     */
    private String form_id;

    /**
     * 模版内容，不填则下发空模板
     */
    private Map<String, TemplateData> data;

    /**
     * 小程序模版内容发大的字的内容
     */
    private String emphasis_keyword;
}
