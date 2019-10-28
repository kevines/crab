package com.wentuo.crab.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lv on 2019/3/21 16:28.
 */
public class SimpleDateFormatUtils {

    private static final Logger logger = LoggerFactory.getLogger(SimpleDateFormatUtils.class);

    /**
     * 将日期时间归至零点
     *
     * @param date
     * @return
     */
    public static Date dateToBeginOfDay(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * 将日期时间归至 23时59分59秒999毫秒
     *
     * @param date
     * @return
     */
    public static Date dateToEndOfDay(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * 计算日期
     *
     * @param date 当前日期
     * @param day 要改变的天数
     * @return 改变后的日期
     */
    public static Date dateCal(Date date, int day) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, day);
            Date date1 = new Date(calendar.getTimeInMillis());
            return date1;
        }
        return null;
    }

    /**
     * 计算分钟
     *
     * @param date 当前时间
     * @param min 要改变的分钟数，可以是负数
     * @return 改变后的日期
     */
    public static Date dateCalMin(Date date, int min) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, min);
            Date date1 = new Date(calendar.getTimeInMillis());
            return date1;
        }
        return null;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的小时数
     *
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static Integer hoursBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (Exception e) {
            logger.error("解析时间失败", e);
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_houses = (time2 - time1) / (1000 * 3600);

        return Integer.parseInt(String.valueOf(between_houses));
    }

    /**
     * @param date
     * @return yyyy-MM-dd格式的String
     */
    public static final String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static final String dateToStringByYMDHMS(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        logger.debug("日期转字符串结果为：" + dateStr);
        return dateStr;
    }

    /**
     * date转成string
     *
     * @param date 时间
     * @param format 格式，如：‘yyyy-MM-dd HH:mm:ss’
     * @return
     */
    public static final String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateStr = sdf.format(date);
        logger.debug("日期转字符串结果为：" + dateStr + "  转换format为：" + format);
        return dateStr;
    }

    /**
     * yyyy-MM-dd string转date
     *
     * @param dateStr 时间字符串
     * @return
     * @throws ParseException
     */
    public static final Date stringToDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            return date;
        } catch (Exception e) {
            logger.error("stringToDate失败", e);
            return null;
        }
    }

    /**
     * yyyyMMdd string转date
     *
     * @param dateStr 时间字符串
     * @return
     * @throws ParseException
     */
    public static final Date stringToDateByMd(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = sdf.parse(dateStr);
            return date;
        } catch (Exception e) {
            logger.error("stringToDate失败", e);
            return null;
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss string转date
     *
     * @param dateStr 时间字符串
     * @return
     * @throws ParseException
     */
    public static final Date stringToDateByYMDHMS(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(dateStr);
            logger.debug("字符串转日期结果为：" + date);
            return date;
        } catch (Exception e) {
            logger.error("stringToDate失败");
            return null;
        }
    }

    /**
     * string转date
     *
     * @param dateStr 时间字符串
     * @param format 格式，如：‘yyyy-MM-dd HH:mm:ss’
     * @return
     * @throws ParseException
     */
    public static final Date stringToDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateStr);
            logger.debug("字符串转日期结果为：" + date + "  转换format为：" + format);
            return date;
        } catch (Exception e) {
            logger.error("stringToDate失败");
            return null;
        }
    }

    /**
     * 将时间转为毫秒数字符串
     *
     * @param date
     * @return
     */
    public static final String DateToMillionSecondsStr(Date date) {
        try {
            Long ms = date.getTime();
            return ms + "";
        } catch (Exception e) {
            logger.error("DateToMillionSecondsStr", e);
            return null;
        }

    }

    /**
     * 将时间归至当天的某一个时间，指定时分秒
     *
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static final Date dateToSpecificTime(Date date, int hour, int minute, int second) {
        if (date != null && hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59 && second >= 0
                && second <= 59) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), hour, minute, second);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * 将时间归至第二天的相同时间
     *
     * @param date
     * @return
     */
    public static final Date dateToTomorrow(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取日期的年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取日期的月份，0-11（注意不是1-12）
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 获取日期在当前周的顺序，周日-1，周六-7
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取日期在当前月的号数
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

}
