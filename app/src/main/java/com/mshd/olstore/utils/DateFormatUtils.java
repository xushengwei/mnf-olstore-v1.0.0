package com.mshd.olstore.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author xushengwei
 * @date 2019/1/8
 */
public class DateFormatUtils {
    public static DateFormat getMonthDayFormat(){

        return new SimpleDateFormat("MM月dd日");
    }

    public static DateFormat getMonthFormat(){

        return new SimpleDateFormat("MM月");
    }

    public static DateFormat getYearDayFormat(){

        return new SimpleDateFormat("yyyy年MM月dd日");
    }
}
