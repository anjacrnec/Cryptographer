package com.appbundles.cryptographer

import CryptographerFragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.appbundles.cryptographer.features.Features
import com.example.bundles.BaseSplitActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory

class MainActivity : BaseSplitActivity() {

    private lateinit var cryptographerFragment:CryptographerFragment
    private lateinit var splitInstallManager:SplitInstallManager
    val TAG_CRYPTOGRAPHER_FRAGMENT = "cryptographer"
    val TAG_EXERCISES_FRAGMENT = "exercises"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splitInstallManager=SplitInstallManagerFactory.create(applicationContext)
        checkTutorialStatus()

        if (savedInstanceState == null) {
            cryptographerFragment = CryptographerFragment()
            supportFragmentManager.beginTransaction().add(
                    R.id.main_fragment_container,
                    cryptographerFragment,
                    TAG_CRYPTOGRAPHER_FRAGMENT
                )
                .commit()
        } else {
            cryptographerFragment = supportFragmentManager.findFragmentByTag(TAG_CRYPTOGRAPHER_FRAGMENT) as CryptographerFragment

        }

        Log.e("BUNDLES", "skip login" + Storage.getSkipTutorial(applicationContext).toString())
        Log.e("BUNDLES", "uninstall" + Storage.getUninstallTutorial(applicationContext).toString())

    }

    private fun checkTutorialStatus(){
        if(splitInstallManager.installedModules.contains(Features.Tutorial.FEATURE_NAME)
            && Storage.getUninstallTutorial(applicationContext))
            splitInstallManager.deferredUninstall(arrayListOf(Features.Tutorial.FEATURE_NAME))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_navigation, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            R.id.settings_help -> {
                if (splitInstallManager.installedModules.contains(Features.Tutorial.FEATURE_NAME)
                    && !Storage.getUninstallTutorial(applicationContext)
                )
                    navigateToTutorial()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToTutorial(){
        val intent = Intent(Intent.ACTION_VIEW).setClassName(
            BuildConfig.APPLICATION_ID,
            Features.Tutorial.ACTIVITY_TUTORIAL_DIRECTORY
        )
        startActivity(intent)

    }

}