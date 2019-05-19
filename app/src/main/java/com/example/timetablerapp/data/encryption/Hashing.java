package com.example.timetablerapp.data.encryption;

import com.example.timetablerapp.MainApplication;
import com.example.timetablerapp.data.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 19/05/19 -bernard
 */
public class Hashing {
    public static String createHash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.reset();

        byte[] salt = MainApplication.getSharedPreferences().getString(Constants.SALT, "").getBytes();

//        digest.update(salt);

        byte[] hash = digest.digest(password.getBytes());
        return byteToStringHex(hash);
    }

    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static String byteToStringHex(byte[] hash) {
        char[] hexChar = new char[hash.length * 2];
        for (int i = 0; i < hash.length; i++) {
            int v = hash[i] & 0xFF;
            hexChar[i * 2] = hexArray[v >>> 4];
            hexChar[i * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChar);
    }
}
