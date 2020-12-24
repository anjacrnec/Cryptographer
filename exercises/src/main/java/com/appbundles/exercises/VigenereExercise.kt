package com.appbundles.exercises

import android.content.Context
import com.appbundles.cryptographer.ResUtil
import com.appbundles.cryptographer.R
import com.appbundles.cryptographer.cryptography.VigenereCiphere
import rita.RiTa

class VigenereExercise:ExerciseImpl {

    override fun generate(context: Context): Exercise {
        var cipher=generateCipher()
        val exercise= Exercise(
            method = ResUtil.getString(context, R.string.vignere_cipher),
            type = generateType(),
            plainText = cipher.plainText,
            cipherText = cipher.cipherText,
            key = cipher.key
        )
        exercise.body=generateBody(context,exercise)
        return exercise
    }

    override fun generateCipher(): VigenereCiphere {

        val pt= RiTa.randomWord()

        val key=RiTa.randomWord()

        val cipher=VigenereCiphere(pt,key)

        return cipher
    }
}