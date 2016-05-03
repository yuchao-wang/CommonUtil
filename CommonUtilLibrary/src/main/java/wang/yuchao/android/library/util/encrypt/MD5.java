package wang.yuchao.android.library.util.encrypt;

import java.security.MessageDigest;

/**
 * Created by wangyuchao on 16/1/19.
 */
public class MD5 {

    public static String stringToMD5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        StringBuilder result = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                result.append("0");
            result.append(Integer.toHexString(b & 0xFF));
        }
        return result.toString();
    }

}
