package com.appbundles.cryptographer

import android.content.Context
import android.content.SharedPreferences

class Storage {

    companion object{
        private const val STORAGE_NAME="storage"
        private const val SKIP_TUTORIAL="skip tutorial"
        private const val UNINSTALL_TUTORIAL="uninstall tutorial"

        private fun getStorage(context: Context):SharedPreferences{
            return context.getSharedPreferences(STORAGE_NAME,Context.MODE_PRIVATE)
        }

        fun setUninstallTutorial(context: Context,uninstall:Boolean){
            val sp= getStorage(context)
            sp.edit().putBoolean(UNINSTALL_TUTORIAL,uninstall).apply()
        }

        fun getUninstallTutorial(context: Context):Boolean{
            val sp= getStorage(context)
            return sp.getBoolean(UNINSTALL_TUTORIAL,false)
        }

        fun setSkipTutorial(context: Context,skip:Boolean){
            val sp= getStorage(context)
            sp.edit().putBoolean(SKIP_TUTORIAL, skip).apply()
        }

         fun getSkipTutorial(context: Context):Boolean{
            val sp= getStorage(context)
            return sp.getBoolean(SKIP_TUTORIAL,false)
         }

    }

}