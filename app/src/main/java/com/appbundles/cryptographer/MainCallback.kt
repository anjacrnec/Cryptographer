package com.appbundles.cryptographer

import com.appbundles.cryptographer.features.Session


interface MainCallback {

    fun showDialog()

    fun getCurrentSession(): Session
}