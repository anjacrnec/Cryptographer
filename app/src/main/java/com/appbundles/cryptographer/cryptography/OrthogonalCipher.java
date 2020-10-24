package com.appbundles.cryptographer.cryptography;

import java.util.HashMap;

public class OrthogonalCipher extends  TranspositionalCipher{





    public OrthogonalCipher()
    {

    }
    public OrthogonalCipher(String plainText, String key)
    {
        this.plainText=plainText;
        this.key=key;
        this.cipherText=encrypt(plainText,key);
    }


    //orthogonal transpositional encryption
    public static String encrypt (String plainText, String key)
    {
        OrthogonalCipher cipher=new OrthogonalCipher();

        char [][] matrix=cipher.generateMatrix(plainText,key);

        HashMap<Integer, Integer> keyMap=keyToHashMap(key);

        String cipherText=encrypt(matrix,keyMap);

        return cipherText;
    }


    @Override
    protected  char [][] generateMatrix(String text, String key) {

        text=text.replaceAll(" ","");
        int [] keyInt=keyToInt(key);
        int [] matrixDimensions=generateMatrixDimensions(text,keyInt);
        int rows=matrixDimensions[0];
        int colums=matrixDimensions[1];
        char[][] matrix=new char[rows][colums];

        int k=0;
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<colums;j++)
            {
                if(k<text.length())
                    matrix[i][j]=text.charAt(k);
                else
                    matrix[i][j]='x';
                k++;
            }
        }

        return matrix;
    }
}
