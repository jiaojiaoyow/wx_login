package com.example.demo.Util;


import com.example.demo.Error.CommonErrorCode;
import com.example.demo.Error.ErrorCodeException;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by codedrinker on 2018/11/25.
 */

public class DigestUtil {
    public static void checkDigest(String rawData, String sessionKey, String signature) {
        // 调用 apache 的公共包进行 SHA1 加密处理
        String sha1 = DigestUtils.sha1Hex((rawData + sessionKey).getBytes());
        boolean equals = sha1.equals(signature);
        if (!equals) {
            throw new ErrorCodeException(CommonErrorCode.SIGNATURE_ERROR);
        }
    }
}
