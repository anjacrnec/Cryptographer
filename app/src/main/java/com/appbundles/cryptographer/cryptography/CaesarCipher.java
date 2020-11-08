package com.appbundles.cryptographer.cryptography;




public  class CaesarCipher extends  SubstitiutionCipher{



    int key;

    public CaesarCipher(String plainText, int key)
    {
        this.plainText=plainText;
        this.key=key;
        this.cipherText=encrypt(this.plainText,this.key);
    }

    public String decrypt()
    {
        return  decrypt(this.cipherText,this.key);
    }

    public String encrypt()
    {
        return encrypt(this.plainText,this.key);
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    // ceaser cipher encription method
    public static String encrypt(String plainText, int key)
    {
        char [] pt=plainText.toLowerCase().toCharArray();
        String cipherText="";
        for(int i=0;i<pt.length;i++)
        {
            if(Character.isAlphabetic(pt[i])) {
                int ptIndex = charToIndex(pt[i]);
                int ctIndex = (ptIndex + key) % TOTAL;
                char ctChar = indexToChar(ctIndex);
                cipherText = cipherText + ctChar;
            }
            else
                cipherText = cipherText + pt[i];


        }
        return cipherText;
    }

    //caeser cipher decryption method
    public static String decrypt (String cipherText, int key)
    {
        char [] ct=cipherText.toLowerCase().toCharArray();
        String plainText="";
        for(int i=0;i<ct.length;i++)
        {
            if(Character.isAlphabetic(ct[i]))
            {
                int ctIndex=charToIndex(ct[i]);
                int ptIndex=(ctIndex-key)%TOTAL;
                while(ptIndex<0)
                    ptIndex=ptIndex+TOTAL;
                char ptChar=indexToChar(ptIndex);
                plainText=plainText+ptChar;
            }
            else
                plainText=plainText+ct[i];
        }
        return plainText;
    }

    //checks if key is valid
    public static boolean isKeyValid(int key)
    {
        if (key>=1 && key<=25)
            return  true;
        else
            return false;
    }


}
