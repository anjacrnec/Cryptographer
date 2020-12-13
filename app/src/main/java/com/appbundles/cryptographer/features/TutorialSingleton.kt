package com.appbundles.cryptographer.features

import android.content.Context
import android.content.Intent
import com.appbundles.cryptographer.App

class TutorialSingleton (feature:Feature):BaseFeatureSingleton(feature){

    companion object{

        private var feature:TutorialSingleton?=null
        const val ACTIVITY_TUTORIAL_NAME="TutorialActivity"

        fun getInstance():TutorialSingleton{
            if(feature==null)
                feature= TutorialSingleton(
                    Feature(
                        App.getApplicationContext(),
                        "tutorial",
                        "com.appbundles.tutorial.",
                        null,
                        arrayListOf(ACTIVITY_TUTORIAL_NAME),
                        false
                    )
                )
            return feature!!
        }
    }

    fun createTutorialIntent(context: Context): Intent {
       return feature.createIntent(context, ACTIVITY_TUTORIAL_NAME)!!
    }

}