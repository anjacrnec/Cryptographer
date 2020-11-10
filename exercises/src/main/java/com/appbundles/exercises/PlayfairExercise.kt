package com.appbundles.exercises

import android.content.Context
import com.appbundles.cryptographer.LanguageUtil
import com.appbundles.cryptographer.R
import com.appbundles.cryptographer.cryptography.Cipher
import com.appbundles.cryptographer.cryptography.PlayfairCipher
import com.appbundles.cryptographer.cryptography.VigenereCiphere
import rita.RiTa

class PlayfairExercise:ExerciseImpl() {


    override fun generate(context: Context): Exercise {
        var cipher=generateCipher()
        val exercise= Exercise(
            method = LanguageUtil.getResString(context, R.string.playfair_cipher),
            type = generateType(),
            plainText = cipher.plainText,
            cipherText = cipher.cipherText,
            key = cipher.key
        )
        exercise.body=generateBody(context,exercise)
        return exercise
    }

    override fun generateCipher(): PlayfairCipher {

        val pt= RiTa.randomWord()

        val key= RiTa.randomWord()

        val cipher= PlayfairCipher(pt,key)

        return cipher
    }


}