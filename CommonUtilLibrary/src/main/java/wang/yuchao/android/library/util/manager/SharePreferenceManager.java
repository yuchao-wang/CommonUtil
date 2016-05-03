package wang.yuchao.android.library.util.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import wang.yuchao.android.library.util.CommonUtilLibrary;
import wang.yuchao.android.library.util.DeviceUtil;

/**
 * SharePreference存储分为两类：
 * <pre>
 * 0.存取对象最好用GSON
 * 1.应用级别(主要是变量存储,应用全局唯一的，举例：手机唯一标识，每个手机的唯一登陆的用户个人信息)
 * 2.用户级别(每个用户的信息,目前很少用到，暂时没有)
 * </pre>
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
