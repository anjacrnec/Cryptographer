package com.appbundles.cryptographer.main

import com.appbundles.cryptographer.cryptographer.CryptographerFragment
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appbundles.cryptographer.*
import com.appbundles.cryptographer.alerts.AlertDialog
import com.appbundles.cryptographer.alerts.AlertFragment
import com.appbundles.cryptographer.features.Session
import com.example.bundles.BaseSplitActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseSplitActivity(), AlertDialog.OnClickListener, MainCallback {


    private lateinit var session:Session
    private var cryptographerFragment: Fragment?=null
    private var exercisesFragment:Fragment?=null
    private var storageFragment:Fragment?=null
    private lateinit var splitInstallManager:SplitInstallManager
    private lateinit var listener: SplitInstallStateUpdatedListener
    private lateinit var viewModel: MainViewModel
    val TAG_CRYPTOGRAPHER_FRAGMENT = "cryptographer"
    val TAG_EXERCISES_FRAGMENT = "exercises"
    val TAG_STATUS_FRAGMENT="status"
    val TAG_STORAGE_FRAGMENT="storage"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        splitInstallManager= App.getSplitInstallManager()

        initFeatures()
        initFragments(savedInstanceState)
        initBottomNavigation()
        initViewModel()
        listener=viewModel.initSplitListener()

    }

    override fun onResume() {
        super.onResume()
        splitInstallManager.registerListener(listener)
    }

    override fun onPause() {
        super.onPause()
        splitInstallManager.unregisterListener(listener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_navigation, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.settings_help -> {
                if (App.getTutorialFeatureUtil().isInstalled()
                    && !Storage.getUninstallTutorial(applicationContext)
                )
                    navigateToTutorial()
                else
                    showTutorialDownloadDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.sessionValue.observe(this, Observer { session ->
           this.session=session
        })


        viewModel.stateValue.observe(this, Observer { state ->
            val dialogDownloading: AlertDialog? = findFragmentByTag(AlertDialog.DIALOG_DOWNLOADING.toString()) as AlertDialog?
            val alertFragment: AlertFragment? = findFragmentByTag(TAG_STATUS_FRAGMENT) as AlertFragment?
            var featuresDownloading=""

            for(featureName in state.moduleNames()){
                for(feature in App.getAllFeaturesUtil())
                    if(featureName==feature.featureName){
                        if(dialogDownloading==null || state.moduleNames().size==1)
                            featuresDownloading=featuresDownloading+feature.getLocalFeatureName()+" "
                        else {
                            featuresDownloading = featuresDownloading + feature.getLocalFeatureName()+"\n"
                        }
                        break
                    }
            }

            when (state.status()) {
                SplitInstallSessionStatus.PENDING -> {
                    dialogDownloading?.setTitle(ResUtil.getString(this, R.string.status_pending))
                    dialogDownloading?.setBody(featuresDownloading)
                    alertFragment?.updateStatus(ResUtil.getString(this, R.string.status_pending),featuresDownloading, null, true)
                }

                SplitInstallSessionStatus.DOWNLOADING -> {
                    dialogDownloading?.setTitle(ResUtil.getString(this, R.string.status_downloading))
                    dialogDownloading?.setBody(featuresDownloading)
                    alertFragment?.updateStatus(
                        ResUtil.getString(this, R.string.status_downloading),featuresDownloading, null, true)
                }

                SplitInstallSessionStatus.INSTALLING -> {
                    dialogDownloading?.setTitle(ResUtil.getString(this, R.string.status_installing))
                    dialogDownloading?.setBody(featuresDownloading)
                    alertFragment?.updateStatus(ResUtil.getString(this, R.string.status_installing),featuresDownloading, null, true)
                }

                SplitInstallSessionStatus.INSTALLED -> {
                    dialogDownloading?.setTitle(ResUtil.getString(this, R.string.status_installed))
                    dialogDownloading?.setBody(featuresDownloading)
                    dialogDownloading?.setIcon(R.drawable.ic_done)
                    dialogDownloading?.setOptionOne(null)
                    dialogDownloading?.setOptionTwo(null)
                    dialogDownloading?.setOptionThree(ResUtil.getString(this, R.string.ok))
                    alertFragment?.updateStatus(
                        ResUtil.getString(this, R.string.status_installed),featuresDownloading,
                        R.drawable.ic_done, false)
                    hideAlertFragment(true)

                    if (state.moduleNames().contains(App.getExerciseFeatureUtil().featureName) &&
                            !state.moduleNames().contains(App.getStorageFeatureUtil().featureName)) {

                        exercisesFragment = FragmentUtil.ExercisesFragment()
                        if(mainBottomNavigation.menu.findItem(R.id.navExercises).isChecked)
                            loadFragment(R.id.main_fragment_container, exercisesFragment!!, TAG_EXERCISES_FRAGMENT)
                        else
                            loadHiddenFragment(R.id.main_fragment_container, exercisesFragment!!, TAG_EXERCISES_FRAGMENT)

                    } else if (state.moduleNames().contains(App.getStorageFeatureUtil().featureName) &&
                        !state.moduleNames().contains(App.getExerciseFeatureUtil().featureName)) {

                        storageFragment = FragmentUtil.StorageFragment()
                        loadHiddenFragment(R.id.main_fragment_container, storageFragment!!, TAG_STORAGE_FRAGMENT)

                    } else if (state.moduleNames().contains(App.getStorageFeatureUtil().featureName) &&
                        state.moduleNames().contains(App.getExerciseFeatureUtil().featureName)){

                        exercisesFragment = FragmentUtil.ExercisesFragment()
                        loadHiddenFragment(R.id.main_fragment_container, exercisesFragment!!, TAG_EXERCISES_FRAGMENT)
                        storageFragment = FragmentUtil.StorageFragment()
                        loadHiddenFragment(R.id.main_fragment_container, storageFragment!!, TAG_STORAGE_FRAGMENT)
                    }

                    initBottomNavigation()
                }

                SplitInstallSessionStatus.CANCELED -> {
                    dialogDownloading?.dismiss()
                }

                SplitInstallSessionStatus.FAILED -> {
                    dialogDownloading?.setTitle(ResUtil.getString(this, R.string.status_error))
                    dialogDownloading?.setIcon(R.drawable.ic_error)
                    alertFragment?.updateStatus(
                        ResUtil.getString(this, R.string.status_error),featuresDownloading,
                        R.drawable.ic_error, true)
                }
            }
        })

    }

    private fun initFragments(savedInstanceState: Bundle?){
        cryptographerFragment = findFragmentByTag(TAG_CRYPTOGRAPHER_FRAGMENT)
        exercisesFragment = findFragmentByTag(TAG_EXERCISES_FRAGMENT)
        storageFragment = findFragmentByTag(TAG_STORAGE_FRAGMENT)
        val container= R.id.main_fragment_container

        if (savedInstanceState == null) {
            cryptographerFragment = CryptographerFragment()
            loadFragment(container, cryptographerFragment!!, TAG_CRYPTOGRAPHER_FRAGMENT)

            if(App.getExerciseFeatureUtil().isInstalled()){
                exercisesFragment= FragmentUtil.ExercisesFragment()
                loadHiddenFragment(container, exercisesFragment!!, TAG_EXERCISES_FRAGMENT)
            }

            if(App.getStorageFeatureUtil().isInstalled()){
                storageFragment= FragmentUtil.StorageFragment()
                loadHiddenFragment(container, storageFragment!!, TAG_STORAGE_FRAGMENT)
            }
        } else {
            cryptographerFragment = findFragmentByTag(TAG_CRYPTOGRAPHER_FRAGMENT) as CryptographerFragment
        }
    }

    private fun initBottomNavigation(){
            mainBottomNavigation.setOnNavigationItemSelectedListener(null)
            val menu=mainBottomNavigation.menu
            val exStatus = Storage.getExercisesStatus(this)
            if (exStatus == -1) {
                hideBottomNavigation()
                return
            }

            if (App.getStorageFeatureUtil().isInstalled() && menu.size()<3) {
                val itemStorage= ResUtil.getString("storage")
                val itemStorageIcon= ResUtil.getDrawable("ic_storage")
                menu.add(Menu.NONE, 3, Menu.NONE, itemStorage).icon = itemStorageIcon
            }

            if (App.getExerciseFeatureUtil().isInstalled())
                menu.findItem(R.id.navExercises).title = getString(R.string.exercise)
            else
                menu.findItem(R.id.navExercises).title = getString(R.string.exercises_download)

            mainBottomNavigation.setOnNavigationItemSelectedListener {
                val item: Int = it.itemId
                if (item == R.id.navCryptography) {
                    navigateToCryptographerFragment()
                } else if (item == R.id.navExercises) {
                    if (App.getExerciseFeatureUtil().isInstalled())
                        navigateToExercisesFragment()
                    else
                        showExercisesDownloadDialog()
                } else if (item == 3)
                    navigateToStorageFragment()

                true
            }
    }


    private fun initFeatures(){
        if(App.getTutorialFeatureUtil().isInstalled()
            && Storage.getUninstallTutorial(applicationContext)
        )
            splitInstallManager.deferredUninstall(
                arrayListOf(
                    App.getTutorialFeatureUtil().featureName,
                    App.getImageFeatureutil().featureName
                )
            )
    }

    private fun navigateToTutorial(){
        val intent = ActivityUtil.TutorialActivity(this)
        startActivity(intent)
    }

    private fun navigateToExercisesFragment(){
        showFragment(exercisesFragment)
        hideFragment(cryptographerFragment)
        hideFragment(storageFragment)
    }

    private fun navigateToCryptographerFragment(){
        hideFragment(exercisesFragment)
        showFragment(cryptographerFragment)
        hideFragment(storageFragment)
    }

    private fun navigateToStorageFragment(){
        hideFragment(cryptographerFragment)
        hideFragment(exercisesFragment)
        showFragment(storageFragment)
    }


    private fun showExercisesDownloadingDialog(){
      val dialogDownloading= AlertDialog.newInstance(
          AlertDialog.DIALOG_DOWNLOADING,
          ResUtil.getString(this,R.string.status_pending),
          "",
          null,
          ResUtil.getString(this,R.string.hide),
          ResUtil.getString(this,R.string.cancel),
          null,
          null,
          true
      )
        dialogDownloading.isCancelable=false
        dialogDownloading.show(supportFragmentManager, AlertDialog.DIALOG_DOWNLOADING.toString())
    }

    private fun showExercisesDownloadDialog() {
        val dialog= AlertDialog.newInstance(
            AlertDialog.DIALOG_DOWNLOAD_EXERCISE,
            ResUtil.getString(this,R.string.download_feature),
            ResUtil.getString(this,R.string.download_exercises_desc),
            ResUtil.getString(this,R.string.download_include_storage),
            ResUtil.getString(this,R.string.yes),
            ResUtil.getString(this,R.string.no),
            null,
            R.drawable.ic_download,
            false
        )
        dialog.isCancelable=false
        dialog.show(supportFragmentManager, AlertDialog.DIALOG_DOWNLOAD_EXERCISE.toString())
    }

    private fun showStorageDownloadDialog(){
        val dialog= AlertDialog.newInstance(
            AlertDialog.DIALOG_DOWNLOAD_STORAGE,
            ResUtil.getString(this,R.string.download_feature),
            ResUtil.getString(this,R.string.download_storage_desc),
            null,
            ResUtil.getString(this,R.string.yes),
            ResUtil.getString(this,R.string.no),
            null,
            R.drawable.ic_download,
            false
        )
        dialog.isCancelable=false
        dialog.show(supportFragmentManager, AlertDialog.DIALOG_DOWNLOAD_STORAGE.toString())
    }

    private fun showTutorialDownloadDialog(){
        val dialog= AlertDialog.newInstance(
            AlertDialog.DIALOG_DOWNLOAD_TUTORIAL,
            ResUtil.getString(this,R.string.download_feature),
            ResUtil.getString(this,R.string.download_tutorial_desc) ,
            null,
            ResUtil.getString(this,R.string.yes),
            ResUtil.getString(this,R.string.no),
            null,
            R.drawable.ic_download,
            false
        )
        dialog.isCancelable=false
        dialog.show(supportFragmentManager, AlertDialog.DIALOG_DOWNLOAD_TUTORIAL.toString())
    }

    private fun showAlertFragment(status: String?, body:String?,progress: Boolean){
        val statusFragment= AlertFragment.newInstance(status, body,progress)
        loadFragment(R.id.mainStatusContainer, statusFragment, TAG_STATUS_FRAGMENT)
    }

    private fun hideAlertFragment(delay: Boolean)
    {
        if(delay)
            Handler(Looper.getMainLooper()).postDelayed({ run { hideAlertFragment() } }, 3000)
                else
            hideAlertFragment()
    }

    private fun hideAlertFragment(){
       val alertFragment: AlertFragment?=supportFragmentManager.findFragmentByTag(TAG_STATUS_FRAGMENT) as AlertFragment?
        removeFragment(alertFragment)
    }

    private fun hideBottomNavigation(){
        mainBottomNavigation.visibility=View.GONE
    }


    //download exercises dialog callbacks
    override fun onDownloadExercisesNo() {
        mainBottomNavigation.menu.findItem(R.id.navCryptography).isChecked=true
        val dialog:AlertDialog?=findFragmentByTag(AlertDialog.DIALOG_DOWNLOAD_EXERCISE.toString()) as AlertDialog
        dialog?.dismiss()
    }

    override fun onDownloadExerciseYes(includeSave: Boolean) {
        val dialog:AlertDialog?=findFragmentByTag(AlertDialog.DIALOG_DOWNLOAD_EXERCISE.toString()) as AlertDialog
        dialog?.dismiss()
        if(includeSave)
            viewModel.install(arrayListOf(
                App.getExerciseFeatureUtil().featureName,
                App.getStorageFeatureUtil().featureName))
        else
            viewModel.install(arrayListOf(App.getExerciseFeatureUtil().featureName))
        showExercisesDownloadingDialog()
    }

    //download storage dialog callbacks
    override fun onDownloadStorageNo() {
        (findFragmentByTag(AlertDialog.DIALOG_DOWNLOAD_STORAGE.toString()) as AlertDialog?)?.dismiss()
    }

    override fun onDownloadStorageYes() {
        (findFragmentByTag(AlertDialog.DIALOG_DOWNLOAD_STORAGE.toString()) as AlertDialog?)?.dismiss()
        viewModel.install(arrayListOf(App.getStorageFeatureUtil().featureName))
        showExercisesDownloadingDialog()
    }


    //download tutorial callbacks
    override fun onDownloadTutorialNo() {
        (findFragmentByTag(AlertDialog.DIALOG_DOWNLOAD_TUTORIAL.toString()) as AlertDialog?)?.dismiss()
    }

    override fun onDownloadTutorialYes() {

    }

    //downloading dialog callbacks
    override fun onDownloadingCancel() {
        if(!App.getExerciseFeatureUtil().isInstalled())
            mainBottomNavigation.menu.findItem(R.id.navCryptography).isChecked=true
        session?.let { splitInstallManager.cancelInstall(session.id) }
        (findFragmentByTag(AlertDialog.DIALOG_DOWNLOADING.toString()) as AlertDialog?)?.dismiss()
    }

    override fun onDownloadingHide() {
        if(!App.getExerciseFeatureUtil().isInstalled())
            mainBottomNavigation.menu.findItem(R.id.navCryptography).isChecked=true
        val dialog=(findFragmentByTag(AlertDialog.DIALOG_DOWNLOADING.toString()) as AlertDialog?)
        val body= dialog?.getBody()
        val title=dialog?.getTitle()
        dialog?.dismiss()
        showAlertFragment(title,body,true)
    }

    override fun onDownloadingFinish() {
        (findFragmentByTag(AlertDialog.DIALOG_DOWNLOADING.toString()) as AlertDialog?)?.dismiss()
    }



    override fun isAlertFragmentVisible(): Boolean {
        return findFragmentByTag(AlertDialog.DIALOG_DOWNLOADING.toString()) != null
    }
    override fun showDialog() {
        showStorageDownloadDialog()
    }
}