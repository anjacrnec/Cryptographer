package com.appbundles.cryptographer.cryptography;

public abstract class SubstitiutionCipher extends Cipher{

    protected static final char[] ALPHABET = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    protected   static final int TOTAL=26;

    //finds the index of a character according to its position in the alphabet
    protected static int charToIndex(char c)
    {
        Boolean findChar=false;
        int posTemp=0;
        int index=-1;
        while(!findChar & posTemp<26)
        {
            if(c==ALPHABET[posTemp]) {
                findChar = true;
                index=posTemp;
            }
            posTemp++;
        }
        return index;
    }

    //finds the character of an index -number- according to the alphabet
    protected static char indexToChar(int index)
    {
        return ALPHABET[index];
    }


}
