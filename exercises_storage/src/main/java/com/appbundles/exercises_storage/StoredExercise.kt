package com.appbundles.exercises_storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "table_stored_exercises")
class StoredExercise (
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "body") val body: String?,
    @ColumnInfo(name = "answer") val answer: String?,
    ){
    @PrimaryKey(autoGenerate = true)
    var foodId: Int = 0

}