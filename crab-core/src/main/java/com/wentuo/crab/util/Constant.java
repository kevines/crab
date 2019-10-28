/**
 * BEYONDSOFT.COM INC
 */
package com.wentuo.crab.util;


import com.wentuo.crab.enums.EnvironmentEnum;

import java.util.Random;

/**
 *
 * @author yulijun
 * @version $Id: MessageCode.java, v 0.1 2017/9/2 15:34 yulijun Exp $$
 */
public class Constant {


    public static final String OPERATOR_SESSION_ID = "OPERATOR_SESSION_ID";

    public static final String DATEFORMALDEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String good_img_process = "?x-oss-process=style/good";

    public static final boolean TRUE = true;
    public static final String TOKEN = "token";
    public static final String USER = "user";
    public static final String PM = "pm";
    public static final String APP = "app";
    public static final String OP = "op";
    public static final String GOP = "gop";
    public static final String WX = "wx";
    public static final String TOWN = "town";

    public static final String PAYMENT = "货款到账";
    public static final String GOODSAMOUT = "商品佣金";
    public static final String PARTNERGOODSAMOUT = "大礼包佣金";
    public static final String PARTNERGOODSPAYMENT = "大礼包货款";
    public static final String TASKAMOUT = "任务佣金";



    public static final boolean FALSE = false;

    public static final String PARTNERGOODSID = "props.partnerGoodsId";
    public static final String GOPUSERID = "props.gopUserId";
    public static final String ACTIVE = "spring.profiles.active";

    /**
     * 角色常量
     */
    public static final String COUNTYPARTNER = "countyPartner";
    public static final String TOWNSPARTNER = "townsPartner";
    public static final String VILLAGEPARTNER = "villagePartner";
    public static final String INVITEPARTNER      = "invitePartner";

    /**默认头像*/
    public static final String USER_PHOTO          = "default_photo";
    /**默认压缩头像*/
    public static final String USER_COMPRESS_PHOTO = "compress_photo";

    /**
     * 成功码
     */
    public static final Integer SUCCESS = 200;

    /**
     * 参数空错误码
     */
    public static final Integer NULLCODE = 400;

    /**
     * 错误码
     */
    public static final Integer ERRCODE = 500;

    /**
     * 系统优化
     */
    public static final Integer SYSCODE = 205;
    /**
     * 错误信息
     */
    public static final String ERRMESS = "请求超时，请稍后重试";

    /**
     *无权，在另一台机器登录
     */
    public static final String NOT_ALLOW = "405";

    /*******交易模块常量********/

    /**
     *App用户注册验证码
     */
    public static final String VERTIFYCODE                              = "VERTIFYCODE";
    /**
     * 交易状态
     */
    /**待支付***/
    public static final int    TRANSACTION_STATUS_WAIT_PAY              = 1;
    /*****完成支付*****/
    public static final int    TRANSACTION_STATUS_COMPLETE_PAY          = 2;
    /*****完成*******/
    public static final int    TRANSACTION_STATUS_CLOSED                = 3;
    /**
     * 交易类型
     */
    /**购买***/
    public static final String TRANSACTION_TRADE_TYPE_BUY               = "1";
    /**手机充值***/
    public static final String TRANSACTION_TRADE_TYPE_TEL_FEE           = "2";
    /**取现***/
    public static final String TRANSACTION_TRADE_TYPE_WITHDRAW          = "3";
    /***账户充值****/
    public static final String TRANSACTION_TRADE_TYPE_RECHARGE          = "4";
    /***分佣****/
    public static final String TRANSACTION_TRADE_TYPE_INCOME_COMMISSION = "5";

    /**交易类型文本**/
    /**购买交易类型文本***/
    public static final String TRANSACTION_TRADE_TYPE_BUY_TEXT               = "购买";
    /**手机充值类交易文本***/
    public static final String TRANSACTION_TRADE_TYPE_TEL_FEE_TEXT           = "话费充值";
    /***充值类交易文本****/
    public static final String TRANSACTION_TRADE_TYPE_WITHDRAW_TEXT          = "账户提现";
    /***账户充值类文本****/
    public static final String TRANSACTION_TRADE_TYPE_RECHARGE_TEXT          = "账户充值";
    /***分佣类交易文本****/
    public static final String TRANSACTION_TRADE_TYPE_INCOME_COMMISSION_TEXT = "分佣";

