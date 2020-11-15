package com.appbundles.cryptographer

import android.content.Context
import android.content.Intent

class ActivityUtil {

    companion object {
        const val ACTIVITY_TUTORIAL_NAME = "TutorialActivity"

        fun TutorialActivity(context: Context): Intent? {
            return App.getTutorialFeatureUtil().createActivity(context, ACTIVITY_TUTORIAL_NAME)
        }
    }
}