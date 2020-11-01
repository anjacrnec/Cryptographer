package com.appbundles.cryptographer

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appbundles.cryptographer.features.Features
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.google.android.play.core.tasks.OnFailureListener
import com.google.android.play.core.tasks.OnSuccessListener

class MainViewModel:ViewModel() {

    private val status = MutableLiveData<Int>()
    val statusValue: LiveData<Int> = status

    fun initSplitListener(splitInstallManager: SplitInstallManager) :SplitInstallStateUpdatedListener {
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

                SplitInstallSessionStatus.FAILED -> {
                    status.postValue(SplitInstallSessionStatus.FAILED)
                }
            }
        }
        return listener
    }

    fun installExercises(splitInstallManager: SplitInstallManager){
        val requestFeature = SplitInstallRequest.newBuilder().addModule(Features.Exercises.FEATURE_NAME).build()
        splitInstallManager
            .startInstall(requestFeature)
            .addOnSuccessListener(OnSuccessListener<Int> {

            }
            )
            .addOnFailureListener(OnFailureListener {

            })
    }
}