package com.generic.service.crypto.impl;

import com.generic.service.crypto.CryptoService;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Service
@Log4j2
public class CryptoServiceImpl implements CryptoService {

    private static final String SHA_256 = "SHA-256";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    @Value("${generic.crypto.internal-key}")
    private String cryptoSecretKey;
    private Cipher encryptionCipher;
    private Cipher decryptionCipher;

    @PostConstruct
    private void configureCrypto() throws Exception {
        var key = cryptoSecretKey.getBytes(StandardCharsets.UTF_8);
        final var sha = MessageDigest.getInstance(SHA_256);
        key = sha.digest(key);
        key = Arrays.copyOf(key, 32);
        final var secretKey = new SecretKeySpec(key, ALGORITHM);
        encryptionCipher = Cipher.getInstance(TRANSFORMATION);
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        decryptionCipher = Cipher.getInstance(TRANSFORMATION);
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey);
        log.info("Crypto cipher configured");
    }

    @Override
    public String encrypt(String rawData) {
        try {
            return Base64.getEncoder().encodeToString(encryptionCipher.doFinal(rawData.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public String decrypt(String encrypted) {
        try {
            return new String(decryptionCipher.doFinal(Base64.getDecoder().decode(encrypted)));
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public String encrypt(String rawData, String keyData) {
        try {
            var key = keyData.getBytes(StandardCharsets.UTF_8);
            final var sha = MessageDigest.getInstance(SHA_256);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 32);
            final var secretKey = new SecretKeySpec(key, ALGORITHM);
            final var cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(rawData.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public String decrypt(String encrypted, String keyData) {
        try {
            var key = keyData.getBytes(StandardCharsets.UTF_8);
            final var sha = MessageDigest.getInstance(SHA_256);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 32);
            final var secretKey = new SecretKeySpec(key, ALGORITHM);
            final var cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)));
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public boolean matches(String rawData, String encrypted) {
        final var decryptedData = decrypt(encrypted);
        return decryptedData != null && decryptedData.equals(rawData);
    }
}
