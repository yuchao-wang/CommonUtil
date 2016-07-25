package wang.yuchao.android.library.util;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wangyuchao on 15/7/30.
 */
public class BitmapUtil {

    /**
     * 网络获取Bitmap图片
     */
    public static Bitmap getBitmapFromUrl(String strUrl) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        BufferedInputStream buffer = null;
        try {
            URL url = new URL(strUrl);
            URLConnection conn = url.openConnection();
            conn.connect();
            // 获得图像的字符流
            inputStream = conn.getInputStream();
            buffer = new BufferedInputStream(inputStream, 8 * 1024);
            bitmap = BitmapFactory.decodeStream(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    /**
     * 获取本地的Bitmap对象
     */
    public static Bitmap getBitmapFromSDCard(String picFilePath) {
        Bitmap bitmap = null;
        try {
            File file = new File(picFilePath);
            bitmap = BitmapFactory.decodeFile(picFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 获取assets的Bitmap对象
     */
    public static Bitmap getBitmapFromAssets(String assetFileName) {
        Bitmap bitmap = null;
        try {
            AssetManager assetManager = CommonUtilLibrary.getAppContext().getResources().getAssets();
            InputStream inputStream = assetManager.open(assetFileName);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 获取drawable中的Bitmap对象
     */
    public static Bitmap getBitmapFromDrawable(int drawable) {
        Resources resources = CommonUtilLibrary.getAppContext().getResources();
        InputStream inputStream = resources.openRawResource(drawable);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }

    /**
     * 旋转图片
     *
     * @param orientation 旋转角度
     * @param src         原始图片
     * @return 旋转后的bitmap图片
     */
    public static Bitmap rotateBitmap(int orientation, Bitmap src) {
        Bitmap bitmapNew = null;
        if (orientation != 0) {
            Matrix matrix = new Matrix();
            matrix.reset();
            matrix.setRotate(orientation);
            bitmapNew = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        }
        return bitmapNew;
    }

    /**
     * 缩小Bitmap为指定大小
     *
     * @param bitmap    源图片Bitmap
     * @param dstWidth  指定宽度
     * @param dstHeight 指定高度
     * @return 指定大小的图片
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int dstWidth, int dstHeight) {
        Bitmap bitmapNew = null;
        if (dstWidth > 0 && dstHeight > 0) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scaleWidth = ((float) dstWidth) / width;
            float scaleHeight = ((float) dstHeight) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            bitmapNew = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        }
        return bitmapNew;
    }


    /**
     * 得到size大小的Bitmap
     *
     * @param srcBitmap 源图片
     * @param size      目的Bitmap大小最高值（0-srcBitmap bytes）
     * @return 压缩后的新的Bitmap
     */
    public static Bitmap compress(int size, Bitmap srcBitmap) {
        Bitmap bitmapCompressed = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            int quality = 100;
            srcBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
            while (quality != 0 && byteArrayOutputStream.toByteArray().length > size) {
                quality = quality - 10;
                byteArrayOutputStream.reset();
                srcBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
            }
            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            bitmapCompressed = BitmapFactory.decodeStream(byteArrayInputStream, null, null);/* 把ByteArrayInputStream数据生成图片 */
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmapCompressed;
    }

    /**
     * 按照比例压缩
     *
     * @param srcBitmap 源Bitmap
     * @param ratio     质量大小压缩比例(目的Bitmap大小与源Bitmap大小比例ratio%,范围是0-100,0表示最小值，100表示最大值)
     * @return 压缩后的新的Bitmap
     */
    public static Bitmap compress(Bitmap srcBitmap, int ratio) {
        Bitmap bitmapCompressed = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.reset();
            srcBitmap.compress(Bitmap.CompressFormat.JPEG, ratio, byteArrayOutputStream);
            byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            bitmapCompressed = BitmapFactory.decodeStream(byteArrayInputStream, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmapCompressed;
    }


    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
//
//    /**
//     * 创建二维码图片
//     */
//    public static Bitmap createQRBitmap(String content, int widthPix, int heightPix) {
//        Bitmap bitmap = null;
//        try {
//            //配置参数
//            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
//            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//            //容错级别
//            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//            //设置空白边距的宽度
////            hints.put(EncodeHintType.MARGIN, 2); //default is 4
//            // 图像数据转换，使用了矩阵转换
//            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);
//            int[] pixels = new int[widthPix * heightPix];
//            // 下面这里按照二维码的算法，逐个生成二维码的图片，
//            // 两个for循环是图片横列扫描的结果
//            for (int y = 0; y < heightPix; y++) {
//                for (int x = 0; x < widthPix; x++) {
//                    if (bitMatrix.get(x, y)) {
//                        pixels[y * widthPix + x] = 0xff000000;
//                    } else {
//                        pixels[y * widthPix + x] = 0xffffffff;
//                    }
//                }
//            }
//
//            // 生成二维码图片的格式，使用ARGB_8888
//            bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
//            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }

    /**
     * 在二维码中间添加Logo图案
     */
    public static Bitmap addLogo(Bitmap src, Bitmap logo) {
        try {
            //获取图片的宽高
            int srcWidth = src.getWidth();
            int srcHeight = src.getHeight();
            int logoWidth = logo.getWidth();
            int logoHeight = logo.getHeight();

            //logo大小为二维码整体大小的1/5
            float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
            Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();

            return bitmap;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
}
