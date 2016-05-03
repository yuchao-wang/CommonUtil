package wang.yuchao.android.library.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络帮助类
 * Created by wangyuchao on 15/9/6.
 */
public class NetworkUtil {

    private static Context context = CommonUtilLibrary.getAppContext();

    public static boolean isNetworkConnected() {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否为wifi环境
     */
    public static boolean isWifiConnected() {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null
                        && networkInfo.isConnected()
                        && networkInfo.isAvailable()
                        && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isMobileConnected() {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null
                        && networkInfo.isConnected()
                        && networkInfo.isAvailable()
                        && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return true;
                }
            }
        }
        return false;
    }
}
