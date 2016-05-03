package wang.yuchao.android.library.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangyuchao on 15/11/9.
 */
public class VerifyUtil {

    /**
     * 是否是数字组成的字符串
     */
    public static boolean isValidDigits(String string) {
        return (!TextUtils.isEmpty(string)) && TextUtils.isDigitsOnly(string);
    }

    public static boolean isValidMobile(String mobile) {
        String regular = "^(1\\d{10})$";//暂时不管未开通号码段
        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        String regular = "^[a-zA-Z0-9]+[(-|.|_)a-zA-Z0-9]*@([a-z0-9]+(-[a-z0-9]+)*.[a-z]+)$";
        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 是否是合法的二代身份证
     */
    public static boolean isValidID(String id) {
        /* 首先舍去不符合条件的 */
        if (TextUtils.isEmpty(id)) return false;
        String regular = "^\\d{17}[0-9X]$";
        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(id);
        if (!matcher.matches()) {
            return false;
        }
        /* 乘积和 */
        int sum = 0;
        /* 17个参数 */
        int[] params = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        /* 下标对应的结果，下标为2的时候，对应的结果是X */
        char[] results = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        /* 循环17次，最后一个字符是校验码 */
        for (int i = 0; i < id.length() - 1; i++) {
            int temp = Integer.parseInt(id.charAt(i) + "");
            sum = sum + temp * params[i];
        }
        if (results[sum % 11] == id.charAt(17)) {
            return true;
        }
        return false;
    }

    public static boolean isValidIp(String ip) {
        String regular = "^\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}$";
        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(ip);
        if (!matcher.matches()) {
            return false;
        }
        String ipString[] = ip.split(".");
        for (int i = 0; i < 4; i++) {
            if (Integer.parseInt(ipString[i]) > 255) {
                return false;
            }
        }
        return true;

    }

    /**
     * 是否为合法域名
     */
    public static boolean isValidDomain(String domain) {
        String regular = "^[a-z0-9]+(-[a-z0-9]+)*.[a-z]+$";
        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(domain);
        return matcher.matches();
    }
}
