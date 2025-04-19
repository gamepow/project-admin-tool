package com.example.projectmyfinances.security;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;

public class JasyptEncrypt {
    public static void main(String[] args) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword("0pmfbA3IZAgyKmirBfXaei9N"); // Replace with a strong password
        encryptor.setConfig(config);

        String secret = "your_secret_value";
        String encryptedSecret = encryptor.encrypt(secret);
        System.out.println("Encrypted Secret: " + encryptedSecret);
    }
}
