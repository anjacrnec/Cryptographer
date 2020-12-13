package com.appbundles.cryptographer

import android.content.Intent
import android.os.Bundle
import com.appbundles.cryptographer.features.TutorialSingleton
import com.appbundles.cryptographer.main.MainActivity
import com.example.bundles.BaseSplitActivity

class SplashActivity : BaseSplitActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
       if(Storage.getSkipTutorial())  {
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
        val intent = TutorialSingleton.getInstance().createTutorialIntent(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

}

