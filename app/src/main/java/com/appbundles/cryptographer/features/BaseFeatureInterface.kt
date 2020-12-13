package com.appbundles.cryptographer.features

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment

interface BaseFeatureInterface {

    fun getString(name:String):String?

    fun getDrawable(name:String):Drawable?

    fun isInstalled():Boolean

    fun createFragment(tag:String): Fragment?

    fun createIntent(context: Context,tag:String): Intent?

    fun getLocalFeatureName():String?

    fun getFeatureName():String
}