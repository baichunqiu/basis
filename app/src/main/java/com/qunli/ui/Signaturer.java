package com.qunli.ui;

import android.annotation.TargetApi;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 签名工具
 */
public class Signaturer {
    private final static String TAG = "Signaturer";
    private final static String HMAC_ALGORITHM = "HmacSHA256";
    private final static char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 生成签名，开发版本oracle jdk 1.8.0_221
     *
     * @param secretId        邮件下发的secret_id
     * @param secretKey       邮件下发的secret_key
     * @param httpMethod      http请求方法 GET/POST/PUT等
     * @param headerNonce     X-TC-Nonce请求头，随机数
     * @param headerTimestamp X-TC-Timestamp请求头，当前时间的秒级时间戳
     * @param requestUri      请求uri，eg：/v1/meetings
     * @param requestBody     请求体，没有的设为空串
     * @return 签名，需要设置在请求头X-TC-Signature中
     * @throws NoSuchAlgorithmException e
     * @throws InvalidKeyException      e
     */
    @TargetApi(26)
    public static String sign(String secretId, String secretKey, String httpMethod, String headerNonce, String headerTimestamp, String requestUri, String requestBody)
            throws NoSuchAlgorithmException, InvalidKeyException {
        String tobeSig = httpMethod
                + "\nX-TC-Key=" + secretId
                + "&X-TC-Nonce=" + headerNonce
                + "&X-TC-Timestamp=" + headerTimestamp
                + "\n" + requestUri
                + "\n" + requestBody;
        Log.e(TAG, "sign: tobeSig = \n" + tobeSig);
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), mac.getAlgorithm());
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(tobeSig.getBytes(StandardCharsets.UTF_8));
        String hexHash = bytesToHex(hash);
        return new String(Base64.getEncoder().encode(hexHash.getBytes(StandardCharsets.UTF_8)));
    }

    public static String bytesToHex(byte[] bytes) {
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) {
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }
        return new String(buf);
    }


    public final static String APP_ID = "200000037";
    public final static String SECRET_ID = "toIruTB2Av3fepMDUWjHc7z1Lxl8mRG6KNk9";
    public final static String SECRET_KEY = "W8SPk7ib4decwfZrxIzYBaF91MO2vhns";
    public final static String HOST = "https://developer.meeting.tencent.com";
//    public final static String HOST = "https://api.dev.qunlivideo.com";

    public static String sign(String method, String headerNonce, String timestamp, String requestUri, String body) {
        try {
            String signStr = sign(SECRET_ID, SECRET_KEY, method, headerNonce, timestamp, requestUri, body);
            Log.e(TAG, "sign: signStr = " + signStr);
            return signStr;
        } catch (Exception e) {
        }
        return null;
    }

    public static String signForGet(String headerNonce, String timestamp, String requestUri) {
        return sign("GET", headerNonce, timestamp, requestUri, "");
    }

    public static String signForPost(String headerNonce, String timestamp, String requestUri, String body) {
        return sign("POST", headerNonce, timestamp, requestUri, body);
    }


}