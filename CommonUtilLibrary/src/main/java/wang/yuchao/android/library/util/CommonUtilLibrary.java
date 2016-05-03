package wang.yuchao.android.library.util;

import android.content.Context;

/**
 * Created by wangyuchao on 16/5/3.
 */
public class CommonUtilLibrary {

    private static Context mContext;

    public static void init(Context context){
        mContext = context;
    }

    public static Context getAppContext(){
        return mContext;
    }
}
