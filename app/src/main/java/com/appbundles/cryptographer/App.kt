package com.appbundles.cryptographer

import android.content.Context
import com.appbundles.cryptographer.features.Feature
import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory

class App:SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()

        context=this

        splitInstallManager= SplitInstallManagerFactory.create(context)

        mainModule=Feature(
            this,
            "app",
            "com.appbundles.app.",
            null,
            null,
            true
        )

        imageFeatureUtil=Feature(
            this,
            "images",
            "com.appbundles.images.",
            null,
            null,
            false
        )

        tutorialFeatureUtil= Feature(
            this,
            "tutorial",
            "com.appbundles.tutorial.",
            null,
            arrayListOf("TutorialActivity"),
        false
        )

        exerciseFeatureUtil= Feature(
            this,
            "exercises",
            "com.appbundles.exercises.",
            arrayListOf("ExercisesFragment"),
            null,
        false)

        storageFeatureUtil= Feature(
            this,
            "exercises_storage",
            "com.appbundles.exercises_storage.",
            arrayListOf("SavedExercises"),
            null,
        false)

        allFeaturesUtil= arrayListOf(mainModule, exerciseFeatureUtil, storageFeatureUtil, tutorialFeatureUtil)

    }


    companion object{
        private lateinit var context:Context
        private lateinit var splitInstallManager:SplitInstallManager
        private lateinit var allFeaturesUtil:List<Feature>
        private lateinit var mainModule:Feature
        private lateinit var imageFeatureUtil:Feature
        private lateinit var tutorialFeatureUtil:Feature
        private lateinit var exerciseFeatureUtil:Feature
        private lateinit var storageFeatureUtil:Feature

        fun getSplitInstallManager():SplitInstallManager{
            return splitInstallManager
        }

        fun getApplicationContext():Context{
            return context
        }
        fun getAllFeaturesUtil():List<Feature>{
            return allFeaturesUtil
        }

        fun getExerciseFeatureUtil():Feature{
            return exerciseFeatureUtil
        }

        fun getStorageFeatureUtil():Feature{
            return storageFeatureUtil
        }

        fun getTutorialFeatureUtil():Feature{
            return tutorialFeatureUtil
        }

        fun getImageFeatureutil():Feature{
            return imageFeatureUtil
        }
    }

}