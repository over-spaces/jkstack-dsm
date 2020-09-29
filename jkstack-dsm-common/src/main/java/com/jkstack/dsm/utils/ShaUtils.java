package com.jkstack.dsm.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;

public final class ShaUtils {
    private static char[] HEX_CHARS;

    private ShaUtils() {
    }

    public static String sha1(String text) {
        Objects.requireNonNull(text, "input text not null");

        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            return hex(md.digest(text.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException var2) {
            throw new IllegalStateException("Unable to calculate hash. No SHA1 hasher available in this Java implementation", var2);
        } catch (UnsupportedEncodingException var3) {
            throw new IllegalStateException("Unable to calculate hash. UTF-8 encoding is not available in this Java implementation", var3);
        }
    }

    public static String dSha1(String text) {
        return sha1(sha1(text));
    }

    public static String hex(byte[] data) {
        Objects.requireNonNull(data);
        return hex(data, 0, data.length);
    }

    public static String hex(byte[] data, int offset, int length) {
        Objects.requireNonNull(data);

        assert offset >= 0 : "The offset must be positive";

        assert offset < data.length : "The offset must be lower than the length of the data";

        assert length >= 0 : "The requested length must be positive";

        assert length <= data.length : "The requested length must be equal to or lower than the length of the data";

        StringBuilder buf = new StringBuilder(length * 2);

        for (int i = offset; i < offset + length; ++i) {
            byte b = data[i];
            buf.append(HEX_CHARS[(b & 240) >>> 4]);
            buf.append(HEX_CHARS[b & 15]);
        }

        return buf.toString();
    }


    public static String decryptWehcat(byte[] key, byte[] iv, byte[] encData) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        return new String(cipher.doFinal(encData), "UTF-8");
    }
}
