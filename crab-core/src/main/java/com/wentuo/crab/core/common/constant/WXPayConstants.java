package com.wentuo.crab.core.common.constant;

/**
 * 常量
 */
public class WXPayConstants {

    public enum SignType {
        MD5, HMACSHA256
    }

    public static final String FAIL     = "FAIL";
    public static final String SUCCESS  = "SUCCESS";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    public static final String MICROPAY_URL     = "https://api.mch.weixin.qq.com/pay/micropay";
    public static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String ORDERQUERY_URL   = "https://api.mch.weixin.qq.com/pay/orderquery";
    public static final String REVERSE_URL      = "https://api.mch.weixin.qq.com/secapi/pay/reverse";
    public static final String CLOSEORDER_URL   = "https://api.mch.weixin.qq.com/pay/closeorder";
    public static final String REFUND_URL       = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    public static final String REFUNDQUERY_URL  = "https://api.mch.weixin.qq.com/pay/refundquery";
    public static final String DOWNLOADBILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
    public static final String REPORT_URL       = "https://api.mch.weixin.qq.com/payitil/report";
    public static final String SHORTURL_URL     = "https://api.mch.weixin.qq.com/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL = "https://api.mch.weixin.qq.com/tools/authcodetoopenid";

    // sandbox
    public static final String SANDBOX_MICROPAY_URL     = "https://api.mch.weixin.qq.com/sandboxnew/pay/micropay";
    public static final String SANDBOX_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/unifiedorder";
    public static final String SANDBOX_ORDERQUERY_URL   = "https://api.mch.weixin.qq.com/sandboxnew/pay/orderquery";
    public static final String SANDBOX_REVERSE_URL      = "https://api.mch.weixin.qq.com/sandboxnew/secapi/pay/reverse";
    public static final String SANDBOX_CLOSEORDER_URL   = "https://api.mch.weixin.qq.com/sandboxnew/pay/closeorder";
    public static final String SANDBOX_REFUND_URL       = "https://api.mch.weixin.qq.com/sandboxnew/secapi/pay/refund";
    public static final String SANDBOX_REFUNDQUERY_URL  = "https://api.mch.weixin.qq.com/sandboxnew/pay/refundquery";
    public static final String SANDBOX_DOWNLOADBILL_URL = "https://api.mch.weixin.qq.com/sandboxnew/pay/downloadbill";
    public static final String SANDBOX_REPORT_URL       = "https://api.mch.weixin.qq.com/sandboxnew/payitil/report";
    public static final String SANDBOX_SHORTURL_URL     = "https://api.mch.weixin.qq.com/sandboxnew/tools/shorturl";
    public static final String SANDBOX_AUTHCODETOOPENID_URL = "https://api.mch.weixin.qq.com/sandboxnew/tools/authcodetoopenid";
    //小程序服务通知
    public static final String MININOTIFYURL     = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";
    //小程序获取ACCESS_TOKEN
    public static final String ACCESS_TOKEN     = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx49e305c638e38ee2&secret=35d5d32f7dc3e2845331bc9eae24bb41";
    //退款失败
    public static final String REFUNDFAIL        = "vTF2XTwZv_DaReCtTQz_9SU8TKD8KFWnTfg97PaCRsQ";
    //退款失败信息
    public static final String REFUNDFAILINFO        = "温馨提示: 您购买的商品,商家拒绝退款,如有问题请及时联系平台!";
    //退款成功
    public static final String REFUNDSUCCESS     = "oX-UCAxPuqkDR3bwJuWBZnax4oNCoXN2VkONO2XNuXg";
    //退款成功信息
    public static final String REFUNDSUCCESSINFO     = "温馨提示: 您购买的商品,商家同意退款,如有问题请及时联系平台!";
    //发货
    public static final String SEND              = "VPuy5YXNL06tITUKJzcmdGleCrBGchSlCzLxcYQaYRU";
    //发货信息
    public static final String SENDINFO              = "温馨提示: 您购买的商品,商家已经发货,请注意查收!";
    //待支付
    public static final String WAITPAY           = "5hyPXX1U4lYW_lQaG0f_j-qn2RQDIh9Fl53iHxy3E8Y";
    //待支付信息
    public static final String WAITPAYINFO           = "温馨提示: 商品正在火热售卖中,请尽快完成支付,商家会第一时间安排发货的哦!";
    //支付成功
    public static final String PAYSUCCESS        = "zxsFsqCfKxR8PfcUYuXCY8ZaIIYIqt-Vbm2920GnIMs";
    //支付成功信息
    public static final String PAYSUCCESSINFO        = "温馨提示: 您已成功下单,商家会尽快发货,请耐心等待!";
}