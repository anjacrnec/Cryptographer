package com.appbundles.cryptographer

import android.content.Context
import android.content.SharedPreferences


class Storage {

    companion object{
        private const val STORAGE_NAME="storage"
        private const val SKIP_TUTORIAL="skip tutorial"
        private const val UNINSTALL_TUTORIAL="uninstall tutorial"


        private fun getStorage():SharedPreferences{
            return App.getApplicationContext().getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
        }

        fun setUninstallTutorial(uninstall: Boolean){
            val sp= getStorage()
            sp.edit().putBoolean(UNINSTALL_TUTORIAL, uninstall).apply()
        }

        fun getUninstallTutorial():Boolean{
            val sp= getStorage()
            return sp.getBoolean(UNINSTALL_TUTORIAL, false)
        }

        fun setSkipTutorial(skip: Boolean){
            val sp= getStorage()
            sp.edit().putBoolean(SKIP_TUTORIAL, skip).apply()
        }

         fun getSkipTutorial():Boolean{
            val sp= getStorage()
            return sp.getBoolean(SKIP_TUTORIAL, false)
         }

    }

}