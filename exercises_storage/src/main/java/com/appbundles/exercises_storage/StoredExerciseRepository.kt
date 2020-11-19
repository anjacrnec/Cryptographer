package com.appbundles.exercises_storage

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutionException

class StoredExerciseRepository(private val storedExerciseDao: StoredExerciseDao) {

    fun insert(storedExercise: StoredExercise) {
      InsertWordAsyncTask(storedExerciseDao).execute(storedExercise)
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    fun getListWords(): List<StoredExercise>? {
        return GetListWordsASyncTask(storedExerciseDao).execute().get()
    }

    fun getLive(): LiveData<List<StoredExercise>> {
        return GetLiveListWordsASyncTask(storedExerciseDao).execute().get()
    }


    class GetLiveListWordsASyncTask(storedExerciseDao: StoredExerciseDao) :
        AsyncTask<Void?, Void?, LiveData<List<StoredExercise>>>() {
        private val storedExerciseDao=storedExerciseDao

        override fun doInBackground(vararg params: Void?): LiveData<List<StoredExercise>> {
            return  storedExerciseDao.getAllLiveStored()
        }

    }

    class GetListWordsASyncTask(storedExerciseDao: StoredExerciseDao) :
        AsyncTask<Void?, Void?, List<StoredExercise>>() {
        private val storedExerciseDao=storedExerciseDao

        override fun doInBackground(vararg params: Void?): List<StoredExercise> {
                return  storedExerciseDao.getAllStoredExercises()
        }

    }


    private class InsertWordAsyncTask(storedExerciseDao: StoredExerciseDao) :
        AsyncTask<StoredExercise?, Void?, Void?>() {
        private val storedExerciseDao: StoredExerciseDao = storedExerciseDao

        override fun doInBackground(vararg params: StoredExercise?): Void? {
            storedExerciseDao.insert(params.get(0)!!)
            return null
        }
    }
}