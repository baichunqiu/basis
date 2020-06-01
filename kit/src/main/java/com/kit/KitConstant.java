package com.kit;

import android.os.Environment;

import java.io.File;

/**
 * @author: BaiCQ
 * @ClassName: KitConstant
 * @date: 2018/8/17
 * @Description: KitConstant 公共常量
 */
public class KitConstant {
    /*****************存储路径 path*****************/
    //SD卡根目录[/storage/sdcard0]
    public final static String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    //应用缓存目录[/data/data/应用包名/cache]
    public final static String CACHE_PATH = UIKit.getContext().getCacheDir().getAbsolutePath() + File.separator;

    //应用根目录名称 sd/0/kit
    protected static String SD_ROOT_NAME = "kit";
    ///data/data/应用包名/cache/kit
    protected static String CACHE_ROOT_NAME = "kit";
    //sp 默认文件名
    protected static String SP_FILE_NAME = "com_kit_common";
}
