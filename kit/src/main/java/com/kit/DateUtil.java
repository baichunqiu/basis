package com.kit;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author: BaiCQ
 * @ClassName: DateUtil
 * @Description: 日期转换工具类
 */
public class DateUtil {
    public final static String TAG = "DateUtil";
    public final static String DEF_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";

    /**
     * 分割符：日期格式定义三种分割符：S、L 和 H
     * S:  /
     * L:  -
     * H:汉字 年、月、日
     */
    public enum DateFt {
        yMd("yyyyMMdd"),// 20160927
        ySMSd("yyyy/MM/dd"),// 2016/09/27
        yLMLd("yyyy-MM-dd"),// 2016-09-27
        yHMHdH("yyyy年MM月dd日"),//2016年09月27日
        yM("yyyyMM"),// 201609
        ySM("yyyy/MM"),// 2016/09
        yLM("yyyy-MM"),// 2016-09
        yHMH("yyyy年MM月"),//2016年09月
        Md("MMdd"),//0927
        MSd("MM/dd"),//09/27
        MLd("MM-dd"),//09-27
        MHdH("MM月dd日");//09月27日
        private String value;

        DateFt(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 分割符：时间格式定义两种分割符C和H
     * C:  :
     * H:汉字 年、月、日
     */
    public enum TimeFt {
        Hms("HHmmss"),//150334
        HHmHsH("HH时mm分ss秒"),//15时30分21秒
        HCmCs("HH:mm:ss"),//15:03:34
        Hm("HHmm"),//1503
        HHmH("HH时mm分"),//15时30分
        HCm("HH:mm");//15:03
        //定义值
        private String value;

        TimeFt(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 格式拼接
     *
     * @param dft
     * @param tft
     * @return
     */
    public static String format(DateFt dft, TimeFt tft) {
        if (null == dft && null == tft) {
            return DEF_FORMAT;
        }
        if (null == dft) {
            return tft.value;
        }
        if (null == tft) {
            return dft.value;
        }
        return dft.value + " " + tft.value;
    }

    public static String date2String(Date date, TimeFt tft) {
        return date2String(date, format(null, tft));
    }

    public static String date2String(Date date, DateFt dft) {
        return date2String(date, format(dft, null));
    }

    public static String date2String(Date date, DateFt dft, TimeFt tFt) {
        return date2String(date, format(dft, tFt));
    }

    /**
     * 日期格式化输出
     *
     * @param date   日期对象
     * @param format 格式
     * @return
     */
    public static String date2String(Date date, String format) {
        // 接收待返回的时间字符串
        String resultTimeStr = "";
        if (date != null) {
            try {
                SimpleDateFormat formatPattern = new SimpleDateFormat(format);
                resultTimeStr = formatPattern.format(date);
            } catch (Exception e) {
                Logger.e(TAG, "date2String error:e = " + e.toString());
            }
        }
        return resultTimeStr;
    }

    public static Date string2Date(String dateStr, DateFt dft) {
        return string2Date(dateStr, format(dft, null));
    }

    public static Date string2Date(String dateStr, TimeFt tFt) {
        return string2Date(dateStr, format(null, tFt));
    }

    public static Date string2Date(String dateStr, DateFt dft, TimeFt tFt) {
        return string2Date(dateStr, format(dft, tFt));
    }

    /**
     * 日期格式的字符串 解析成日期对象
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date string2Date(String dateStr, String format) {
        Date date = null;
        if (!TextUtils.isEmpty(dateStr)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                date = sdf.parse(dateStr);
            } catch (Exception e) {
            }
        }
        return date;
    }

    public static Calendar date2Calendar(Date date) {
        if (null == date) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date calendar2Date(Calendar calendar) {
        if (null == calendar) return null;
        return calendar.getTime();
    }

    public static boolean sameDay(Calendar c1, Calendar c2) {
        if (c1 != null && c2 != null) {
            return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                    && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                    && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
        }
        return false;
    }

    public static boolean sameYear(Calendar c1, Calendar c2) {
        if (c1 != null && c2 != null) {
            return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
        }
        return false;
    }

    public static boolean sameMonth(Calendar c1, Calendar c2) {
        if (c1 != null && c2 != null) {
            return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                    && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH));
        }
        return false;
    }

    public static Calendar getCurrentCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    public static Date formatDate(int year, int month, int day, int hour, int min, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static Date startOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date endOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

}
