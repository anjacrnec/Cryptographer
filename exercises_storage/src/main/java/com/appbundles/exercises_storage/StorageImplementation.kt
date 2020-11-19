package com.appbundles.exercises_storage

import com.appbundles.cryptographer.App
import com.appbundles.exercises.Exercise
import com.appbundles.exercises.StorageInterface

class StorageImplementation: StorageInterface {


    override fun saveExercise(exercise:Exercise) {
        val repository=StoredExerciseRepository((StoredExerciseDatabase.getDatabaseInstance(App.getApplicationContext()).storedExerciseDao()))
        val savedExercise=StoredExercise(exercise.method,exercise.body,exercise.answer)
        repository.insert(savedExercise)
    }


    companion object Provider: StorageInterface.Provider{
        override fun get(): StorageInterface {
            return StorageImplementation()
        }

    }
}