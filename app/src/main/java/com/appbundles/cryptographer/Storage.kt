package com.appbundles.cryptographer

import android.content.Context
import android.content.SharedPreferences
import com.appbundles.cryptographer.features.Session
import com.google.gson.Gson


class Storage {

    companion object{
        private const val STORAGE_NAME="storage"
        private const val SKIP_TUTORIAL="skip tutorial"
        private const val UNINSTALL_TUTORIAL="uninstall tutorial"
        private const val EXERCISES_STATUS="status"
        private const val SESSION="session"

        private fun getStorage(context: Context):SharedPreferences{
            return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
        }

        fun setUninstallTutorial(context: Context, uninstall: Boolean){
            val sp= getStorage(context)
            sp.edit().putBoolean(UNINSTALL_TUTORIAL, uninstall).apply()
        }

        fun getUninstallTutorial(context: Context):Boolean{
            val sp= getStorage(context)
            return sp.getBoolean(UNINSTALL_TUTORIAL, false)
        }

        fun setSkipTutorial(context: Context, skip: Boolean){
            val sp= getStorage(context)
            sp.edit().putBoolean(SKIP_TUTORIAL, skip).apply()
        }

         fun getSkipTutorial(context: Context):Boolean{
            val sp= getStorage(context)
            return sp.getBoolean(SKIP_TUTORIAL, false)
         }

        fun setExercisesStatus(context: Context, status: Int){
            val sp= getStorage(context)
            sp.edit().putInt(EXERCISES_STATUS, status).apply()
        }

        fun getExercisesStatus(context: Context):Int{
            val sp= getStorage(context)
            return sp.getInt(EXERCISES_STATUS, 0)
        }

        fun setCurrentExerciseSessions(context: Context, sessions: Session){
            val sp= getStorage(context)
            val json= Gson().toJson(sessions)
            sp.edit().putString(SESSION, json).apply()
        }

        fun getCurrentExerciseSessions(context: Context):Session?{
            val sp= getStorage(context)
            val json: String? = sp.getString(SESSION, null)
            return Gson().fromJson(json, Session::class.java)
        }

    }

}