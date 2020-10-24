package com.appbundles.cryptographer.cryptography;

import java.util.Random;

//abstract class for Cipher
public abstract class Cipher {

    //all ciphers have plaintext and ciphertext of type String
    protected String plainText;
    protected String cipherText;


    protected String title;

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getCipherText() {
        return cipherText;
    }

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }




    public static int generateRandomNumber(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static int generateRandomNumber() {
        Random r = new Random();
        return r.nextInt();
    }
}
