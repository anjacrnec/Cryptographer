package com.appbundles.tutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.bundles.BaseSplitActivity

class TutorialActivity : BaseSplitActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_tutorial)

        if(savedInstanceState==null)
            loadFragment(R.id.tutorial_fragment_container,TutorialFragment(),"TUTORIAL_FRAGMENT")
        else
            supportFragmentManager.findFragmentByTag("TUTORIAL_FRAGMENT")
    }

}