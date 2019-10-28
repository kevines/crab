/**
 * BEYONDSOFT.COM INC
 */
package com.wentuo.crab.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * BigDecimal计算和转化工具类
 * @author zhangqiu
 * @version $Id: DecimalCalculateUtil.java, v 0.1 2017年9月14日 下午8:13:24 zhangqiu Exp $
 */
public class DecimalCalculateUtil {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    /**
     * 将金额计算成以分为单位的long值<br>
     * 直接去尾，不保留小数
     *
     * @param decimal
     * @return
     */
    public static long getCentLongValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0L;
        }
        // 四舍五入
        BigDecimal cal = decimal.multiply(ONE_HUNDRED).setScale(0, BigDecimal.ROUND_HALF_UP);
        return cal.longValue();
    }

    /**
     * 将分为单位的long值转化成元为单位的2位小数字符串
     *
     * @param num
     * @return
     */
    public static String getYuanValueFromCent(Long num) {
        if (num == null) {
            return "";
        }
        BigDecimal decimal = new BigDecimal(num);
        // 四舍五入
        BigDecimal cal = decimal.divide(ONE_HUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP);
        return cal.toString();
    }

    /**
     * 将分为单位的long值转化成元为单位的2位小数的BigDecimal
     *
     * @param num
     * @return
     */
    public static BigDecimal getYuanDecimalFromCent(Long num) {
        if (num == null) {
            return null;
        }
        BigDecimal decimal = new BigDecimal(num);
        // 四舍五入
        BigDecimal cal = decimal.divide(ONE_HUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP);
        return cal;
    }

    /**
     * 将分为单位的long值转化成元为单位的2位小数的BigDecimal
     *
     * @param num
     * @return
     */
    public static BigDecimal getYuan(BigDecimal num) {
        if (num == null) {
            return null;
        }
        // 四舍五入
        BigDecimal cal = num.divide(ONE_HUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP);
        return cal;
    }

    /**
     * 将分为单位的long值转化成元为单位的4位小数的BigDecimal
     *
     * @param num
     * @return
     */
    public static BigDecimal getYuan4(BigDecimal num) {
        if (num == null) {
            return null;
        }
        // 四舍五入
        BigDecimal cal = num.divide(ONE_HUNDRED).setScale(4, BigDecimal.ROUND_HALF_UP);
        return cal;
    }

    /**
     * 将价格字符串转换为元
     *
     * @param amount
     * @return
     */
    public static long getYuanFromLong(Long amount) {

        long amountOut = amount / 100;

        return amountOut;
    }

    /**
     * 将价格字符串转换为分
     *
     * @param amount
     * @return
     */
    public static long getCentFromString(String amount) {
        if (StringUtils.isBlank(amount)) {
            return -1;
        }
        long amountOut = (long) (Double.parseDouble(amount) * 100);

        return amountOut;
    }

}
