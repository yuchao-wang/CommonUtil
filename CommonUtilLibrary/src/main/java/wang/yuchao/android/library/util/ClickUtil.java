package wang.yuchao.android.library.util;

import android.os.SystemClock;

/**
 * 之所以不用System.currentTimeMillis()是因为他可修改
 * Created by wangyuchao on 15/7/30.
 */
public class ClickUtil {
    private static long lastClickTime = 0;
    private static long[] allClickTime = new long[8];

    /**
     * 判断是否为快速点击事件
     */
    public static boolean isQuickDoubleClick() {
        if ((SystemClock.uptimeMillis() - lastClickTime) <= 1000) {
            return true;
        } else {
            lastClickTime = SystemClock.uptimeMillis();
            return false;
        }
    }

    /**
     * 是否三秒内连续点击八次
     */
    public static boolean isQuickEightClick() {
        //将所有时间左移一个位置
        System.arraycopy(allClickTime, 1, allClickTime, 0, allClickTime.length - 1);
        allClickTime[allClickTime.length - 1] = SystemClock.uptimeMillis();
        if (allClickTime[0] > SystemClock.uptimeMillis() - 3000) {
            allClickTime = new long[8];
            return true;
        }
        return false;
    }
}
