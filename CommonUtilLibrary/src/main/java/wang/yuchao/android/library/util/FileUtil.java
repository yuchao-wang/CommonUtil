package wang.yuchao.android.library.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by wangyuchao on 15/9/6.
 */
public class FileUtil {

    public static void deleteFile(File file) {
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }
            for (int i = 0; i < childFiles.length; i++) {
                deleteFile(childFiles[i]);
            }
            file.delete();
        }
    }

    public static void deleteFile(String filePath) {
        deleteFile(new File(filePath));
    }

    /**
     * bitmap保存到文件
     */
    public static boolean saveBitmapToFile(Bitmap bitmap,
                                           boolean recycleBitmap,
                                           String filePath,
                                           Bitmap.CompressFormat format,
                                           int quality) {
        if (bitmap != null && !bitmap.isRecycled()) {
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(filePath);
                bitmap.compress(format, quality, outputStream);
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (recycleBitmap) {
                    if (bitmap != null && !bitmap.isRecycled()) {
                        try {
                            bitmap.recycle();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
