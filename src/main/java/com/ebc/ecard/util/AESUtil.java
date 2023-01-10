package com.ebc.ecard.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class AESUtil {

    private final static String specalgorithm = "AES";
    private final static String algorithm = "AES/CBC/PKCS5Padding";
    private final static String hmacalgorithm = "HmacSHA256";
    private final static String encoding = "UTF-8";
    private final static byte[] ivBytes = {
        0x60, 0x10, 0x30, 0x40, 0x70, 0x21, 0x11, 0x62, 0x20, 0x30, 0x38, 0x10, 0x2F, 0x5F, 0x0F, 0x1F
    };

    public static String encryptAesToBase64(String plainText, String key)
        throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException,
        InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (plainText == null || key == null) {
            return null;
        }

        AlgorithmParameterSpec iv = new IvParameterSpec(ivBytes);
        SecretKeySpec k = new SecretKeySpec(key.getBytes(encoding), specalgorithm);
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.ENCRYPT_MODE, k, iv);

        byte encBytes[] = c.doFinal(plainText.getBytes(encoding));

        String base64Enc = new String(Base64.getEncoder().encode(encBytes));
        return base64Enc;
    }

    public static String decryptBase64ToAES(String base64Aes, String key) throws Exception {
        if (base64Aes == null || key == null) {
            return null;
        }
        AlgorithmParameterSpec iv = new IvParameterSpec(ivBytes);
        SecretKeySpec k = new SecretKeySpec(key.getBytes(encoding), specalgorithm);
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.DECRYPT_MODE, k, iv);

        byte b[] = Base64.getDecoder().decode(base64Aes.getBytes(encoding));
        byte decBytes[] = c.doFinal(b);

        return new String(decBytes, encoding);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static byte[] hex2Bytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    public static String getHmacSHA256ToStr(String key, String message)
        throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String hmac = null;

        SecretKeySpec skp = new SecretKeySpec(key.getBytes(encoding), hmacalgorithm);
        Mac mac = Mac.getInstance(hmacalgorithm);
        mac.init(skp);
        hmac = new String(Base64.getEncoder().encode(mac.doFinal(message.getBytes(encoding))));
        return hmac;
    }

    public static String getCurrentDatetime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return format.format(date);
    }
}