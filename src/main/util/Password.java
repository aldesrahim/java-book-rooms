/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author aldes
 */
public class Password {
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    public static String hash(String input) {
        byte[] inputBytes = input.getBytes(UTF_8);

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : md.digest(inputBytes)) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static boolean verify(String input, String hashed) {
        return hash(input).equals(hashed);
    }
}
