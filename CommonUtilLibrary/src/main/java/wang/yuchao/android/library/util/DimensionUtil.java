package wang.yuchao.android.library.util;

import android.content.Context;

/**
 * Created by wangyuchao on 15/7/30.
 */
public class DimensionUtil {

    private static Context context = CommonUtilLibrary.getAppContext();

    /**
     * dp 转换成 px值
     */
    public static int dpToPx(float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转换成 dp值
     */
    public static int pxToDp(float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int spToPx(float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int pxToSp(float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


}
