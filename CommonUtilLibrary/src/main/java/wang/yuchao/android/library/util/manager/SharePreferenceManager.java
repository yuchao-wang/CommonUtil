package wang.yuchao.android.library.util.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import wang.yuchao.android.library.util.CommonUtilLibrary;
import wang.yuchao.android.library.util.DeviceUtil;

/**
 * Created by wangyuchao on 15/11/6.
 */
public class SharePreferenceManager {

    private static String FILE_NAME_APP = DeviceUtil.getPackageName().replaceAll("\\.", "_");

    protected static SharedPreferences getSharePreferences() {
        return CommonUtilLibrary.getAppContext().getSharedPreferences(FILE_NAME_APP, Context.MODE_PRIVATE);
    }

    public static <T> void set(T t) {
        SharedPreferences.Editor editor = getSharePreferences().edit();
        String result = new Gson().toJson(t);
        editor.putString(t.getClass().getSimpleName(), result);
        editor.apply();
    }

    public static <T> T get(Class<T> c) {
        String result = getSharePreferences().getString(c.getSimpleName(), null);
        try {
            return new Gson().fromJson(result, c);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> void clear(Class<T> c) {
        SharedPreferences.Editor editor = getSharePreferences().edit();
        editor.putString(c.getSimpleName(), null);
        editor.apply();
    }

    public static void clearAll() {
        SharedPreferences.Editor editor = getSharePreferences().edit();
        editor.clear();
        editor.apply();
    }

    public static String getUUID() {
        return getSharePreferences().getString("uuid", "");
    }

    public static void setUUID(String uuid) {
        getSharePreferences().edit().putString("uuid", uuid).apply();
    }

}
