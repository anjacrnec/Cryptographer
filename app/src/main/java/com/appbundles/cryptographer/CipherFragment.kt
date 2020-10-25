package com.appbundles.cryptographer

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText

abstract class CipherFragment:Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
        setInputTextListener()
        setKeyTextListener()
    }

    fun enableButtons(btnEncrypt: Button, btnDecrypt: Button, toEnable: Boolean) {
            btnEncrypt.isEnabled = toEnable
            btnDecrypt.isEnabled = toEnable
    }

    fun enableButtons(btnEncrypt: Button, toEnable: Boolean) {
        btnEncrypt.isEnabled = toEnable
    }

    fun clearFields(input: TextInputEditText, key: TextInputEditText, output: TextInputEditText) {
        input.setText("")
        key.setText("")
        output.setText("")
        clearFocus(input, key)
    }

    fun clearFields(
        input: TextInputEditText,
        keyA: TextInputEditText,
        keyB: TextInputEditText,
        output: TextInputEditText
    ) {
        input.setText("")
        keyA.setText("")
        keyB.setText("")
        output.setText("")
        clearFocus(input, keyA, keyB)
    }

    fun clearFocus(input: TextInputEditText, key: TextInputEditText) {
        input.clearFocus()
        key.clearFocus()
    }

    fun clearFocus(input: TextInputEditText, keyA: TextInputEditText, keyB: TextInputEditText) {
        input.clearFocus()
        keyA.clearFocus()
        keyB.clearFocus()
    }

    abstract fun initialize()
    abstract fun encrypt()
    abstract fun decrypt()
    abstract fun setInputTextListener()
    abstract fun setKeyTextListener()
    abstract fun clear()
    fun test(){

    }

}