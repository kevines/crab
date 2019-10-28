/**
 * BEYONDSOFT.COM INC
 */
package com.wentuo.crab.util;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间转换工具类
 *
 * @author yulijun
 * @version $Id: DateUtils.java, v 0.1 2017/6/5 18:15 yulijun Exp $$
 */
public class DateUtils {

    public static final String DEFAULT = "yyyy-MM-dd";

    public static final String DEFAULT1 = "yyyyMMdd";

    public static final String YYYYMMDD = "yyyy-MM-dd";

    public static final String YYYYMM = "yyyy-MM";

    public static final String YYYYMMDD_NO_SEPARATOR = "yyyyMMdd";

    public static final String YYYYMMDDHH = "yyyy-MM-dd HH";

    public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";

    public static final String YYYYMMDDHHMMSS1 = "yyyyMMddHHmmss";

    public static final String MMddHHmm = "MMddHHmm";

    public static final String YYYYMMDD000000 = "yyyy-MM-dd 00:00:00";

    public static final String YYYYMMDD0000001 = "yyyy-MM-dd 23:59:59";

    public static final String YYYYMMDDHH0000 = "yyyy-MM-dd HH:00:00";

    public static final String YYYYMMDDHHMM00 = "yyyy-MM-dd HH:mm:00";

    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String MMDDHHMM = "MM-dd HH:mm";

    public static final String CHN_YMD = "yyyy年MM月dd日";

    public static final String CHN_YMD_HH = "yyyy-MM-dd HH时";

    public static final String CHN_YMD_HHMM = "yyyy年MM月dd日HH:mm";

    public static final String CHN_MDHHMM = "MM月dd日HH时mm分";

    public static final String CHN_YMDHHMMSS = "yyyy年MM月dd日 HH时mm分ss秒";

    public static final String CHN_MD = "MM月dd日";


    /**
     * 判断当钱时间是否在一个给定的时间段内
     * @return
     */
    public static Integer compareTimeArea(Date startTime, Date endTime) {
        int num;
        Date time = new Date();   //请求时当前时间
        Calendar now = Calendar.getInstance();
        now.setTime(time);
        Calendar before = Calendar.getInstance();
        before.setTime(startTime);
        Calendar after = Calendar.getInstance();
        after.setTime(endTime);
        if (now.after(after)) {   //表示超过该时间段
            num = 3;
        } else if (now.before(before)){   //表示未到设定的时间段
            num = 1;
        } else {   //表示处在设定的时间段内
            num = 2;
        }
        return num;
    }

    /**
     * 比较两个日期前后
     * @param dateOne
     * @param dateTwo
     * @return
     */
    public static Integer compareTimes(Date dateOne, Date dateTwo) {
        Calendar calendarOne = Calendar.getInstance();
        calendarOne.setTime(dateOne);
        Calendar calendarTwo = Calendar.getInstance();
        calendarTwo.setTime(dateTwo);
        if (calendarOne.before(calendarTwo)) { //小于
            return -1;
        } else if (calendarOne.after(calendarTwo)) {  //大于
            return 1;
        } else {  //等于
            return 0;
        }
    }


    /**
     * date转换成string
     *
     * @param date    Date对象
     * @param pattern 转换格式
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null || StringUtils.isBlank(pattern)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * Calendar转换成string
     *
     * @param calendar Calendar对象
     * @param pattern  转换格式
     * @return
     */
    public static String format(Calendar calendar, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(calendar.getTime());
    }

