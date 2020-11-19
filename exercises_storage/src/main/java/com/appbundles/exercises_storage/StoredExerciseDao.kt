package com.appbundles.exercises_storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoredExerciseDao {

    @Query("SELECT * FROM table_stored_exercises")
    fun getAllStoredExercises(): List<StoredExercise>

    @Query("SELECT * FROM table_stored_exercises")
    fun getAllLiveStored(): LiveData<List<StoredExercise>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(exercise: StoredExercise)

}