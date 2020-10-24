package com.appbundles.cryptographer

import android.os.Bundle
import android.util.Log
import com.appbundles.cryptographer.features.Features
import com.example.bundles.BaseSplitActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory

class MainActivity : BaseSplitActivity() {

    private lateinit var splitInstallManager:SplitInstallManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splitInstallManager=SplitInstallManagerFactory.create(applicationContext)
        checkTutorialStatus()

        Log.e("BUNDLES","skip login"+Storage.getSkipTutorial(applicationContext).toString())
        Log.e("BUNDLES","uninstall"+Storage.getUninstallTutorial(applicationContext).toString())

    }

    private fun checkTutorialStatus(){
        if(splitInstallManager.installedModules.contains(Features.Tutorial.FEATURE_NAME)
            && Storage.getUninstallTutorial(applicationContext))
            splitInstallManager.deferredUninstall(arrayListOf(Features.Tutorial.FEATURE_NAME))
    }
}