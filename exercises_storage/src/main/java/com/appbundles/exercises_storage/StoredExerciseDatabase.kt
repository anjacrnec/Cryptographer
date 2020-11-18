package com.appbundles.exercises_storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(StoredExercise::class), version = 1, exportSchema = false)
abstract class StoredExerciseDatabase:RoomDatabase() {

    abstract fun storedExerciseDao(): StoredExerciseDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var instance: StoredExerciseDatabase? = null

        fun getDatabaseInstance(context: Context): StoredExerciseDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoredExerciseDatabase::class.java,
                    "stored_exercise_database"
                ).build()
                this.instance = instance
                instance
            }
        }
    }
}