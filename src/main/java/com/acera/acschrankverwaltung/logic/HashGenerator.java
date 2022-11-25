package com.acera.acschrankverwaltung.logic;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {

    //region Methoden

    /**
     * Methode, welche ein String in ein byte Array umwandelt und zurückgibt.
     * Statische Methode getInstance wird aufgerufen mit hashing SHA
     * digest() Methode wird aufgerufen und der Input wird in ein byte Array umgewandelt.
     *
     * @param input : {@link String} : Input, der gehasht wird.
     * @return byte[] : {@link Byte[]} : Byte Array
     * @throws NoSuchAlgorithmException
     */
    public byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        return messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Methode, welche ein Byte Array in ein gehashtes String umwandelt und zurückgibt.
     *
     * @param hash : {@link Byte[]} : Byte Array
     * @return {@link String} hashed Passwort.
     */
    public String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
    //endregion
}
