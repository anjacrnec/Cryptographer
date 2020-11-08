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

         fun generateMethod(
             caesarExercise:Boolean,
             affineExercise:Boolean,
             vigenereExercise:Boolean,
             playfairExercise:Boolean,
             orthogonalExercise:Boolean,
             reverseOrthoExercise:Boolean,
             diagonalExercise:Boolean
         ):String{
             var arrayEnabledExercises:ArrayList<String> = ArrayList()

             if(caesarExercise)
                 arrayEnabledExercises.add(Exercise.CAESAR)
             if(affineExercise)
                 arrayEnabledExercises.add(Exercise.AFFINE)
             if(vigenereExercise)
                 arrayEnabledExercises.add(Exercise.VIGENERE)
             if(playfairExercise)
                 arrayEnabledExercises.add(Exercise.PLAYFAIR)
             if(orthogonalExercise)
                 arrayEnabledExercises.add(Exercise.ORTHO)
             if(reverseOrthoExercise)
                 arrayEnabledExercises.add(Exercise.ORTHO_REVERSE)
             if(diagonalExercise)
                 arrayEnabledExercises.add(Exercise.DIAGONAL)

             val method= RiTa.randomItem(arrayEnabledExercises) as String

             return method
         }

        /* fun generateExercise(method: String,context: Context):Exercise{
             when(method){
                 CAESAR ->   return CaesarExercise().generate(context)
                 AFFINE -> return AffineExercise().generate(context)
                 VIGENERE-> return VigenereExercise().generate(context)
             }

         }*/
     }
}