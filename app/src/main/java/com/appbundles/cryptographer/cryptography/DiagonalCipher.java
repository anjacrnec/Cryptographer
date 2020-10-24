package com.appbundles.cryptographer.cryptography;

import android.util.Log;

import java.util.HashMap;

// TO DO - RESTRICT ENCRYPTION IF KEY IS <= PT
public class DiagonalCipher extends TranspositionalCipher {


    public DiagonalCipher()
    {

    }

    public DiagonalCipher(String plaintext, String key)
    {
        this.plainText=plaintext;
        this.key=key;
        this.cipherText=encrypt(this.plainText,this.key);
    }

    //diagonal transpositional encryption
    public static String encrypt(String plainText, String key)
    {
        DiagonalCipher cipher=new DiagonalCipher();

        char [][] matrix=cipher.generateMatrix(plainText,key);

        HashMap<Integer, Integer> keyMap=keyToHashMap(key);

        String cipherText=encrypt(matrix,keyMap);

        return cipherText;
    }


    @Override
    protected char[][] generateMatrix(String text, String key) {

        text = text.replaceAll(" ", "");
        int[] keyInt = keyToInt(key);
        int[] matrixDimensions = generateMatrixDimensions(text, keyInt);
        int rows = matrixDimensions[0];
        int colums = matrixDimensions[1];
        char[][] matrix = new char[rows][colums];


        for (int i = text.length(); i < rows * colums; i++)
            text = text + "x";

        int i = 0, j = 0, k = 0, r = 0, maxi = 0, maxj = 0, mini = 0, minj = 0;
        int starti=0;
        int startj=0;
        while (k < text.length()) {
            if (i == 0 && j == 0) {
                matrix[i][j] = text.charAt(k);
                k++;
                j++;
                maxi++;
                startj++;
            } else {

                while (i <= maxi && j >= minj && k<text.length()) {
                    Log.d("pr1",i+"");
                    Log.d("pr2",j+"");
                    matrix[i][j] = text.charAt(k);
                    i++;
                    j--;
                    k++;
                }

                r++;
                i--;
                j++;

                if(startj<colums-1)
                {
                    Log.d("ovde1","ovde1");
                    startj = startj + 1;
                    j=startj;
                    i=starti;

                }
                else
                {
                    Log.d("ovde2","ovde2");
                    j=startj;
                    starti=starti+1;
                    i=starti;
                }


                if (maxi != rows - 1)
                    maxi++;
                else {
                    mini--;
                }
            }


        }

        return  matrix;
    }

    public static boolean isKeyShorter(String text, String key)
    {
        if(text.length()>key.length())
            return true;
        else
            return false;
    }
}
