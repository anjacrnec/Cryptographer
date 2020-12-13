package com.appbundles.cryptographer

import android.content.Context
import com.appbundles.cryptographer.features.*
import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory

class App:SplitCompatApplication() {

    override fun onCreate() {
        super.onCreate()

        context=this

        splitInstallManager= SplitInstallManagerFactory.create(context)


        allFeaturesUtil= arrayListOf(
            TutorialSingleton.getInstance(),
            ExercisesSingleton.getInstance(),
            StorageSingleton.getInstance()
        )

    }


    companion object{
        private lateinit var context:Context
        private lateinit var splitInstallManager:SplitInstallManager
        private lateinit var allFeaturesUtil:List<BaseFeatureSingleton>

        fun getSplitInstallManager():SplitInstallManager{
            return splitInstallManager
        }

        fun getApplicationContext():Context{
            return context
        }
        fun getAllFeaturesUtil():List<BaseFeatureSingleton>{
            return allFeaturesUtil
        }


    }

}