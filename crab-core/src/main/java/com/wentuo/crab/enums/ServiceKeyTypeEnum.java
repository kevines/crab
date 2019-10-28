package com.wentuo.crab.enums;

/**
 * 功能描述：业务类型枚举
 *
 * @author wangbencheng
 * @version 1.0.0
 * @claaName ServiceKeyTypeEnum
 * @since 2019/10/25 16:54
 */
public enum ServiceKeyTypeEnum {
    Commision("分佣", "C"),

    ORDERS("订单", "OSP"),

    TASKDO("做任务", "CU"),

    FLOW("流水", "FLOW"),

    ACTIVITYGOODS("活动商品","AG"),

    CHECKBOOK("对账单","CB"),

    CHECKBOOKITEM("对账单明细","CBI"),

    TRADING("交易", "GSP"),
    TGSP("测试交易", "TGSP"),

    REFUND("退款单", "R"),

    SHOPPING_ADDRESS("收货地址", "SA"),

    MENU("菜单", "M"),

    MENU2("菜单", "M2"),

    ORGANIZATION("组织机构", "OG"),

    ROLE("角色", "R"),

    OP_USER("OP用户", "OU"),

    P_USER("合伙人用户", "P"),

    APP_USER("APP用户", "AU"),

    TRACKER("商务经理", "APU"),


    APP_HOME_REFRESH("用户app首页刷新记录", "APH"),

    GOODS("商品", "GD"),

    GOODS_SKU_NAME("商品sku属性名", "GSN"),

    GOODS_SKU_VALUE("商品sku属性值", "GSV"),

    GOODS_SKU_GROUP("商品sku组合", "GSG"),

    GOODS_CATEGORY("商品类目", "GC"),

    GOODS_GROUP_BOOKING("商品拼团", "GGB"),

    SPECIAL_BANNER("特卖banner", "SB"),

    WHOLESALER_APPLICATION("一县一品批发商申请", "WA"), COUNTY_GOODS("一县一品商品", "CG"),

    /**
     * 场景需要
     */
    SPECIAL_FAIR("特卖市集", "special_fair"),

    /**
     * 场景需要
     */
    SPECIAL_LIGHTNING_SELLING("特卖闪购", "special_lightning"),

    /**
     * 场景需要
     */
    SPECIAL_PLACARD("特卖海报", "special_placard"),

    SHOP_CART("购物车", "SC"),

    BANNER("banner", "B"),

    TOURSIM_GOODS("旅游产品", "TG"),

    APP_HOME("app首页刷新记录", "AH"),

    SUPPLY_DEMAND_CATELOGUE("供需类型", "SDC"),

    SUPPLY_DEMAND("供需信息", "SD"),

    LOGIN_RECORD("登录记录", "LR"),

    SCORE_FLOW("工分记录", "SF"),

    OP_SCORE("工分配置", "OS"),

    SCORE_REWARD("工分奖励单", "SR"),

    WORK_PERSON_APPROVE_RECORD("个人认证记录", "WPAR"),

    WORK_COMPANY_APPROVE("企业认证", "WCA"),

    WORK_COMPANY_APPROVE_RECORD("企业认证审核记录", "WCAR"),

    WORK_EMPLOY_INFO("应聘信息", "WEI"),

    WORK_MASTER_INFO("师傅信息", "WMI"),

    WORK_PERSON_APPROVE("个人认证", "WPA"),

    WORK_RECRUIT("招工信息", "WR"),

    WORK_RECRUIT_RELEVANCE("招工人才关联信息", "WRR"),

    WORK_TALENT_POOL("人才库信息", "WTP"),

    WORK_ARBITRATION("招工仲裁", "WA"),

    COMMON_ATTENTION("关注", "CA"),

    WORK_INDUSTRY_CONFIG("工作工种配置", "WIC"),

    COMMON_MESSAGE("站内信", "CM"),

    COMMON_PUSH_MESSAGE("推送信息任务","CPM"),

    COMMON_PUSH_MESSAGE_ITEM("推送信息任务明细","CPMI"),

    WORK_SKILL_CONFIG("工作技能配置", "WSC"),

    WORK_ORDER("找工作订单", "WO"),

    COMMON_ACCUSATION("举报", "CAN"),

    COMMON_COMMENT("评论", "CC"),

    VIDEO_INFO("视频信息", "VI"),

