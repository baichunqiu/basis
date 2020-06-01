package com.kit;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

/**
 * 默认：
 * 图片的宽高等于imageView的宽高.前提是你的imageView控件不能设置成wrap_content，必须要有宽高
 */
public class ImageLoader {

    public static void loadUrl(ImageView to, String url) {
        loadUrl(to, url, Size.SZ_AUTO);
    }

    /**
     * @param to   target imageView
     * @param url  URL
     * @param size Size
     */
    public static void loadUrl(ImageView to, String url, Size size) {
        if (TextUtils.isEmpty(url)) return;
        loadUri(to, Uri.parse(url), size);
    }

    /**
     * 加载 resourceId
     *
     * @param resouceId
     * @param to
     */
    public static void loadResId(ImageView to, int resouceId) {
        if (resouceId < 0) return;
        Picasso.get()
                .load(resouceId)
                .fit()
                .into(to);
    }

    /**
     * 加载 本地路径文件
     *
     * @param localPath
     * @param to
     */
    public static void loadLocalPath(ImageView to, String localPath) {
        loadLocalPath(to, localPath, Size.SZ_AUTO);
    }

    public static void loadLocalPath(ImageView to, String localPath, Size size) {
        if (TextUtils.isEmpty(localPath)) return;
        loadUri(to, Uri.parse(localPath), size);
    }

    /**
     * 加载本地文件
     *
     * @param to
     * @param file
     */
    public static void loadFile(ImageView to, File file) {
        loadFile(to, file, Size.SZ_AUTO);
    }

    public static void loadFile(ImageView to, File file, Size size) {
        if (null == file || !file.exists()) return;
        loadUri(to, Uri.fromFile(file), size);
    }

    public static void loadUri(ImageView to, Uri uri) {
        loadUri(to, uri, Size.SZ_AUTO);
    }

    /**
     * @param to   target imageView
     * @param uri  Uri
     * @param size STYPE
     */
    public static void loadUri(final ImageView to, Uri uri, Size size) {
        if (null == uri) return;
        RequestCreator creator = Picasso.get().load(uri);
        if (null == size || Size.SZ_AUTO == size) {
            creator.fit();
        } else {
            int[] sizes = Size.valueOf(size);
            creator.resize(sizes[0], sizes[1]);
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
}
