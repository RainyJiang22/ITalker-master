package net.jacky.italker.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author jacky
 * @version 1.0.0
 * @date 2020/2/23
 * 时间工具类
 */
public class DateTimeUtil {


    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yy-mm-dd", Locale.ENGLISH);

    /**
     * 获取一个简单的时间字符串
     *
     * @param date Date
     * @return 时间字符串
     */
    public static String getSampleDate(Date date) {
        return FORMAT.format(date);
    }
}
