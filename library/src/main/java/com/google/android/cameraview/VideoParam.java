package com.google.android.cameraview;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class VideoParam {
    private String filePath;
    public int bitRate = 30 * 1280 * 720;
    public int fps = 30;
    public ArrayList<String> childPaths = new ArrayList<>();

    private VideoParam(String filePath) {
        this.filePath = filePath;

    }

    public static VideoParam get(String filePath) {
        return new VideoParam(filePath);
    }

    public boolean available() {
        return !TextUtils.isEmpty(filePath) && bitRate > 0 && fps > 0;
    }

    public String getFilePath() {
        return filePath;
    }

    //    /**
//     * 检测文件路径
//     */
//    public void addResumePath() {
//        if (null == childPaths) childPaths = new ArrayList<>();
//        String tempPath = filePath.substring(0, filePath.lastIndexOf("."));
//        tempPath = tempPath + "_" + childPaths.size() + ".mp4";
//        childPaths.add(tempPath);
//    }
//
//    public String getFilePath() {
//        int size = null == childPaths ? 0 : childPaths.size();
//        if (size > 0) {
//            return childPaths.get(size - 1);
//        }
//        return filePath;
//    }
//
//    public List<String> getPaths() {
//        List<String> paths = new ArrayList<>();
//        paths.add(filePath);
//        if (null != childPaths) paths.addAll(childPaths);
//        childPaths.clear();
//        return paths;
//    }
}
