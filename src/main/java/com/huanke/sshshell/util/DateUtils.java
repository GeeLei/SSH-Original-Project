package com.huanke.sshshell.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

public class DateUtils {
    private static Logger logger = Logger.getLogger(DateUtils.class);

    private static long MILLI_SECONDS_IN_DAY = 24 * 60 * 60 * 1000;

    /**
     * 将java.util.Date类型转成XMLGregorianCalendar类型
     * 
     * @author 崔凯
     * @param date
     *            java.util.Date类型
     * @return 成功返回XMLGregorianCalendar类型，否则返回Null.
     */
    public static XMLGregorianCalendar convertDateToXMLGregorianCalendar(
            Date date) {
        GregorianCalendar gregoriancalendar = new GregorianCalendar();
        gregoriancalendar.setTime(date);
        XMLGregorianCalendar xmlgregoriancalendar = null;
        try {
            xmlgregoriancalendar = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(gregoriancalendar);
        } catch (Exception e) {
            xmlgregoriancalendar = null;
        }
        return xmlgregoriancalendar;
    }

    /**
     * 将日期格式为指定的格式的形式
     * 
     * @param date
     *            日期
     * @param formater
     *            格式样式
     * @return type String
     * @throws Exception
     *             异常
     */
    public static String formatDate(Date date, String formater) {
        String dateformater = "yyyy-MM-dd";
        if (!StringUtility.IsNullOrEmpty(formater)) {
            dateformater = formater;
        }
        try {
            SimpleDateFormat simpledateformat = new SimpleDateFormat(
                    dateformater);
            return simpledateformat.format(date);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static String formatIntTimeToString(int time) {
        int hour = time / 100;
        int minute = time % 100;
        String mStr = minute < 10 ? "0" + minute : "" + minute;
        return hour + ":" + mStr;
    }

    public static int getDiffBetween(Date startDate, Date endDate) {
        int daysToStart = 0;
        if (endDate != null) {
            // 解析传入的日期参数距离开始日期有几天
            long time = endDate.getTime();
            long time2 = startDate.getTime();
            long diff = time - time2;
            long dayDiff = diff / MILLI_SECONDS_IN_DAY;
            daysToStart += dayDiff;
        }
        return daysToStart;
    }

    /**
     * 获取当前系统时间
     * 
     * @return type Date
     */
    public static Date getSystemDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 将XMLGregorianCalendar类型转成java.util.Date
     * 
     * @author 崔凯(Kevin)
     * 
     * @createdate 2015年7月8日 下午7:56:26
     *
     * @param xmlGregorianCalendar
     * @return java.util.Date类型
     * @return type Date
     */
    public static Date convertXMLGregorianCalendarToDate(
            XMLGregorianCalendar xmlGregorianCalendar) {
        try {
            GregorianCalendar ca = xmlGregorianCalendar.toGregorianCalendar();
            return ca.getTime();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
