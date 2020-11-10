package com.appbundles.exercises

import android.content.Context
import rita.RiTa


class Exercise(
     var method:String,
     var type:String,
     var plainText:String,
     var cipherText:String,
     var key:String) {

     lateinit var body: String
      var answer:String

     init{
             if(type=="encrypt")
                 answer=cipherText
             else
                 answer=plainText
     }

     companion object{
         const val CAESAR="caeser"
         const val AFFINE="affine"
         const val VIGENERE="vigenere"
         const val PLAYFAIR="playfair"
         const val ORTHO="ortho"
         const val ORTHO_REVERSE="ortho reverse"
         const val DIAGONAL="diagonal"
         const  val ENCRYPT = "encrypt"
         const val DECRYPT = "decrypt"
          val EXERCISES_TYPES = arrayOf(ENCRYPT, DECRYPT)


        fun generateExercise(method: String,context: Context):Exercise{
            return when(method){
                CAESAR -> CaesarExercise().generate(context)
                AFFINE -> AffineExercise().generate(context)
                VIGENERE-> VigenereExercise().generate(context)
                PLAYFAIR-> PlayfairExercise().generate(context)
                ORTHO-> OrthogonalExercise().generate(context)
                ORTHO_REVERSE-> ReverseOrthogonalExercise().generate(context)
                DIAGONAL-> DiagonalExercise().generate(context)
                else-> CaesarExercise().generate(context)
            }
         }
     }
}