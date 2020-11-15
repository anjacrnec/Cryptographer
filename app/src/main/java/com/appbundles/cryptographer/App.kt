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

        tutorialFeatureUtil= Feature(
            this,
            "tutorial",
            "com.appbundles.tutorial.",
            null,
            arrayListOf("TutorialActivity")
        )

        exerciseFeatureUtil= Feature(
            this,
            "exercises",
            "com.appbundles.exercises.",
            arrayListOf("ExercisesFragment"),
            null)

        storageFeatureUtil= Feature(
            this,
            "exercises_storage",
            "com.appbundles.exercises_storage.",
            arrayListOf("SavedExercises"),
            null)

        allFeaturesUtil= arrayListOf(exerciseFeatureUtil, storageFeatureUtil)

    }


    companion object{
        private lateinit var context:Context
        private lateinit var splitInstallManager:SplitInstallManager
        private lateinit var allFeaturesUtil:List<Feature>
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
    }

}