package com.appbundles.cryptographer

import android.content.Context

class LanguageUtil {

    companion object {
        fun getResString(context: Context, string: Int):String {
            return context.resources.getString(string)
        }
    }
}