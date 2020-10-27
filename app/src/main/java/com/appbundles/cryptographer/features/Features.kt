package com.appbundles.cryptographer.features

class Features {

    class Tutorial{
        companion object{
            const val FEATURE_NAME="tutorial"
            const val FEATURE_DIRECTORY="com.appbundles.tutorial."
            const val ACTIVITY_TUTORIAL_NAME="TutorialActivity"
            const val ACTIVITY_TUTORIAL_DIRECTORY= FEATURE_DIRECTORY+ACTIVITY_TUTORIAL_NAME
            const val SKIP_TUTORIAL="skip"
        }
    }

    class Exercises{
        companion object{
            const val FEATURE_NAME="exercises"
            const val FEATURE_DIRECTORY="com.appbundles.exercises."
            const val FRAGMENT_EXERCISES_NAME="ExercisesFragment"
            const val FRAGMENT_EXERCISES_DIRECTORY= FEATURE_DIRECTORY+FRAGMENT_EXERCISES_NAME
        }
    }
}