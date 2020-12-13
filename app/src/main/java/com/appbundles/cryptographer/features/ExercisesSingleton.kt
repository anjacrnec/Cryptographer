package com.appbundles.cryptographer.features

import android.content.Context
import androidx.fragment.app.Fragment
import com.appbundles.cryptographer.App

class ExercisesSingleton(feature:Feature):BaseFeatureSingleton(feature){

    companion object{

        private var feature:ExercisesSingleton?=null

        const val FRAGMENT_EXERCISES_NAME="ExercisesFragment"

        fun getInstance():ExercisesSingleton{
            if(feature==null)
                feature= ExercisesSingleton(
                    Feature(
                        App.getApplicationContext(),
                        "exercises",
                        "com.appbundles.exercises.",
                        arrayListOf(FRAGMENT_EXERCISES_NAME),
                        null,
                        false)
                )
            return feature!!
        }
    }

    fun createExercisesFragment(): Fragment {
        return feature.createFragment(FRAGMENT_EXERCISES_NAME)!!
    }

}