package com.ebc.ecard.util;

import org.assertj.core.util.Streams;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class AES256 {

    public static final String EBC_AES256_KEY = "cGx1c2FwcDIwMjIxMjEyLmhhbndoYWxpZmVmcy5jb20=";
    private final static String alg = "AES/CBC/PKCS5Padding";

    public static String encrypt(String text, String key)
            throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException {

        if (key.getBytes().length < 16) {
            new OsppBizException("key는 16바이트 이상 이여야 합니다.");
        }
        byte[] iv = Arrays.copyOfRange(key.getBytes(), 0, 16);
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(iv, "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String cipherText, String key)
        throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException,
        NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        if(key.getBytes().length < 16) {
            new OsppBizException("key는 16바이트 이상 이여야 합니다.");
        }
        byte[] iv = Arrays.copyOfRange(key.getBytes(), 0, 16);
        Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(iv, "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);

        return new String(decrypted, "UTF-8");
    }
}
