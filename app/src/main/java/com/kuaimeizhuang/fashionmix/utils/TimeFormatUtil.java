package com.kuaimeizhuang.fashionmix.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>时间转换格式</p>
 * Created on 17/5/18.
 *
 * @author Shi GuangXiang.
 */

public class TimeFormatUtil {
    /**
     * 显示多少天数
     *
     * @param createtime
     * @return
     */
    public static String getStandardDay(String createtime) {
        String interval;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//yyyy-MM-dd HH:mm:ss
            ParsePosition pos = new ParsePosition(0);
            Date d1 = (Date) sd.parse(createtime, pos);
            // Date().getTime()减去d1.getTime()得出的就是以前的时间与现在时间的时间间隔
            long time = new Date().getTime() - d1.getTime();// 得出的时间间隔是毫秒
            time = time / 1000;
            //            System.out.println(time + "间隔");
            if (time < 60 && time >= 0) {
                //                如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒
                interval = "刚刚";
                //            } else if (time / 1000 < 60 && time / 1000 > 0) {
                //                如果时间间隔小于60秒则显示多少秒前
                //                int se = (int) ((time % 60000) / 1000);
                //                interval = se + "秒前";
            } else if (time / 60 < 60 && time / 60 >= 1) {
                // 如果时间间隔小于60分钟则显示多少分钟前
                int m = (int) ((time % 3600) / 60);// 得出的时间间隔的单位是分钟
                interval = m + "分钟前";
            } else if (time / 3600 < 24 && time / 3600 >= 1) {
                // 如果时间间隔小于24小时则显示多少小时前
                int h = (int) (time / 3600);// 得出的时间间隔的单位是小时
                interval = h + "小时前";
            } else if (time / 86400 < 30 && time / 86400 >= 1) {
                // 如果时间间隔小于30天则显示多少天前
                int d = (int) (time / 86400);// 得出的时间间隔的单位是天
                interval = d + "天前";
                //                interval = "昨天";
            } else if (time >= 60 * 60 * 24 * 30 && time < 60 * 60 * 24 * 30 * 12) {
                // 如果时间间隔小于12个月则显示多少月前
                int m = (int) (time / (60 * 60 * 24 * 30));// 得出的时间间隔的单位是月
                interval = m + "月前";
            } else {
                // 大于等于12个月，则显示多少年前
                //                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //                ParsePosition pos2 = new ParsePosition(0);
                //                Date d2 = (Date) sdf.parse(createtime, pos2);
                int y = (int) (time / (60 * 60 * 24 * 30 * 12));// 得出的时间间隔的单位是年
                interval = y + "年前";
            }
        } catch (Exception e) {
            return createtime;
        }
        return interval;
    }

    /**
     * 获取时间间隔
     *
     * @param createtime 传入的时间格式必须类似于“yyyy-MM-dd HH:mm:ss”这样的格式
     * @return
     */
    public static String getStandardDate(String createtime) {
        String interval = null;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date d1 = (Date) sd.parse(createtime, pos);
            // 用现在距离1970年的时间间隔new
            // Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔
            long time = new Date().getTime() - d1.getTime();// 得出的时间间隔是毫秒
            //            System.out.println(time + "间隔");
            if (time / 1000 < 10 && time / 1000 >= 0) {
                // 如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒
                interval = "刚刚";
            } else if (time / 1000 < 60 && time / 1000 > 0) {
                // 如果时间间隔小于60秒则显示多少秒前
                int se = (int) ((time % 60000) / 1000);
                interval = se + "秒前";
            } else if (time / 60000 < 60 && time / 60000 > 0) {
                // 如果时间间隔小于60分钟则显示多少分钟前
                int m = (int) ((time % 3600000) / 60000);// 得出的时间间隔的单位是分钟
                interval = m + "分钟前";
            } else if (time / 3600000 < 24 && time / 3600000 >= 0) {
                // 如果时间间隔小于24小时则显示多少小时前
                int h = (int) (time / 3600000);// 得出的时间间隔的单位是小时
                interval = h + "小时前";
            } else if (time / 86400000 < 2 && time / 86400000 >= 0) {
                // 如果时间间隔小于3天则显示多少天前
                //                int h = (int) (time / 86400000);// 得出的时间间隔的单位是天
                //                interval = h + "天前";
                interval = "昨天";
            } else {
                // 大于3天，则显示正常的时间，但是不显示秒
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                ParsePosition pos2 = new ParsePosition(0);
                Date d2 = (Date) sdf.parse(createtime, pos2);
                interval = sdf.format(d2);
            }
        } catch (Exception e) {
            return createtime;
        }
        return interval;
    }

    /**
     * 获取时间间隔
     *
     * @param createtime 传入的时间格式必须类似于“yyyy-MM-dd HH:mm:ss”这样的格式
     * @return
     */
    public static String getStandardDateTwo(String createtime) {
        String interval = null;
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date d1 = (Date) sd.parse(createtime, pos);
            // 用现在距离1970年的时间间隔new
            // Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔
            long time = new Date().getTime() - d1.getTime();// 得出的时间间隔是毫秒
            //            System.out.println(time + "间隔");
            if (time / 1000 < 10 && time / 1000 >= 0) {
                // 如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒
                interval = "刚刚";
            } else if (time / 1000 < 60 && time / 1000 > 0) {
                // 如果时间间隔小于60秒则显示多少秒前
                int se = (int) ((time % 60000) / 1000);
                interval = se + "秒前";
            } else if (time / 60000 < 60 && time / 60000 > 0) {
                // 如果时间间隔小于60分钟则显示多少分钟前
                int m = (int) ((time % 3600000) / 60000);// 得出的时间间隔的单位是分钟
                interval = m + "分钟前";
            } else if (time / 3600000 < 24 && time / 3600000 >= 0) {
                // 如果时间间隔小于24小时则显示多少小时前
                int h = (int) (time / 3600000);// 得出的时间间隔的单位是小时
                interval = h + "小时前";
            } else if (time / 86400000 < 2 && time / 86400000 >= 0) {
                // 如果时间间隔小于3天则显示多少天前
                //                int h = (int) (time / 86400000);// 得出的时间间隔的单位是天
                //                interval = h + "天前";
                interval = "昨天";
            } else {
                // 大于3天，则显示正常的时间，但是不显示秒
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                ParsePosition pos2 = new ParsePosition(0);
                Date d2 = (Date) sdf.parse(createtime, pos2);
                interval = sdf.format(d2);
            }
        } catch (Exception e) {
            return createtime;
        }
        return interval;
    }


    public static String secToMinuteAndSecond(int time) {
        String timeStr = null;
        int minute;
        int second;
        if (time <= 0)
            return "0'00''";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = minute + "'" + unitFormat(second) + "''";
            }
        }
        return timeStr;
    }

    public static String secToMinuteAndSecond1(int time) {
        String timeStr = null;
        int minute;
        int second;
        if (time <= 0)
            return "0:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = minute + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


    public static String newMinuteAndSecond(int time) {
        String timeStr = null;
        int minute;
        int second;
        if (time <= 0)
            return "0'00''";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = minute + "'" + unitFormat(second);
            }
        }
        return timeStr + "''";
    }

    /**
     * 获取年月日
     *
     * @param date 时间
     * @return 时间
     */
    public static String getDateYear(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String resultData = "";
        try {
            Date parse = sdf.parse(date);
            resultData = sdf.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultData;

    }

    /**
     * 获取系统当前时间
     * @return 时间
     */
    public static String getNowDate() {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        String dateStr = String.valueOf(date);
        if (dateStr.length() == 1) {
            dateStr = "0" + dateStr;
        }
        return year + "-" + (month + 1) + "-" + dateStr;
    }


}
