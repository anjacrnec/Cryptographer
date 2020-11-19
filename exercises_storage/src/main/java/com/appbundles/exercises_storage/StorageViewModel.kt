package com.appbundles.exercises_storage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appbundles.cryptographer.App

class StorageViewModel:ViewModel() {

    private val exercises = MutableLiveData<List<StoredExercise>>()
    val exercisesValue: LiveData<List<StoredExercise>> = exercises

    fun getStoredExercises():LiveData<List<StoredExercise>>{
        val rep=StoredExerciseRepository( (StoredExerciseDatabase.getDatabaseInstance(App.getApplicationContext()).storedExerciseDao()))
        return rep.getLive()
    }
}


