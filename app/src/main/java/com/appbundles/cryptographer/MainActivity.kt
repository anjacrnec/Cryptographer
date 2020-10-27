package com.appbundles.cryptographer

import CryptographerFragment
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.appbundles.cryptographer.features.Features
import com.example.bundles.BaseSplitActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import kotlinx.android.synthetic.main.activity_main.*


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
            ).commit()

            if(isExercisesInstalled()){
                val exercisesFragment=Class.forName(Features.Exercises.FRAGMENT_EXERCISES_DIRECTORY).newInstance() as Fragment
//                var exercisesFragment=Fragment.instantiate(applicationContext,Features.Exercises.FRAGMENT_EXERCISES_DIRECTORY)
                supportFragmentManager.beginTransaction().add(
                    R.id.main_fragment_container,
                    exercisesFragment,
                    TAG_EXERCISES_FRAGMENT
                ).hide(exercisesFragment).commit()
            }

        } else {
            cryptographerFragment = supportFragmentManager.findFragmentByTag(
                TAG_CRYPTOGRAPHER_FRAGMENT
            ) as CryptographerFragment

        }

        initBottomNavigation()
        mainBottomNavigation.setOnNavigationItemSelectedListener {
            val item: Int = it.getItemId()
            val cryptographerFragment= supportFragmentManager.findFragmentByTag(TAG_CRYPTOGRAPHER_FRAGMENT)
            val exercisesFragment=supportFragmentManager.findFragmentByTag(TAG_EXERCISES_FRAGMENT)
            if (item == R.id.navCryptography) {

                exercisesFragment?.let { fragment ->
                    supportFragmentManager.beginTransaction().hide(fragment).commit()
                }

                cryptographerFragment?.let { fragment ->
                    supportFragmentManager.beginTransaction().show(fragment).commit()
                }

            } else if (item == R.id.navExercises) {
                exercisesFragment?.let { fragment ->
                    supportFragmentManager.beginTransaction().show(fragment).commit()

                    cryptographerFragment?.let { fragment ->
                        supportFragmentManager.beginTransaction().hide(fragment).commit()
                    }
                }

            }
            true
        }

    }

    private fun isExercisesInstalled():Boolean{
        return splitInstallManager.installedModules.contains(Features.Exercises.FEATURE_NAME)
    }

    private fun isTutorialInstalled():Boolean{
       return splitInstallManager.installedModules.contains(Features.Tutorial.FEATURE_NAME)
    }

    private fun checkTutorialStatus(){
        if(isTutorialInstalled()
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
                if (isTutorialInstalled()
                    && !Storage.getUninstallTutorial(applicationContext)
                )
                    navigateToTutorial()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initBottomNavigation(){
        if(!splitInstallManager.installedModules.contains(Features.Exercises.FEATURE_NAME))
            mainBottomNavigation.menu.findItem(R.id.navExercises).title=getString(R.string.exercises_download)
    }

    private fun navigateToTutorial(){
        val intent = Intent(Intent.ACTION_VIEW).setClassName(
            BuildConfig.APPLICATION_ID,
            Features.Tutorial.ACTIVITY_TUTORIAL_DIRECTORY
        )
        startActivity(intent)

    }

}