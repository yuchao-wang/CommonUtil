package wang.yuchao.android.library.util;

import java.io.File;

/**
 * Created by wangyuchao on 16/2/25.
 */
public class UrlUtil {
    public String getAssetsWebUrl(String relativeAssetFilePath) {
        return "file:///android_asset" + File.separator + relativeAssetFilePath;
    }
}
