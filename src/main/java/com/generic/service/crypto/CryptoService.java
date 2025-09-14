package com.generic.service.crypto;

public interface CryptoService {
    String encrypt(String rawData);

    String decrypt(String encrypted);

    String encrypt(String rawData, String key);

    String decrypt(String encrypted, String key);

    boolean matches(String rawData, String encrypted);
}

