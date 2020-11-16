package com.appbundles.cryptographer.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appbundles.cryptographer.App
import com.appbundles.cryptographer.features.Session
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.google.android.play.core.tasks.OnFailureListener
import com.google.android.play.core.tasks.OnSuccessListener

class MainViewModel:ViewModel() {

    private val session = MutableLiveData<Session>()
    val sessionValue: LiveData<Session> = session

    private val status = MutableLiveData<Int>()
    val statusValue: LiveData<Int> = status


    fun initSplitListener() :SplitInstallStateUpdatedListener {
        var listener = SplitInstallStateUpdatedListener { state ->
            when (state.status()) {

                SplitInstallSessionStatus.PENDING -> {
                    status.postValue(SplitInstallSessionStatus.PENDING)
                }

                SplitInstallSessionStatus.DOWNLOADING -> {
                    status.postValue(SplitInstallSessionStatus.DOWNLOADING)
                }

                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {

                }
                SplitInstallSessionStatus.INSTALLED -> {
                    status.postValue(SplitInstallSessionStatus.INSTALLED)
                }

                SplitInstallSessionStatus.INSTALLING -> {
                    status.postValue(SplitInstallSessionStatus.INSTALLING)
                }

                SplitInstallSessionStatus.FAILED, SplitInstallSessionStatus.UNKNOWN -> {
                    status.postValue(SplitInstallSessionStatus.FAILED)
                }
            }
        }
        return listener
    }

    fun install(features:List<String>){
        val requestFeatureBuilder = SplitInstallRequest.newBuilder()
        var type:String=""
        for(feature in features) {
            type=type+feature
            requestFeatureBuilder.addModule(feature)
        }
       val request= requestFeatureBuilder.build()
        App.getSplitInstallManager()
            .startInstall(request)
            .addOnSuccessListener(OnSuccessListener<Int> {
                session.postValue(Session(it, type,null))
             })
            .addOnFailureListener(OnFailureListener {

            })
    }

}