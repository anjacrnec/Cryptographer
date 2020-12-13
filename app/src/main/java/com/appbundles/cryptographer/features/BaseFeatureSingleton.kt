package com.appbundles.cryptographer.features

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment

abstract class BaseFeatureSingleton(val feature:Feature):BaseFeatureInterface {

    override fun getString(name: String):String? {
        return feature.getString(name)
    }

    override fun getDrawable(name: String): Drawable? {
        return feature.getDrawable(name)
    }

    override fun isInstalled(): Boolean {
       return feature.isInstalled()
    }

    override fun createFragment(tag: String): Fragment? {
        return feature.createFragment(tag)
    }

    override fun createIntent(context: Context, tag: String): Intent? {
        return feature.createIntent(context,tag)
    }

    override fun getLocalFeatureName(): String? {
        return feature.getLocalFeatureName()
    }

    override fun getFeatureName(): String {
        return feature.getFeatureName()
    }

}