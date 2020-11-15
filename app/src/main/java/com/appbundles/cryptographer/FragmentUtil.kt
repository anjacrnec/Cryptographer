package com.appbundles.cryptographer

import androidx.fragment.app.Fragment

class FragmentUtil{

    companion object {
        const val FRAGMENT_EXERCISES_NAME = "ExercisesFragment"
        const val FRAGMENT_SAVED_EXERCISES_NAME = "SavedExercises"


        fun ExercisesFragment():Fragment?{
            return App.getExerciseFeatureUtil().createFragment(FRAGMENT_EXERCISES_NAME)
        }

        fun StorageFragment():Fragment?{
            return App.getStorageFeatureUtil().createFragment(FRAGMENT_SAVED_EXERCISES_NAME)
        }
    }
}