    /**
     * 订单评价状态
     */
    /**待评价状态**/
    public static final int ORDER_EVALUATION_STATUS_WAIT   = 1;
    /**评价完成状态**/
    public static final int ORDER_EVALUATION_STATUS_CLOSED = 2;

    /**
     * 订单模块
     */
    /**OTO模块**/
    public static final int ORDER_MODULE_OTO  = 1;
    /**特卖模块**/
    public static final int ORDER_MODULE_TM   = 2;
    /**一县一品模块**/
    public static final int ORDER_MODULE_YXYP = 3;
    /**旅游模块**/
    public static final int ORDER_MODULE_LY   = 4;

    /**
     * 订单状态
     */
    /**待支付状态**/
    public static final int ORDER_STATUS_WAIT_PAY     = 1;
    /**待发货状态**/
    public static final int ORDER_STATUS_WAIT_SHIP    = 2;
    /**待收货状态**/
    public static final int ORDER_STATUS_WAIT_RECEIPT = 3;
    /**关闭完成状态**/
    public static final int ORDER_STATUS_CLOSED       = 4;

    /**
     * 订单关闭状态
     */
    /**取消关闭**/
    public static final int ORDER_STATUS_CANCEL_CLOSED  = 1;
    /**退货退款关闭**/
    public static final int ORDER_STATUS_RETURN_CLOSED  = 2;
    /**正常完成成功完成关闭**/
    public static final int ORDER_STATUS_SUCCESS_CLOSED = 3;

    /**
     * 发货方式
     *
     * **/
    /**快递**/
    public static final int ORDER_SENDTYPE_EXPRESS     = 1;
    /**赶快**/
    public static final int ORDER_SENDTYPE_GANKUAI     = 2;
    /**配送至网点**/
    public static final int ORDER_SENDTYPE_SHOP        = 3;
    /**自提**/
    public static final int ORDER_SENDTYPE_SELFSERVICE = 4;
    /**包邮**/
    public static final int ORDER_SENDTYPE_FREE        = 5;

    /**
     * 退货退款状态
     *
     * **/
    /**申请退款中**/
    public static final int ORDER_REFUNDSSTATUS_APPLY      = 1;
    /**申请退货中**/
    public static final int ORDER_REGOODSSTATUS_APPLY      = 2;
    /**退货中**/
    public static final int ORDER_REGOODSSTATUS_PROCESSING = 3;
    /**退款中**/
    public static final int ORDER_REFUNDSSTATUS_PROCESSING = 4;
    /**拒绝退货**/
    public static final int ORDER_REGOODSSTATUS_REFUSE     = 5;
    /**拒绝退款**/
    public static final int ORDER_REFUNDSSTATUS_REFUSE     = 6;
    /**退货退款成功完成**/
    public static final int ORDER_RETURNSTATUS_COMPLETED   = 7;

    /**
     * 订单模块错误码及错误信息
     */
    public static final String ORDER_MODULEERROR_CODE = "100001";
    public static final String ORDER_MODULEERROR_MSG  = "订单模块参数错误";

    /**
     * 收货地址默认状态
     */
    public static final Boolean DEFAULT_TRUE  = Boolean.TRUE;
    public static final Boolean DEFAULT_FALSE = Boolean.FALSE;

    /**
     * 是否删除
     */
    public static final Boolean DELETE_TRUE  = Boolean.TRUE;
    public static final Boolean DELETE_FALSE = Boolean.FALSE;

    /**
     * 分页条数
     */
    public static final int PAGESIZE = 10;

    /**
     *供需状态
     */
    public static final int SUPPLY_DEMAND_STATUS_WAIT_APPROVE    = 1;
    public static final int SUPPLY_DEMAND_STATUS_APPROVED_PASS   = 2;
    public static final int SUPPLY_DEMAND_STATUS_APPROVED_REFUSE = 3;
    /**
     *供需类型
     */
    public static final int SUPPLY_DEMAND_TYPE_SUPPLY            = 1; //供应
    public static final int SUPPLY_DEMAND_TYPE_DEMAND            = 2; //需求

    /**
     * 角色类型
     */
    public static final String ROLE_TYPE_GOP = "1";
    public static final String ROLE_TYPE_OP  = "2";

    /**
     * 用户类型
     */
    public static final String USER_TYPE_GOP = "1";
    public static final String USER_TYPE_OP  = "2";

