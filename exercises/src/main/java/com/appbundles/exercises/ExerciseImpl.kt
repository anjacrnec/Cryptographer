package com.appbundles.exercises

import android.content.Context
import com.appbundles.cryptographer.cryptography.Cipher
import com.appbundles.exercises.Exercise.Companion.ENCRYPT
import com.appbundles.exercises.Exercise.Companion.EXERCISES_TYPES
import rita.RiTa

abstract class ExerciseImpl {


    abstract fun generate(context: Context):Exercise

    abstract fun generateCipher():Cipher

    fun generateType():String {
       return  RiTa.randomItem(EXERCISES_TYPES) as String
    }

    fun generateBody(context:Context, exercise: Exercise):String{
        val res=context.resources
        var body=""
        if (exercise.type === ENCRYPT) {
            body = res.getString(
                R.string.exercise_body,
                exercise.method,
                res.getString(R.string.ek),
                res.getString(R.string.pt),
                exercise.plainText,
                exercise.key
            )
        } else {
            body = res.getString(
                R.string.exercise_body,
                exercise.method,
                res.getString(R.string.dk),
                res.getString(R.string.ct),
                exercise.cipherText,
                exercise.key
            )
        }
        return body
    }

}


