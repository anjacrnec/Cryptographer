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
}