    /**
     * App端用户类型  1--普通用户  2--商家用户
     */
    public static final String USER_TYPE_APP    = "1";
    public static final String USER_TYPE_SELLER = "2";
    public static final String USER_TYPE_SHARE  = "3";
    /**
     * 初始密码
     */
    public static final String PWD = "123456";

    /**
     * 商家登录商家平台
     */
    public static final String SELLER     = "SELLER";
    /**
     * 手机号校验
     */
    public static final String REGEXPHONE = "^1\\d{10}$";

    /**
     * 用户信息是否完善
     */
    public static final String IS_COMPLETE     = "1";
    public static final String IS_NOT_COMPLETE = "0";

    /**
     * 系统站内信发送人编码
     */
    public static final String SYSTEM_MESSAGE = "system";

    /**
     *收支类型
     */
    public static final int CHANGE_TYPE_IN  = 1;
    public static final int CHANGE_TYPE_OUT = 2;

    /**
     *工分流水类型
     */
    public static final int     ORDER_PAYMENT       = 1;
    public static final int     SCORE_RULE          = 2;
    public static final int     SCORE_REWARD        = 3;
    public static final int     SCORE_REWARD_REBATE = 4;
    /***
     * 验证码短信最大限
     */
    public static final Integer MAX_SMS             = 15;

    /**
     * 找回密码手机号  还是推荐人 还是短信验证码登入
     */
    public static final String FIND_PWD  = "retrieve";
    public static final String REFERENCE = "reference";

    public static final String SMS_LOGIN = "smslogin";

    /**
     * 工分抵扣金额数据字典datakey
     */
    public static final String SCORE_DATAKEY           = "SCORE2017100914513601";
    /**
     * 闪购定时任务开始时间点datakey
     */
    public static final String LIGHTNING_DATAKEY       = "LIGHTNING2017101217265512";
    /**
     * 代付款订单定时任务datakey
     */
    public static final String ORDER_WAIT_PAY_DATAKEY  = "ORDER_WAIT_PAY2017101309575441";
    /**
     * 待发货订单定时任务datakey
     */
    public static final String ORDER_WAIT_SEND_DATAKEY = "ORDER_WAIT_SEND20171013163215";
    /**
     * 待收货超时
     */
    public static final String ORDER_WAIT_TAKE_DATAKEY = "ORDER_WAIT_TAKE20171013173126";
    /**
     * 商家处理退款超时
     */
    public static final String ORDER_BACK_DATAKEY      = "ORDER_BACK20171013183325";
    /**
     * 二维码前缀
     */
    public static final String GANJIESPECIALORDER      = "ganjieSpecialOrder:";

    /**
     * 合伙人订单目标
     */
    public static final int ORDER_QUANTITY_TARGET      = 10;

    // 50条处理一次
    public static final int    PAGE_SIZE    = 50;
    // 最多处理10次
    public static final int    HANDLE_COUNT = 10;
    //
    public static final String SYSTEM_NAME  = "system";

    /**
     * 是否为乡镇合伙人
     */
    public static final String IS_TOWNSHIP_PERSOPN     = "1";
    public static final String IS_NOT_TOWNSHIP_PERSOPN = "0";

    public static final String PARTNER_ORDER_ACTIVITY  = "PARTNER_ORDER_ACTIVITY";

    /**
     * 端口
     */
    public  static  String PORT;

    /**
     * 站内信查看评论参数
     */
    public static final String COMMONT_LINK = "&scroll=scroll";

    //生成随机密码
    public static String getRandomPwd() {

        String val = "";
        Random random = new Random();
        //length为几位密码
        for(int i = 0; i < 8; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    //是否是正式环境
    public static Boolean isProd() {

        if (StaticConfigUtil.getEnv().contains(EnvironmentEnum.PREPUBLISH.getCode())) {
            return true;
        }
        return false;
    }

    //是否是本地环境
    public static Boolean isLocal() {
        try{
            String url  =  HttpContext.getRequest().getRequestURL().toString();
            if (StaticConfigUtil.getEnv().contains(EnvironmentEnum.LOCAL.getCode()) || url.contains("localhost")) {
                return true;
            }
            return false;
        }catch (Exception e){

        }
        return false;
    }

    //是否大礼包
    public static Boolean isGoods(String goodsId) {

        if (goodsId.equals(Constant.PARTNERGOODSID)) {
            return true;
        }
        return false;
    }


}