    VIDEO_INFO_RECORD("视频审核记录信息", "VIR"),

    COUPLE_SINGLE_INFO("单身信息", "CSI"),

    COUPLE_SINGLE_INFO_RECORD("单身信息审核记录", "CSIR"),

    COUPLE_INDUSTRY_CONFIG("找对象行业配置", "CIC"),

    COUPLE_JOB_CONFIG("找对象工作配置", "CJC"),

    OPERA_INFO("戏曲精选", "OI"),

    COMMON_ATTACHMENT_RELEVANCE("附件关联", "CAR"),

    COMMON_ATTACHMENT("附件信息", "CAT"),

    ///////////////旅游部分开始//////////////////

    TOURSIM_DEALER_INFO("旅游个人资料", "TDI"),

    TOURSIM_ORDER("旅游订单", "TO"),

    TOURSIM_RULE("旅游成团规则", "TR"),

    TOURSIM_DEALER_RELEVANCE("旅游出行人关联", "TDR"),

    ///////////////旅游部分结束//////////////////

    ///////////////分佣部分开始//////////////////
    INTRO_COMMISSION("介绍人分佣配置", "INTRO_COMMISION"),

    EXPRESS_COMMISSION_CONFIG("赶快分佣配置", "EXPRESS_COMMISSION"),

    SPECIAL_COMMISSION_CONFIG("特卖分佣配置", "SPECIAL_COMMISSION"),

    WORK_COMMISSION_CONFIG("找工作分佣配置", "WORK_COMMISSION"),

    ///////////////分佣部分结束//////////////////

    //////////////赶街快递部分开始///////////////////
    EXPRESS_DRIVER("赶快司机", "EXPRESS_DRIVER"),

    EXPRESS_DRIVER_APPLY("赶快司机申请表", "EXPRESS_DRIVER_APPLY"),

    EXPRESS_ROUTE("赶快司机常用路线表", "EXPRESS_ROUTE"),

    EXPRESS_VEHICLE_TYPE("赶快车辆类型配置", "VEHICLE_TYPE"),

    EXPRESS_CHARGE_TEMPLATE("赶快运费服务费模板", "EXPRESS_CHARGE"),

    EXPRESS_LOGISTICIAN("赶快物流人员", "LOGISTICIAN"),

    EXPRESS_ORDER_FLOW("赶街快递订单操作流水", "EOF"),

    EXPRESS_ORDER("赶街快递订单表", "EXP"),

    EXPRESS_ORDER_CODE("赶街快递订单流水号", "EOC"),

    EXPRESS_STORE("赶快网点", "STORE"),

    EXPRESS_STORE_APPLY("赶快网点申请表", "EXPRESS_STORE_APPLY"),

    EXPRESS_OBJ_TYPE("快递物品类型", "OBJ_TYPE"),

    //////////////赶街快递部分结束///////////////////

    COMMON_COMMISSION_FLOW("分佣流水", "CCF"),

    ORDERS_MESSAGE("订单各类消息", "OM"), /** 工分申请 **/
    SCORE_APPLY("工分申请", "SA"),

    FINANCE_BESPOKE("金融预约", "FB"),

    SYSTEM_BULLETIN("快报管理", "KB"),

    FINANCE_GOODS("金融商品", "FG"),

    WITHDRAW_APPLY("提现申请", "WA"),

    BANK_CARD("银行卡", "BC"),

    BAOFU_ERROR_MESSAGE("宝付同步接口返回信息记录", "BEM"),

    //////////////模板///////////////////
    TEMPLATE("商品模板","PRODUCT_TEMPLATE"),

    FOLLOWCATEGORY("用户关注类目","FC"),

    SHOPCATEGORY("店铺类目","SC"),

    GROUP_ORDERS("团单", "GGO"),

    RECHARGE_CONFIG("话费充值配置", "RC"),

    RECHARGE("话费充值", "C"),

    RECHARGE_ORDER("话费充值订单", "RO"),

    RECHARGE_REFUND("话费充值退款单", "RF"),

    ACT("活动", "ACT"),

    TASK_SPECIAL_CONFIG("大牌任务配置", "TSC"),

    TASK_REFER("客服呼叫中心", "ZX"),

    ACCOUNT_CHARGE("账户充值", "AC"),

    ;

    /**
     * 必须英文字母
     */
    private String name;
    /**
     * 必须英文字母
     */
    private String value;

    ServiceKeyTypeEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
