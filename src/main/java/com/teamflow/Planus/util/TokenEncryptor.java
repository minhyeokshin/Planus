package com.teamflow.Planus.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class TokenEncryptor {

    private static final String ALGORITHM = "AES";

    private static String key = ""; // 16바이트 키 (128bit)

    public TokenEncryptor(@Value("${TOKEN_KEY}") String key) {
        TokenEncryptor.key = key;
    }


    // 🔐 암호화
    public static String encrypt(String plainText){
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 🔓 복호화
    public static String decrypt(String encryptedText) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, "UTF-8");
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}