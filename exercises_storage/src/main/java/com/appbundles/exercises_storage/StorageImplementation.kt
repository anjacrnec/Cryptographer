package com.appbundles.exercises_storage

import android.content.Context
import com.appbundles.cryptographer.App
import com.appbundles.cryptographer.StorageInterface

class StorageImplementation:StorageInterface {

    override fun insertExercise() {
        val rep=StoredExerciseRepository( (StoredExerciseDatabase.getDatabaseInstance(App.getApplicationContext()).storedExerciseDao()))
        rep.insert(StoredExercise("caeser","do stuff","this is answer"))
    }

    companion object Provider:StorageInterface.Provider{
        override fun get(): StorageInterface {
            return StorageImplementation()
        }

    }
}