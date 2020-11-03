package com.kit;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.Inflater;

/**
 * @author: BaiCQ
 * @ClassName: KToast
 * @date: 2018/4/4
 * @Description: ToastManager的备注 强制使用resouceId 替换message text
 */
public class ByteUtil {

    private final static String TAG = "ByteUtil";
    private final static char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    public static String bytes2Hex(byte[] bytes) {
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) {
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }
        return new String(buf);
    }

    public static void formatPrintHex(byte[] b) {
        formatPrintHex(b, -1);
    }

    /**
     * 6进制输出格式化输出byte[]
     *
     * @param bytes
     * @param plen  从index = 0开始 输出plen个byte，输出长度 slen < 0 输出全部
     */
    public static void formatPrintHex(byte[] bytes, int plen) {
        if (null == bytes || bytes.length <= 0) return;
        if (plen < 0 || plen > bytes.length) {
            plen = bytes.length;
        }
        String h = "";
        for (int i = 0; i < plen; i++) {
            String temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            h = h + " " + temp;
        }
        Logger.e(TAG, "total: " + bytes.length + "  showLen: " + plen + "  hex: " + h);
    }


    /**
     * byte[]指定起始位置和指定长度的len的子数组转换成long
     *
     * @param data  byte[]
     * @param begin 起始位置
     * @param len   指定长度
     * @return
     */
    public static long get(byte[] data, int begin, int len) {
        int end = begin + len;
        if (begin >= data.length || end >= data.length) return 0;
        long n = 0;
        for (; begin <= end; begin++) {
            n <<= 8;
            n += data[begin] & 0xFF;
        }
        return n;
    }

    /**
     * 1btye范围：-128~127
     * btye转换int 直接强转会有如下问题：小128的可以正常转换，大于128的会转换成负值
     *
     * @param b
     * @return 0x00~0xff
     */
    public static int byte2int(byte b) {
        return b & 0xff;
    }

    /**
     * 获取byte[]的子数组
     *
     * @param src
     * @param start
     * @param len
     * @return 子数组
     */
    public static byte[] subArray(byte[] src, int start, int len) {
        if (src == null) return null;
        if (start > src.length - 1) {
            return null;
        }
        //获取子数组的最大长度
        int maxSubLen = src.length - start;
        if (len > maxSubLen) len = maxSubLen;
        byte[] subs = new byte[len];
        System.arraycopy(src, start, subs, 0, subs.length);
        return subs;
    }

    /**
     * zip解压
     *
     * @param data
     * @return
     */
    public static byte[] unZipByte(byte[] data) {
        Inflater unzip = new Inflater();
        unzip.setInput(data);
        byte[] result = new byte[0];
        ByteArrayOutputStream o = new ByteArrayOutputStream(1);
        try {
            byte[] buf = new byte[1024];
            int got = 0;
            while (!unzip.finished()) {
                got = unzip.inflate(buf);
                o.write(buf, 0, got);
            }
            result = o.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            unzip.end();
        }
        return result;
    }

    public static int getCharLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) > 255) {
                length += 2;
            } else {
                length++;
            }
        }
        return length;
    }

    /**
     * nv21格式的yuv数据保存jpg文件
     *
     * @param data    yuv数据
     * @param mWidth
     * @param mHeight
     * @param picFile 保存文件路径
     * @return
     */
    public static boolean saveYuvToImageFile(byte[] data, int mWidth, int mHeight, String picFile) {
        BufferedOutputStream outYuvStream = null;
        FileOutputStream out = null;
        boolean flag = false;
        try {
            YuvImage yuvImage = new YuvImage(data, ImageFormat.NV21, mWidth, mHeight, null);
            out = new FileOutputStream(picFile);
            outYuvStream = new BufferedOutputStream(out);
            boolean success = yuvImage.compressToJpeg(new Rect(0, 0, mWidth, mHeight), 100, outYuvStream);
            if (success) {
                outYuvStream.flush();
                flag = true;
            }
        } catch (IOException e) {
            Logger.e(TAG, "getImageFileFromYuv：e = " + e);
        } finally {
            if (outYuvStream != null) {
                try {
                    outYuvStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return flag;
        }
    }

    /**
     * nv21格式的yuv数据保存jpg文件
     *
     * @param data    yuv数据
     * @param mWidth
     * @param mHeight
     * @param picFile 保存文件路径
     * @return
     */
    public static boolean saveJpegToImageFile(byte[] data, int mWidth, int mHeight, String picFile) {
        BufferedOutputStream outYuvStream = null;
        FileOutputStream out = null;
        boolean flag = false;
        try {
            YuvImage yuvImage = new YuvImage(data, ImageFormat.NV21, mWidth, mHeight, null);
            out = new FileOutputStream(picFile);
            outYuvStream = new BufferedOutputStream(out);
            boolean success = yuvImage.compressToJpeg(new Rect(0, 0, mWidth, mHeight), 100, outYuvStream);
            if (success) {
                outYuvStream.flush();
                flag = true;
            }
        } catch (IOException e) {
            Logger.e(TAG, "getImageFileFromYuv：e = " + e);
        } finally {
            if (outYuvStream != null) {
                try {
                    outYuvStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return flag;
        }
    }
}
