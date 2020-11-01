package com.appbundles.exercises

import android.content.Context
import com.appbundles.cryptographer.cryptography.Cipher
import rita.RiTa

class Exercise(
    var method:String,
    var type:String,
    var plainText:String,
    var cipherText:String,
    var key:String,
    var body:String,
    var answer:String) {



    companion object{
        const val ENCRYPT="encrypt"
        const val DECRYPT="decrypt"
        val EXERCISES_TYPES= arrayOf(ENCRYPT, DECRYPT)
    }

     private fun generateAnswer():String{
        if(type== ENCRYPT)
            return cipherText
        else
            return plainText
    }

    private fun generateBody(context: Context):String{
        val res=context.resources
        if (type === ENCRYPT) {
            body = res.getString(
                R.string.exercise_body,
                method,
                res.getString(R.string.ek),
                res.getString(R.string.pt),
                plainText,
                key
            )
        } else {
            body = res.getString(
                R.string.exercise_body,
                method,
                res.getString(R.string.dk),
                res.getString(R.string.ct),
                cipherText,
                key
            )
        }
        return body.toString()
    }
}