    /**
     * string转换string
     */
    public static String parse2String(String datestring, String pattern) {
        if (StringUtils.isBlank(pattern) || StringUtils.isBlank(datestring)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.format(sdf.parse(datestring));
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * string转换Date
     *
     * @param datestring 日期格式字符串
     * @param pattern    转换格式
     * @return Date对象
     */
    public static Date parse2Date(String datestring, String pattern) {
        if (StringUtils.isBlank(pattern) || StringUtils.isBlank(datestring)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(datestring);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * string转换Calender
     *
     * @param datestring 日期格式字符串
     * @param pattern    转换格式
     * @return Calendar对象
     */
    public static Calendar parse2Calendar(String datestring, String pattern) {
        if (StringUtils.isBlank(pattern) || StringUtils.isBlank(datestring)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(datestring));
            return calendar;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取传入时间所属月的首日
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        return firstDayOfMonth;
    }

    /**
     * 获取传入时间所属月的末尾日
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDayOfMonth = calendar.getTime();
        return lastDayOfMonth;
    }

    /**
     * 往后倒退年份
     *
     * @return
     */
    public static Date getBackYear(Integer num) {
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH);
        calendar.set(year - num, month, 1);
        return calendar.getTime();
    }

    /**
     * 往后倒退年份到12月31日
     *
     * @return
     */
    public static Date getBackEndYear(Integer num) {
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        calendar.set(year - num, Calendar.DECEMBER, 31);
        return calendar.getTime();
    }

    /**
     * 往后倒退年份到1月1日
     *
     * @return
     */
    public static Date getBackStartYear(Integer num) {
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        calendar.set(year - num, Calendar.JANUARY, 1);
        return calendar.getTime();
    }

    /**
     * 往前倒退num天
     *
     * @return
     */
    public static Date getBackDay(Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -num);
        return calendar.getTime();
    }

    /**
     * 往前倒退num天
     *
     * @return
     */
    public static String getBackDayStr(Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -num);
        return format(calendar.getTime(), YYYYMMDD);
    }

    /**
     * 添加月份
     *
     * @return
     */
    public static Date addMonth(Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONDAY, num);
        return calendar.getTime();
    }

