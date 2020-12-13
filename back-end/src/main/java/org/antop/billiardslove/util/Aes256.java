package org.antop.billiardslove.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

/**
 * AES 암호화/복호화 유틸리티 클래스<br>
 * "AES/CBC/PKCS5Padding"이 보안에 취약하다고 하여 다른 알고리즘 사용<br>
 * 참조: https://www.javainterviewpoint.com/java-aes-256-gcm-encryption-and-decryption/
 *
 * @author antop
 */
@UtilityClass
public class Aes256 {
    /**
     * IV(initialization vector) 길이
     */
    public static final int GCM_IV_LENGTH = 12;
    /**
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">Cipher (Encryption) Algorithms</a>
     */
    public static final String TRANSFORMATION = "AES/GCM/NoPadding";
    /**
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#KeyGenerator">KeyGenerator Algorithms</a>
     */
    public static final String ALGORITHM = "AES";

    /**
     * AES256 암호화에 사용할 키를 생성한다.
     *
     * @return Base64 인코딩된 암호화 키
     */
    @SneakyThrows
    public String generateKey() {
        final KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(256);
        return Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
    }

    /**
     * 암호화를 수행한다.
     *
     * @param plainText 암호화할 문자열
     * @param key       Base64 인코딩된 암호화 키
     * @return 암호화되고 Base64 인코딩된 문자열
     */
    @SneakyThrows
    public String encrypt(String plainText, String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        byte[] iv = iv();
        final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        final SecretKey keySpec = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
        final GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] buffer = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, buffer, 0, iv.length);
        System.arraycopy(encrypted, 0, buffer, iv.length, encrypted.length);
        return Base64.getEncoder().encodeToString(buffer);
    }

    /**
     * 복호화를 수행한다.
     *
     * @param encodedText Base64 인코딩된 암호화된 값
     * @param key         Base64 인코딩된 암호화 키
     * @return 복호화된 문자열
     */
    @SneakyThrows
    public static String decrypt(String encodedText, String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        byte[] decoded = Base64.getDecoder().decode(encodedText);
        byte[] iv = Arrays.copyOfRange(decoded, 0, GCM_IV_LENGTH);
        byte[] encrypted = Arrays.copyOfRange(decoded, GCM_IV_LENGTH, decoded.length);
        final SecretKey keySpec = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        final GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted);
    }

    /**
     * IV(initialization vector)를 생성한다.
     *
     * @return 12바이트
     */
    private byte[] iv() {
        byte[] iv = new byte[GCM_IV_LENGTH];
        final SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }

}
