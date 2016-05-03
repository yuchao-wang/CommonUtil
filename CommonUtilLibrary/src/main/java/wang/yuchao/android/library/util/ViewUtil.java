package wang.yuchao.android.library.util;

import android.view.View;

/**
 * Created by wangyuchao on 15/12/8.
 */
public class ViewUtil {
    /**
     * 替代findViewById方法
     */
    public static <T extends View> T find(View view, int id) {
        return (T) view.findViewById(id);
    }
}
