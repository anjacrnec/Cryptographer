package com.appbundles.exercises

interface StorageInterface {

    fun saveExercise(exercise:Exercise){}
    interface Provider{
        fun get(): StorageInterface
    }
}