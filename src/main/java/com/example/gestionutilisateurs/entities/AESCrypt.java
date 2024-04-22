package com.example.gestionutilisateurs.entities;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESCrypt {

    private static final String ALGORITHM = "AES";
    private static final String MODE = "ECB"; // ECB mode is used for simplicity, not recommended for most purposes
    private static final String PADDING = "PKCS5Padding"; // Padding scheme
    private static final String TRANSFORMATION = ALGORITHM + "/" + MODE + "/" + PADDING;



    // Encrypt plaintext using AES with the given key
    public static String encrypt(String plaintext, String password) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt ciphertext using AES with the given key
    public static String decrypt(String ciphertext, String password) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(password.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

}