package com.jkstack.dsm.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密，使用UTF-8编码
 *
 * @author lifang
 * @since 2020-10-20
 */
public final class MD5Util {

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_MD5_SALT = "d358b857-89c9-441b-9d85-50f0241e0d92";

    private MD5Util() {
    }

    /**
     * Used building output as Hex
     */
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 对字符串进行MD5加密， 默认使用UTF-8
     *
     * @param text 明文
     * @return 密文
     */
    public static String sign(String... text) {
        if (text.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String t : text) {
                sb.append(t);

            }
            return getMD5(sb.toString());
        }
        throw new IllegalStateException("text is null");
    }

    /**
     * 对字符串进行MD5加密,并且使用默认salt加密， 默认使用UTF-8
     *
     * @param text 明文
     * @return 密文
     */
    public static final String getMD5DefaultSalt(String text){
        return getMD5(text + DEFAULT_MD5_SALT);
    }


    /**
     * 对字符串进行MD5加密， 默认使用UTF-8
     *
     * @param text 明文
     * @param count 加密次数
     * @return 密文
     */
    public static String getMD5(String text, int count) {
        int i = 0;
        do {
            text = getMD5(text);
            i++;
        }while (i <= count);
        return text;
    }


    /**
     * 对字符串进行MD5加密
     *
     * @param text        明文
     * @return 密文
     */
    public static String getMD5(String text) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }
        try {
            msgDigest.update(text.getBytes(DEFAULT_CHARSET)); // 注意是按照指定编码形式签名
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("System doesn't support your  EncodingException.");
        }
        byte[] bytes = msgDigest.digest();
        return new String(encodeHex(bytes));
    }

    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }

}
