package com.appbundles.cryptographer

import CryptographerFragment
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.appbundles.cryptographer.features.Features
import com.example.bundles.BaseSplitActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseSplitActivity(),CustomDialog.OnClickListener {


    private lateinit var cryptographerFragment:CryptographerFragment
    private lateinit var splitInstallManager:SplitInstallManager
    private lateinit var dialog: CustomDialog
    val TAG_CRYPTOGRAPHER_FRAGMENT = "cryptographer"
    val TAG_EXERCISES_FRAGMENT = "exercises"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splitInstallManager=SplitInstallManagerFactory.create(applicationContext)

        checkTutorialStatus()
        initFragments(savedInstanceState)
        initBottomNavigation()
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


    private fun initFragments(savedInstanceState: Bundle?){
        if (savedInstanceState == null) {
            cryptographerFragment = CryptographerFragment()
            supportFragmentManager.beginTransaction().add(
                R.id.main_fragment_container, cryptographerFragment, TAG_CRYPTOGRAPHER_FRAGMENT
            ).commit()

            if(isExercisesInstalled()){
                val exercisesFragment=Class.forName(Features.Exercises.FRAGMENT_EXERCISES_DIRECTORY).newInstance() as Fragment
//                var exercisesFragment=Fragment.instantiate(applicationContext,Features.Exercises.FRAGMENT_EXERCISES_DIRECTORY)
                supportFragmentManager.beginTransaction().add(
                    R.id.main_fragment_container, exercisesFragment, TAG_EXERCISES_FRAGMENT
                ).hide(exercisesFragment).commit()
            }
        } else {
            cryptographerFragment = supportFragmentManager.findFragmentByTag(
                TAG_CRYPTOGRAPHER_FRAGMENT
            ) as CryptographerFragment
        }
    }

    private fun initBottomNavigation(){
        val exStatus=Storage.getExercisesStatus(this)
        if(exStatus==-1) {
            hideBottomNavigation()
            return
        }

        mainBottomNavigation.setOnNavigationItemSelectedListener {
            val item: Int = it.getItemId()
            if (item == R.id.navCryptography) {
                showCryptographerFragment()
            } else if (item == R.id.navExercises) {
                if(isExercisesInstalled())
                    showExercisesFragment()
                else
                    showExercisesDownloadDialog()

            }
            true
        }
        if(!isExercisesInstalled())
            mainBottomNavigation.menu.findItem(R.id.navExercises).title=getString(R.string.exercises_download)

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


    private fun showExercisesDownloadDialog() {
     dialog=CustomDialog.newInstance(
              "title",
              "In order to use this feature the app needs to download some files from playstore",
              "Include ability to save exercises",
              "yes",
              "no",
              "never",
                R.drawable.ic_download)
        dialog.isCancelable=false
        dialog.show(supportFragmentManager,"dialog")

    }


    private fun showExercisesFragment(){
        val cryptographerFragment= supportFragmentManager.findFragmentByTag(
            TAG_CRYPTOGRAPHER_FRAGMENT
        )
        val exercisesFragment=supportFragmentManager.findFragmentByTag(TAG_EXERCISES_FRAGMENT)
        exercisesFragment?.let { fragment ->
            supportFragmentManager.beginTransaction().show(fragment).commit()

            cryptographerFragment?.let { fragment ->
                supportFragmentManager.beginTransaction().hide(fragment).commit()
            }
        }
    }

    private fun showCryptographerFragment(){
        val cryptographerFragment= supportFragmentManager.findFragmentByTag(
            TAG_CRYPTOGRAPHER_FRAGMENT
        )
        val exercisesFragment=supportFragmentManager.findFragmentByTag(TAG_EXERCISES_FRAGMENT)
        exercisesFragment?.let { fragment ->
            supportFragmentManager.beginTransaction().hide(fragment).commit()
        }
        cryptographerFragment?.let { fragment ->
            supportFragmentManager.beginTransaction().show(fragment).commit()
        }
    }

    private fun navigateToTutorial(){
        val intent = Intent(Intent.ACTION_VIEW).setClassName(
            BuildConfig.APPLICATION_ID,
            Features.Tutorial.ACTIVITY_TUTORIAL_DIRECTORY
        )
        startActivity(intent)

    }

    private fun hideBottomNavigation(){
        mainBottomNavigation.visibility=View.GONE
    }


    override fun onCancelDialog() {
        mainBottomNavigation.menu.findItem(R.id.navCryptography).isChecked=true
        dialog?.dismiss()
    }

    override fun onDownloadExerciseYes(includeSave: Boolean) {

    }

    override fun onDownloadExerciseNever() {
        mainBottomNavigation.menu.findItem(R.id.navCryptography).isChecked=true
        Storage.setExercisesStatus(this,-1)
        hideBottomNavigation()
        dialog?.dismiss()
    }



}