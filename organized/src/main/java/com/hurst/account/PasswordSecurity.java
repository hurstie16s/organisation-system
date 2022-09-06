package com.hurst.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public abstract class PasswordSecurity {

    private static final Logger logger = LogManager.getLogger(PasswordSecurity.class);

    public static String securePassword(String password) {
        return generateStrongPasswordHash(password);
    }

    public static boolean validatePassword (String passwordToValidate, String storedSecurePassword) {

        String[] parts = storedSecurePassword.split(":");
        int iterations = Integer.parseInt(parts[0]);

        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(passwordToValidate.toCharArray(),
                salt, iterations, hash.length * 8);
        byte[] testHash = new byte[0];
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            testHash = skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            System.exit(7);
        } catch (InvalidKeySpecException e) {
            logger.error(e.getMessage());
            System.exit(8);
        }

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private static String generateStrongPasswordHash(String password) {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = new byte[16];
        byte[] hash = new byte[0];
        try {
            salt = getSalt();

            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            hash = skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            System.exit(7);
        } catch (InvalidKeySpecException e) {
            logger.error(e.getMessage());
            System.exit(8);
        }

        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0) {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        } else{
            return hex;
        }
    }

    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
