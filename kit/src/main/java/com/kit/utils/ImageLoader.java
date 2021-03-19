package com.kit.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.kit.utils.Logger;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

/**
 * 默认：
 * 图片的宽高等于imageView的宽高.前提是你的imageView控件不能设置成wrap_content，必须要有宽高
 */
public class ImageLoader {
    private final static String TAG = "ImageLoader";

    public static void loadUrl(ImageView to, String url, @DrawableRes int def) {
        loadUrl(to, url, def, Size.SZ_AUTO);
    }

    /**
     * @param to   target imageView
     * @param url  URL
     * @param size Size
     */
    public static void loadUrl(ImageView to, String url, @DrawableRes int def, Size size) {
        if (TextUtils.isEmpty(url)) return;
        loadUri(to, Uri.parse(url), def, size);
    }

    /**
     * 加载 本地路径文件
     *
     * @param localPath
     * @param to
     */
    public static void loadLocal(ImageView to, String localPath, @DrawableRes int def) {
        loadLocal(to, localPath, def, Size.SZ_AUTO);
    }


    public static void loadLocal(ImageView to, String localPath, @DrawableRes int def, Size size) {
        if (TextUtils.isEmpty(localPath)) return;
        loadUri(to, Uri.fromFile(new File(localPath)), def, size);
    }

    public static void loadUri(ImageView to, Uri uri, @DrawableRes int def) {
        loadUri(to, uri, def, Size.SZ_AUTO);
    }

    /**
     * @param to   target imageView
     * @param uri  Uri
     * @param size STYPE
     */
    public static void loadUri(final ImageView to, Uri uri, @DrawableRes int def, Size size) {
        if (null == uri) return;
        RequestCreator creator = Picasso.get().load(uri).error(def);
        if (null == size || Size.SZ_AUTO == size) {
            creator.fit()
                    .centerCrop();
        } else {
            int[] sizes = Size.valueOf(size);
            creator.resize(sizes[0], sizes[1]).centerCrop();
        }
        creator.into(to);
    }

    public enum Size {
        SZ_AUTO,//fit
        SZ_100,
        SZ_150,
        SZ_200,
        SZ_250,
        SZ_320x240;

        /**
         * 返回size的宽高的数组
         *
         * @param size
         * @return
         */
        public static int[] valueOf(Size size) {
            int[] value;
            switch (size) {
                case SZ_100:
                    value = new int[]{100, 100};
                    break;
                case SZ_150:
                    value = new int[]{150, 150};
                    break;
                case SZ_200:
                    value = new int[]{200, 200};
                    break;
                case SZ_250:
                    value = new int[]{250, 250};
                    break;
                case SZ_320x240:
                    value = new int[]{320, 240};
                    break;
                default:
                    //默认
                    value = new int[]{100, 100};
                    break;
            }
            return value;
        }

    }

    /**
     * 等比压缩图片
     *
     * @param bm
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public static Bitmap zoomImg(Bitmap bm, int maxWidth, int maxHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        Logger.e(TAG, "bitmap：width = " + width + " height= " + height);
        Logger.e(TAG, "view  ：newWidth = " + maxWidth + " newHeight= " + maxHeight);
        // 计算缩放比例
        float scaleWidth = ((float) maxWidth) / width;
        float scaleHeight = ((float) maxHeight) / height;
        // 取得想要缩放的matrix参数
        float scale = scaleHeight > scaleWidth ? scaleWidth : scaleHeight;
        Logger.e(TAG, "scale = " + scale + " scaleWidth= " + scaleWidth + " scaleHeight = " + scaleHeight);
        if (scale > 1) {
            return bm;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
