package wang.yuchao.android.library.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 转换帮助类
 * Created by wangyuchao on 15/11/9.
 */
public class ConvertUtil {

    public static Date parseDateToMillis(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把毫秒转换为日期（Java后台常用）
     *
     * @param millis 0 -> 正无穷
     */
    public static String parseMillisToDate1(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(millis));
    }

    public static String parseMillisToDate2(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(millis));
    }

    public static String parseMillisToDate3(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(millis));
    }

    /**
     * 把秒转换为日期（PHP后台常用）
     *
     * @param seconds 0 -> 正无穷
     */
    public static String parseSecondsToDate1(long seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(seconds * 1000));
    }

    public static String parseSecondsToDate2(long seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(seconds * 1000));
    }

    public static String parseSecondsToDate3(long seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(seconds * 1000));
    }

    /**
     * 把秒转换为时间
     *
     * @param seconds 0 - 24 * 60 * 60
     */
    public static String parseSecondsToTime(long seconds) {
        return seconds / 3600 + ":" + seconds % 3600 / 60 + ":" + seconds % 60;
    }


    /**
     * 格式化价格
     */
    public static String formatPrice(String price) {
        return "¥" + String.format("%.2f", Float.valueOf(price));
    }

    /**
     * 格式化百分比
     */
    public static String formatPercent(double percent) {
        return String.format("(%2.0f%%)", (float) (percent * 100));
    }

    /**
     * 转换为倒计时的时间「不超过一个小时的」
     */
    public static String parseToCountDownTime(long mills) {
        int minutes = (int) ((mills / 1000) % 3600 / 60);
        int seconds = (int) ((mills / 1000) % 3600 % 60);
        String minutesText = "" + minutes;
        String secondsText = "" + seconds;
        if (minutes < 10) {
            minutesText = "0" + minutes;
        }
        if (seconds < 10) {
            secondsText = "0" + seconds;
        }
        return minutesText + ":" + secondsText;
    }
}
