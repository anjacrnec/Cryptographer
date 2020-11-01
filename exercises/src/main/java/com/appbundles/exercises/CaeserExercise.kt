package com.appbundles.exercises

import android.content.Context
import com.appbundles.cryptographer.LanguageUtil
import com.appbundles.cryptographer.cryptography.CaeserCipher
import com.appbundles.cryptographer.R
import rita.RiTa


class CaeserExercise(){

    companion object {

        fun generate(plainText: String?, context: Context): Exercise {
            var key=RiTa.random(1, 25)
            var cipher=CaeserCipher(plainText, key)
            var type:String= RiTa.randomItem(Exercise.EXERCISES_TYPES) as String

            return Exercise(
                method = LanguageUtil.getResString(context,R.string.caeser_encryption),
                type = type,
                plainText = cipher.plainText,
                cipherText = cipher.cipherText,
                key = cipher.key.toString(),
                "",
               ""
            )
        }
    }



}