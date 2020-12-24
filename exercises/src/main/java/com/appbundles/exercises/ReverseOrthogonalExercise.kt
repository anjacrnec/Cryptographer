package com.appbundles.exercises

import android.content.Context
import com.appbundles.cryptographer.ResUtil
import com.appbundles.cryptographer.R
import com.appbundles.cryptographer.cryptography.ReverseOrthogonalCipher
import rita.RiTa

class ReverseOrthogonalExercise:ExerciseImpl {

    override fun generate(context: Context): Exercise {
        var cipher=generateCipher()
        val exercise= Exercise(
            method = ResUtil.getString(context, R.string.trans_ortho_boustrophedon_cipher),
            type = generateType(),
            plainText = cipher.plainText,
            cipherText = cipher.cipherText,
            key = cipher.key
        )
        exercise.body=generateBody(context,exercise)
        return exercise
    }

    override fun generateCipher(): ReverseOrthogonalCipher {

        val pt= RiTa.randomWord()

        val keyMax= RiTa.random(2, 5)

        val keyArray: Array<Int> = RandomUtil.generateArray(keyMax)

        val key: String = RandomUtil.shuffleArray(keyArray)

        val cipher= ReverseOrthogonalCipher(pt, key)

        return cipher
    }
}