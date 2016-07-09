package wang.yuchao.android.library.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by wangyuchao on 16/7/9.
 */
public class SystemUtil {
    /**
     * 扫描刷新文件:强制扫描本地相册4.4以前
     */
    public static void scanToRefreshFile(String filePath) {
        MediaScannerConnection.scanFile(CommonUtilLibrary.getAppContext(), new String[]{filePath},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    /**
     * 强制扫描本地相册4.4以后
     */
    public static void scanToRefreshFile(Bitmap bitmap, String filePath) {
        MediaStore.Images.Media.insertImage(CommonUtilLibrary.getAppContext().getContentResolver(), bitmap, "", "");
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(filePath));
        intent.setData(uri);
        CommonUtilLibrary.getAppContext().sendBroadcast(intent);
    }

    /**
     * 打开文件
     *
     * @param file
     */
    public static void openFile(File file, Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(Uri.fromFile(file), type);
        //跳转
        context.startActivity(intent);
    }


    /**
     * 根据文件后缀名获得对应的MIME类型。
     */
    private static String getMIMEType(File file) {
        String type = "*/*";
        String fileName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fileName.substring(dotIndex, fileName.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < StaticUtil.MIME_MAP_TABLE.length; i++) { //MIME_MAP_TABLE??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(StaticUtil.MIME_MAP_TABLE[i][0]))
                type = StaticUtil.MIME_MAP_TABLE[i][1];
        }
        return type;
    }
}
