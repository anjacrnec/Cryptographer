package com.appbundles.exercises

import android.content.Context
import com.appbundles.cryptographer.ResUtil
import com.appbundles.cryptographer.R
import com.appbundles.cryptographer.cryptography.DiagonalCipher
import rita.RiTa

class DiagonalExercise:ExerciseImpl {

    override fun generate(context: Context): Exercise {
        var cipher=generateCipher()
        val exercise= Exercise(
            method = ResUtil.getString(context, R.string.trans_diagonal_cipher),
            type = generateType(),
            plainText = cipher.plainText,
            cipherText = cipher.cipherText,
            key = cipher.key
        )
        exercise.body=generateBody(context,exercise)
        return exercise
    }

    override fun generateCipher(): DiagonalCipher {

        val pt= RiTa.randomWord()
        var keyMax:Int

        if (pt.length - 1 < 10)
            keyMax = pt.length - 1
        else
            keyMax = 9

        val keyArray: Array<Int> = RandomUtil.generateArray(keyMax)

        val key: String? = RandomUtil.shuffleArray(keyArray)

        val cipher= DiagonalCipher(pt, key)

        return cipher
    }
}