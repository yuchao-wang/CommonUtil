package wang.yuchao.android.library.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.UUID;

import wang.yuchao.android.library.util.manager.SharePreferenceManager;

/**
 * 应用设备信息相关，全局变量都不直接用
 * Created by wangyuchao on 15/11/6.
 */
public class DeviceUtil {

    private static Context context = CommonUtilLibrary.getAppContext();

    private static String deviceId = "";//也是IMEI
    private static String simSerialNumber = "";
    private static String androidId = "";

    private static String versionName = "";
    private static long versionCode;
    private static String appChannel = "";

    //TODO future 如果需要其他手机信息，可以直接调用getTelephonyManager()获取
    private static TelephonyManager telephonyManager;
    private static String uuid = "";

    private static String packageName = context.getPackageName();//初始化默认包名
    private static String systemVersion = "";

    /**
     * 得到UUID
     */
    public static String getUUID() {
        if (TextUtils.isEmpty(uuid)) {
            String tempUUID = SharePreferenceManager.getUUID();
            if (TextUtils.isEmpty(tempUUID)) {
                uuid = UUID.randomUUID().toString();
                SharePreferenceManager.setUUID(uuid);
            } else {
                uuid = tempUUID;
            }
        }
        return uuid;
    }

    /**
     * 是否有外置存储
     */
    public static boolean hasExternalStorage() {
        String strStatus = android.os.Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(strStatus)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return 设备型号
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * @return 设备制造商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    private static TelephonyManager getTelephonyManager() {
        if (telephonyManager == null) {
            telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
        return telephonyManager;
    }

    public static String getDeviceId() {
        if (TextUtils.isEmpty(deviceId)) {
            try {
                deviceId = getTelephonyManager().getDeviceId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deviceId;
    }

    public static String getIMEI() {
        return getDeviceId();
    }

    /**
     * SIM卡序列号
     */
    public static String getSimSerialNumber() {
        if (TextUtils.isEmpty(simSerialNumber)) {
            try {
                simSerialNumber = getTelephonyManager().getSimSerialNumber();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return simSerialNumber;
    }

    public static String getAndroidId() {
        if (TextUtils.isEmpty(androidId)) {
            androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return androidId;
    }

    public static String getMacAddress() {
        String macAddress = "";
        if (TextUtils.isEmpty(macAddress)) {
            try {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                macAddress = wifiInfo.getMacAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return macAddress;
    }

    public static String getVersionName() {
        if (TextUtils.isEmpty(versionName)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                versionName = packageInfo.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return versionName;

    }

    public static String getPackageName() {
        if (TextUtils.isEmpty(packageName)) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                packageName = packageInfo.packageName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return packageName;

    }

    public static long getVersionCode() {
        if (versionCode == 0) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                versionCode = packageInfo.versionCode;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return versionCode;
    }

    public static String getAppChannel() {
        if (TextUtils.isEmpty(appChannel)) {
            try {
                ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                //TODO update
                appChannel = applicationInfo.metaData.getString("UMENG_CHANNEL", "default_channel");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return appChannel;

    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface networkInterface = en.nextElement();
                for (Enumeration<InetAddress> enumerations = networkInterface.getInetAddresses(); enumerations.hasMoreElements(); ) {
                    InetAddress inetAddress = enumerations.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSystemVersion() {
        if (TextUtils.isEmpty(systemVersion)) {
            systemVersion = Build.VERSION.SDK_INT + "," + android.os.Build.VERSION.RELEASE;
        }
        return systemVersion;
    }

    /**
     * SIM卡简称
     */
    public static String getSimShortName() {
        String simShortName = "";
        String operatorString = getTelephonyManager().getSimOperator();
        if (!TextUtils.isEmpty(operatorString)) {
            if (operatorString.equals("46000") || operatorString.equals("46002")) {
                simShortName = "cmcc";//中国移动
            } else if (operatorString.equals("46001")) {
                simShortName = "cucc";//中国联通
            } else if (operatorString.equals("46003")) {
                simShortName = "ctcc";//中国电信
            }
        }
        return simShortName;
    }

    /**
     * 得到屏幕尺寸
     */
    public static Point getScreenSize() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(size);
        } else {
            display.getSize(size);
        }
        return size;
    }

}
