package com.appbundles.cryptographer.features

import android.content.Context
import androidx.fragment.app.Fragment
import com.appbundles.cryptographer.App

open class StorageSingleton(feature:Feature):BaseFeatureSingleton(feature){

    companion object{

        private var feature:StorageSingleton?=null
        const val FRAGMENT_STORAGE_NAME="SavedExercises"
        const val STRING_STORAGE="storage"
        const val ICON_STORAGE="ic_storage"

        fun getInstance():StorageSingleton{
            if(feature==null)
                feature= StorageSingleton(Feature(
                App.getApplicationContext(),
                "exercises_storage",
                "com.appbundles.exercises_storage.",
                arrayListOf(FRAGMENT_STORAGE_NAME),
                null,
                false)
                )
            return feature!!
        }
    }

    fun createExercisesStorageFragment(): Fragment {
        return feature.createFragment(FRAGMENT_STORAGE_NAME)!!
    }

    fun getProvider():Any?{
        return Class.forName("com.appbundles.exercises_storage.StorageImplementation\$Provider").kotlin.objectInstance
    }

}