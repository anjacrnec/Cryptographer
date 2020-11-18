package com.appbundles.exercises_storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoredExerciseDao {

    @Query("SELECT * FROM table_stored_exercises")
    fun getAllStoredExercises(): List<StoredExercise>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(exercise: StoredExercise)

}