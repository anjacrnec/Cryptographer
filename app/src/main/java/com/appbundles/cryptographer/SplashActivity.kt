package com.appbundles.cryptographer

import android.content.Intent
import android.os.Bundle
import com.appbundles.cryptographer.features.Features
import com.example.bundles.BaseSplitActivity

class SplashActivity : BaseSplitActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

  /*      requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val mWindow = window
        mWindow.getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        )*/

        setContentView(R.layout.activity_splash)
       if(Storage.getSkipTutorial(applicationContext))  {
            navigateToMain()
       } else
       {
            navigateToTutorial()
       }
    }

    private fun navigateToMain(){
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun navigateToTutorial(){
        val intent = Intent(Intent.ACTION_VIEW).setClassName(
            BuildConfig.APPLICATION_ID,
            Features.Tutorial.ACTIVITY_TUTORIAL_DIRECTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

}

