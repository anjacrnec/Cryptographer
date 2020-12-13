package com.appbundles.cryptographer.features


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.fragment.app.Fragment
import com.appbundles.cryptographer.App
import com.appbundles.cryptographer.BuildConfig
import com.appbundles.cryptographer.ResUtil
import java.lang.Exception

open class Feature(
    private val context: Context,
     private val featureName:String,
    private val featureDirectory:String,
    private val fragmentInfo:List<String>?,
    private val activityInfo:List<String>?,
    private val isBase:Boolean
):BaseFeatureInterface
{

    private lateinit var packageName:String
    private val resources=context.resources
    init {
        if(isBase)
            packageName=context.packageName
        else
            packageName=BuildConfig.APPLICATION_ID+"."+ featureName
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun getDrawable(name:String):Drawable?{
        return try {
            val drawable = resources.getDrawable(
                resources.getIdentifier(
                    name,
                    "drawable",
                    packageName
                )
            )
            return drawable
        }catch (e:Resources.NotFoundException){
            null
        }
    }

    override fun getString(name:String):String?{
        return try {
            val string = resources.getString(
                resources.getIdentifier(
                    name,
                    "string",
                    packageName
                )
            )
            string
        } catch (e:Resources.NotFoundException){
            null
        }
    }

    override fun createIntent(context:Context,tag:String):Intent?{
        var activityDirectory=""
        activityInfo?.let {
            for (i in activityInfo.indices)
                if (activityInfo[i] == tag) {
                    activityDirectory = featureDirectory + activityInfo[i]
                    try {
                        return Intent(context, Class.forName(activityDirectory))
                    } catch (e: ClassNotFoundException) {
                        return null
                    }
                }
        }
        return null
    }

    override fun createFragment(tag:String):Fragment?{
        var fragmentDirectory=""
        fragmentInfo?.let {
            for (i in fragmentInfo.indices)
                if (fragmentInfo[i] == tag) {
                    fragmentDirectory = featureDirectory + fragmentInfo[i]
                    try {
                        return Class.forName(fragmentDirectory).newInstance() as Fragment
                    } catch (e: ClassNotFoundException) {
                        return null
                    }
                }
        }

        return null
    }

    override fun isInstalled():Boolean{
        return App.getSplitInstallManager().installedModules.contains(featureName)
    }

    override fun getLocalFeatureName():String?{
        return ResUtil.getString(featureName)
    }

    override fun getFeatureName(): String {
        return featureName
    }


}