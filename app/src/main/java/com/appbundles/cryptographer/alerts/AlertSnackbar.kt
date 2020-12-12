package com.appbundles.cryptographer.alerts

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.appbundles.cryptographer.ResUtil
import com.google.android.material.snackbar.Snackbar

class AlertSnackbar(
    val view:View,
    val message:String,
    val length:Int)
{

    fun show(){
        val snackbar= Snackbar.make(view, message, length)
        val color=ContextCompat.getColor(view.context,com.appbundles.cryptographer.R.color.snackbar)
        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action).setBackgroundColor(color)
        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).setBackgroundColor(color)
        snackbar.view.setBackgroundColor(color)
        snackbar.show()
    }


}