package com.appbundles.exercises

import android.content.Context
import com.appbundles.cryptographer.ResUtil
import com.appbundles.cryptographer.R
import com.appbundles.cryptographer.cryptography.OrthogonalCipher
import rita.RiTa

class OrthogonalExercise :ExerciseImpl {

    override fun generate(context: Context): Exercise {
        var cipher=generateCipher()
        val exercise= Exercise(
            method = ResUtil.getString(context, R.string.transpositional_orthogonal),
            type = generateType(),
            plainText = cipher.plainText,
            cipherText = cipher.cipherText,
            key = cipher.key
        )
        exercise.body=generateBody(context,exercise)
        return exercise
    }

    override fun generateCipher(): OrthogonalCipher {

        val pt= RiTa.randomWord()

        val keyMax= RiTa.random(2, 5)

        val keyArray: Array<Int> = RandomUtil.generateArray(keyMax)

        val key: String = RandomUtil.shuffleArray(keyArray)

        val cipher= OrthogonalCipher(pt, key)

        return cipher
    }

}