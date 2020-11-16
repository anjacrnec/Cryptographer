package com.appbundles.cryptographer.main

import com.appbundles.cryptographer.features.Session


interface MainCallback {

    fun showDialog()

    fun getCurrentSession(): Session
}