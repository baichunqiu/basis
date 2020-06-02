package com.kit;

import android.os.Environment;

import java.io.File;

/**
 * @author: BaiCQ
 * @ClassName: KitPath
 * @date: 2018/8/17
 * @Description: KitPath 公共路径相关的get set 方法
 */
public class KPath {

    /**
     * @param sdRoot sd卡根目录名称
     */
    public static void setSDRoot(String sdRoot) {
        KConstant.SD_ROOT_NAME = sdRoot;
    }

    /**
     * @param cacheRoot data/data 应用缓存目录名称
     */
    public static void setCacheRoot(String cacheRoot) {
        KConstant.CACHE_ROOT_NAME = cacheRoot;
    }

    /**
     * @param spFileName sp 缓存文件的 默认文件名
     */
    public static void setSPFileName(String spFileName) {
        KConstant.SP_FILE_NAME = spFileName;
    }

    public static String getSDRootPath() {
        String sd_root = KConstant.SD_PATH + KConstant.SD_ROOT_NAME + File.separator;
        File file = new File(sd_root);
        if (!file.exists()) {
            file.mkdirs();
        }
        return sd_root;
    }

    public static String getCacheRootPath() {
        String sd_root = KConstant.CACHE_PATH + KConstant.CACHE_ROOT_NAME + File.separator;
        File file = new File(sd_root);
        if (!file.exists()) {
            file.mkdirs();
        }
        return sd_root;
    }

    /**
     * 获取文件存储根路径：
     * 外部存储可用，返回外部存储路径:/storage/emulated/0/Android/data/包名/files
     * 外部存储不可用，则返回内部存储路径：data/data/包名/files
     */
    public String getFilesPath() {
        String filePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //外部存储可用:/storage/emulated/0/Android/data/包名/files
            filePath = UIKit.getContext().getExternalFilesDir(null).getPath();
        } else {
            //外部存储不可用，内部存储路径：data/data/com.learn.test/files
            filePath = UIKit.getContext().getFilesDir().getPath();
        }
        return filePath;
    }

    /**
     * 获取文件存储根路径：
     * 外部存储可用，返回外部存储路径:/storage/emulated/0/Android/data/包名/cache
     * 外部存储不可用，则返回内部存储路径：data/data/包名/cache
     */
    public String getCachePath() {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //外部存储可用：/storage/emulated/0/Android/data/包名/cache
            cachePath = UIKit.getContext().getExternalCacheDir().getPath();
        } else {
            //外部存储不可用：/data/data/com.learn.test/cache
            cachePath = UIKit.getContext().getCacheDir().getPath();
        }
        return cachePath;
    }
}
