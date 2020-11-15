package com.appbundles.cryptographer

import android.content.Context
import android.graphics.drawable.Drawable

class ResUtil {

    companion object {

        fun getString(context: Context, string: Int):String {
            return context.resources.getString(string)
        }

        fun getDrawable(stringName: String):Drawable?{
            val features=App.getAllFeaturesUtil()
            for(feature in features) {
                val drawable=feature.getDrawable(stringName)
                if (drawable!=null)
                    return drawable
            }
            return null
        }

        fun getString(stringName:String):String?{
            val features=App.getAllFeaturesUtil()
            for(feature in features) {
                val string=feature.getString(stringName)
                if (!string.isNullOrEmpty())
                    return string
            }
            return null
        }

    }
}