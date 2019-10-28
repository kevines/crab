package com.wentuo.crab.core.pay.common.util;

import com.wentuo.crab.core.common.constant.RedisKeys;
import com.wentuo.crab.core.common.exception.BizExceptionEnum;
import com.wentuo.crab.core.util.RedisUtil;
import com.wentuo.crab.enums.EnvironmentEnum;
import com.wentuo.crab.enums.ServiceKeyTypeEnum;
import com.wentuo.crab.util.BizException;
import com.wentuo.crab.util.Constant;
import com.wentuo.crab.util.StaticConfigUtil;
import com.wentuo.crab.util.StringUtil;

import java.util.Map;

public class PayUtil {

    public static String wxHandld(Map<String, Object> params) {
        String returnCode = (String) params.get("return_code");
        String resultCode = (String) params.get("result_code");
        if (Constant.isProd() && !("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode))) {
            throw new BizException(BizExceptionEnum.ERROR.getCode(), "微信请求出错" + params.get("err_code_des"));
        }
        return "";
    }

    public static String wxHandldMsg(Map<String, Object> params) {
        String returnCode = (String) params.get("return_code");
        String resultCode = (String) params.get("result_code");
        if (Constant.isProd() && !("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode))) {
            return params.get("err_code_des").toString();
        }
        return "";
    }

    public static String aliHandld(Map<String, Object> result, String key) {
        Map<String, Object> map = (Map<String, Object>) result.get(key);
        if (Constant.isProd() && !map.get("msg").equals("Success")) {
            throw new BizException(BizExceptionEnum.ERROR.getCode(), "支付宝请求出错" + map.get("msg"));
        }
        return "";
    }

    public static String createTradeNo(String tradeNo, String orderNo) {
        if (StringUtil.isEmpty(tradeNo) && StringUtil.isNotEmpty(orderNo)) {
            //测试环境TGSP  正式环境GSP
            tradeNo = RedisUtil.getServiceKeyHaveDateByType(ServiceKeyTypeEnum.TGSP.getValue());
            if (StaticConfigUtil.getEnv().contains(EnvironmentEnum.PREPUBLISH.getCode())) {
                tradeNo = RedisUtil.getServiceKeyHaveDateByType(ServiceKeyTypeEnum.TRADING.getValue());
            }
            RedisUtil.add(RedisKeys.orders.setKey(tradeNo), orderNo);
            RedisUtil.expireDay(RedisKeys.orders.getKey(tradeNo), 1);
        }
        return tradeNo;
    }

}
