package com.appbundles.exercises

import android.content.Context
import com.appbundles.cryptographer.ResUtil
import com.appbundles.cryptographer.R
import com.appbundles.cryptographer.cryptography.AffineCipher
import rita.RiTa

class AffineExercise:ExerciseImpl {

    override fun generate(context: Context): Exercise {
        var cipher=generateCipher()
        val exercise= Exercise(
            method = ResUtil.getString(context, R.string.affine_cipher),
            type = generateType(),
            plainText = cipher.plainText,
            cipherText = cipher.cipherText,
            key = "A="+cipher.keyA+" B="+cipher.keyB
        )
        exercise.body=generateBody(context,exercise)
        return exercise
    }

    override fun generateCipher(): AffineCipher {

        val pt= RiTa.randomWord()

        var keyA= RiTa.random(1, 100)

        while (!AffineCipher.isKeyAValid(keyA)) {
            keyA = RiTa.random(1,100)
        }

        val keyB= RiTa.random(1, 100)

        val cipher= AffineCipher(pt,keyA,keyB)

        return cipher
    }
}