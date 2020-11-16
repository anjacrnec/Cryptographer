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



    fun findFragmentByTag(tag:String):Fragment?{
        return supportFragmentManager.findFragmentByTag(tag)
    }

    fun hideFragment(fragment: Fragment?):Boolean{
        fragment?.let {
            supportFragmentManager.beginTransaction().hide(fragment).commit()
            return true
        }
        return false
    }

    fun showFragment(fragment: Fragment?){
        fragment?.let {
            supportFragmentManager.beginTransaction().show(fragment).commit()
        }
    }

    fun loadFragment(container: Int, fragment: Fragment, tag:String){
        supportFragmentManager.beginTransaction().add(container, fragment,tag).commit()
    }

    fun loadHiddenFragment(container: Int, fragment: Fragment, tag:String){
        supportFragmentManager.beginTransaction().add(container, fragment,tag).hide(fragment).commit()
    }

    fun removeFragment(fragment: Fragment?){
        fragment?.let {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
    }



}