    /**
     * 添加天数
     *
     * @return
     */
    public static Date addDay(Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, num);
        return calendar.getTime();
    }

    /**
     * 传入一个具体日期添加天数（包含当钱或者非当前日期都可）
     * @param date
     * @param num
     * @return
     */
    public static Date addDay(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        return calendar.getTime();
    }

    /**
     * 添加月份
     *
     * @return
     */
    public static Date addMonth(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONDAY, num);
        return calendar.getTime();
    }


    /**
     * 添加月份
     *
     * @return
     */
    public static String addMonthStr(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONDAY, num);
        return DateFormatUtils.format(calendar.getTime(), YYYYMM);
    }

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
     * 秒转成天数
     *
     * @param mm
     * @return
     */
    public static Long convert2Day(Long mm) {
        if (null == mm) {
            return 0L;
        }
        Long day = mm / (60 * 60 * 24);
        return day;
    }


    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        long i = convert2Day((date2.getTime() - date1.getTime()) / 1000);
        return (int) i;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long differentMs(Date date1, Date date2) {
        long i = (date2.getTime() - date1.getTime()) / 1000;
        return i;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @return
     */
    public static long differentMs(Date date, String amount) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(12, Integer.valueOf(amount));
        return differentMs(new Date(), gc.getTime());
    }


    /**
     * 天数转成秒数
     *
     * @param day
     * @return
     */
    public static Long convert2mill(Long day) {
        if (null == day) {
            return 0L;
        }
        return day * 24 * 60 * 60;
    }

    /**
     * 获得天的开始时间
     *
     * @param date
     * @return
     */
    public static Date getDayStartTime(Date date) {
        return org.apache.commons.lang.time.DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得天的结束时间
     *
     * @param date
     * @return
     */
    public static Date getDayEndTime(Date date) {
        Date beginTime = org.apache.commons.lang.time.DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
        return org.apache.commons.lang.time.DateUtils.addSeconds(org.apache.commons.lang.time.DateUtils.addDays(beginTime, 1), -1);
    }

    /**
     * 获得周的开始时间
     *
     * @param date
     * @return
     */
    public static Date getWeekStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
        return DateUtils.parse2Date(DateFormatUtils.format(calendar.getTime(), YYYYMMDD000000), YYYYMMDD000000);
    }

    /**
     * 获得周的结束时间
     *
     * @param date
     * @return
     */
    public static Date getWeekEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);

        calendar.add(Calendar.DATE, 6);
        Date queryBeginTime = calendar.getTime();
        return DateUtils.parse2Date(DateFormatUtils.format(queryBeginTime, YYYYMMDD0000001), YYYYMMDDHHMMSS);
    }

    /**
     * 获得周的开始时间
     *
     * @param date
     * @return
     */
    public static String getWeekStartTimeStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
        return DateFormatUtils.format(calendar.getTime(), YYYYMMDD);
    }

    /**
     * 获得周的结束时间
     *
     * @param date
     * @return
     */
    public static String getWeekEndTimeStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);

        calendar.add(Calendar.DATE, 6);
        Date queryBeginTime = calendar.getTime();
        return DateFormatUtils.format(queryBeginTime, YYYYMMDD);
    }

    /**
     * 获得月的开始时间
     *
     * @param date
     * @return
     */
    public static Date getMonthStartTime(Date date) {
        return DateUtils.parse2Date(format(getFirstDayOfMonth(date), YYYYMMDD000000), YYYYMMDDHHMMSS);
    }

    /**
     * 获得月的结束时间
     *
     * @param date
     * @return
     */
    public static Date getMonthEndTime(Date date) {
        return DateUtils.parse2Date(format(getLastDayOfMonth(date), YYYYMMDD0000001), YYYYMMDDHHMMSS);
    }

    /**
     * 获得月的开始时间
     *
     * @param date
     * @return
     */
    public static String getMonthStartTimeStr(Date date) {
        return format(getFirstDayOfMonth(date), YYYYMMDD);
    }

    /**
     * 获得月的结束时间
     *
     * @param date
     * @return
     */
    public static String getMonthEndTimeStr(Date date) {
        return format(getLastDayOfMonth(date), YYYYMMDD);
    }

    /**
     * 在指定时间上加特定的天数
     *
     * @param days 天数
     * @param date 指定的时间
     * @return
     */
    public static Date plusDay(int days, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 获得本周一
     *
     * @param date 当前时间
     * @return
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获得上周一时间
     *
     * @param date 当前时间
     * @return
     */
    public static Date geLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    /**
     * 给时间加上几个小时
     *
     * @param date
     * @param hour 需要加的时间
     * @return
     */
    public static Date dateAddHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);
        return cal.getTime();
    }

    /**
     * 判断是否是需要的时间格式
     * @param date
     * @param format
     * @return
     */
    public static boolean checkDate(String date,String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date d = null;
        try{
            d = df.parse(date);
        }catch(Exception e){
            //如果不能转换,肯定是错误格式
            return false;
        }
        String s1 = df.format(d);
        // 转换后的日期再转换回String,如果不等,逻辑错误.如format为"yyyy-MM-dd",date为
        // "2006-02-31",转换为日期后再转换回字符串为"2006-03-03",说明格式虽然对,但日期
        // 逻辑上不对.
        return date.equals(s1);
    }


    //进行时间比较
    public static int checkDatetime(Date time){
        //当前时间
        String beginTime = DateUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        //token时间
        String endTime = DateUtils.format(time,"yyyy-MM-dd HH:mm:ss");
        int compareTo=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date1 = format.parse(beginTime);
            Date date2 = format.parse(endTime);
            compareTo = date1.compareTo(date2);
            return compareTo;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return compareTo;
    }


    public static String doubleByString(Double doubles){
        if(doubles>0){
            int parameter=doubles.intValue();
            return String.valueOf(parameter);
        }
        return "";
    }


    /**
     * 获取银行卡后四位
     * @param cardNum
     * @return
     */
    public static String getCardTailNum(String cardNum) {
        StringBuffer tailNum = new StringBuffer();
        if (cardNum != null) {
            int len = cardNum.length();
            for (int i = len - 1; i >= len - 4; i--) {
                tailNum.append(cardNum.charAt(i));
            }
            tailNum.reverse();
        }
        return tailNum.toString();

    }
}
