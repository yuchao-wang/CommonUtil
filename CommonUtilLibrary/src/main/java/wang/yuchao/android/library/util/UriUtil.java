package wang.yuchao.android.library.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by wangyuchao on 16/2/24.
 */
public class UriUtil {
    public static Uri getUriFromResource(int resource) {
        return Uri.parse("android.resource://" + CommonUtilLibrary.getAppContext().getPackageName() + File.separator + resource);
    }

    public static Uri getUriFromAssets(String relativeAssetFilePath) {
        return Uri.fromFile(new File("//assets" + File.separator + relativeAssetFilePath));
    }


    /**
     * @param contentPath content://media/external/images/media/***
     * @return /sdcard/test/***
     */
    public static String parseFromContentUriToFileUriPath(String contentPath, Activity activity) {
        Uri uri = Uri.parse(contentPath);
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();

        String filePath = actualimagecursor.getString(actual_image_column_index);
//        Uri fileUri = Uri.fromFile(filePath);
        return filePath;
    }


    /**
     * @param filePath /sdcard/test/***
     * @return content://media/external/images/media/***
     */
    public static String parseFromFileUriPathToContentUri(String filePath, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
        Uri baseUri = Uri.parse("content://media/external/images/media");
        Uri resultUri = Uri.withAppendedPath(baseUri, "" + id);

        return resultUri.toString();
    }
}