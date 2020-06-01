package com.kit;

import java.io.File;

/**
 * @author: BaiCQ
 * @ClassName: KitPath
 * @date: 2018/8/17
 * @Description: KitPath 公共路径相关的get set 方法
 */
public class KitPath {

    /**
     * @param sdRoot sd卡根目录名称
     */
    public static void setSDRoot(String sdRoot) {
        KitConstant.SD_ROOT_NAME = sdRoot;
    }

    /**
     * @param cacheRoot data/data 应用缓存目录名称
     */
    public static void setCacheRoot(String cacheRoot) {
        KitConstant.CACHE_ROOT_NAME = cacheRoot;
    }

    /**
     * @param spFileName sp 缓存文件的 默认文件名
     */
    public static void setSPFileName(String spFileName) {
        KitConstant.SP_FILE_NAME = spFileName;
    }

    public static String getSDRootPath() {
        String sd_root = KitConstant.SD_PATH + KitConstant.SD_ROOT_NAME + File.separator;
        File file = new File(sd_root);
        if (!file.exists()) {
            file.mkdirs();
        }
        return sd_root;
    }

    public static String getCacheRootPath() {
        String sd_root = KitConstant.CACHE_PATH + KitConstant.CACHE_ROOT_NAME + File.separator;
        File file = new File(sd_root);
        if (!file.exists()) {
            file.mkdirs();
        }
        return sd_root;
    }
}
