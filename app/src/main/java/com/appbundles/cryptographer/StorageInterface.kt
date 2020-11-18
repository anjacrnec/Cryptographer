package com.appbundles.cryptographer

interface StorageInterface {

    fun insertExercise()

    interface Provider{
        fun get():StorageInterface
    }
}