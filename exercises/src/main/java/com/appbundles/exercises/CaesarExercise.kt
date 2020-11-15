package com.appbundles.exercises

import android.content.Context
import com.appbundles.cryptographer.ResUtil
import com.appbundles.cryptographer.cryptography.CaesarCipher
import com.appbundles.cryptographer.R
import rita.RiTa


class CaesarExercise() : ExerciseImpl() {

    override fun generate(context: Context):Exercise {
        var cipher=generateCipher()
        val exercise= Exercise(
            method = ResUtil.getString(context,R.string.caeser_encryption),
            type = generateType(),
            plainText = cipher.plainText,
            cipherText = cipher.cipherText,
            key = cipher.key.toString(),
        )
        exercise.body=generateBody(context,exercise)
        return exercise
    }

    override fun generateCipher(): CaesarCipher {
        val pt=RiTa.randomWord()
        val key=RiTa.random(1, 25)
        val cipher=
            CaesarCipher(pt, key)
        return cipher
    }

}