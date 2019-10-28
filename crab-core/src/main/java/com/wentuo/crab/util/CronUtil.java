package com.wentuo.crab.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lv
 * @date 2019/4/28 20:45
 */
public class CronUtil {
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }

    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

}
