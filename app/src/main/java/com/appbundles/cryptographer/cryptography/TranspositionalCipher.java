package com.appbundles.cryptographer.cryptography;

import java.util.Arrays;
import java.util.HashMap;

public  abstract class TranspositionalCipher extends Cipher {


    String key;

    public String getKey() {
        return key;
    }

    //each value from the kay is paired with its position in a HashMap -> encryption easier
    protected static HashMap<Integer, Integer> keyToHashMap(String key)
    {
        key=key.replaceAll(" ","");
        HashMap<Integer, Integer> keyMap=new HashMap<>();
        for(int i=0;i<key.length();i++)
            keyMap.put(Integer.parseInt(key.charAt(i)+""),i);

        return  keyMap;
    }

    ///convert the key from String to int Array
    protected static int [] keyToInt(String key)
    {

        key=key.replaceAll(" ","");
        int [] keyInt=new int[key.length()];
        for(int i=0;i<key.length();i++)
        {
            keyInt[i]= Integer.parseInt(key.charAt(i)+"");

        }
        return  keyInt;
    }

    //checks whether the key is valid
    /* a key needs to consist of values that if sorted
    will produce a series of incrementing values starting with 1*/
    public static boolean isKeyValid(String key)
    {
        int []keyInt=keyToInt(key);
        Arrays.sort(keyInt);

        if(keyInt.length==1)
            return false;

        if(keyInt[0]!=1)
            return  false;
        else
            {
            for (int i = 0; i < keyInt.length - 1; i++) {
                if (keyInt[i]+1!=keyInt[i+1])
                    return false;
            }

        }

        return true;
    }


    //generate the matrix dimension acccording to the plain text and key
    protected static int [] generateMatrixDimensions(String text, int[] key)
    {
        text=text.replaceAll(" ","");
        int textSize=text.length();
        int keySize=key.length;
        int [] s=new int [] {1,2};

        if(textSize<=keySize)
            return new int [] {1,keySize};
        else
        {
            int rows=1;
            for(int i=2;i<=textSize;i++)
            {
                if(keySize*i>=textSize)
                {
                    rows = i;
                    break;
                }

            }

            return  new int [] {rows,keySize};
        }

    }

    //encyption method for transposition cipher
    //all transpositional ciphers have the same encryption method regarless of type
    protected static String encrypt(char[][] matrix, HashMap<Integer, Integer> key)
    {
        String cipherText="";
        for(int i=0;i<key.size();i++)
        {
            int col=key.get(i+1);
           for(int j=0;j<matrix.length;j++)
           {
               cipherText=cipherText+matrix[j][col];
           }

        }

        return cipherText;
    }


    //each transpositional cipher has a different way of generating the matrix -> abstract
    // TO DO - CONSIDER REMOVING generateMatrix as abstract method and have each transpositional class with its own private generateMatrix method
    protected abstract char [][] generateMatrix(String text, String key);
}
