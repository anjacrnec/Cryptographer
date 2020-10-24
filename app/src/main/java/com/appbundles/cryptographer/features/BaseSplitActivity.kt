package com.example.bundles

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.play.core.splitcompat.SplitCompat


abstract class BaseSplitActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.install(this)
    }

    fun loadFragment(container: Int, fragment: Fragment, tag:String){
        supportFragmentManager.beginTransaction().add(container, fragment).commit()
    }

}

