package com.appbundles.cryptographer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appbundles.cryptographer.cryptography.VigenereCiphere
import kotlinx.android.synthetic.main.fragment_cipher.*

class VigenereFragment:CipherFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cipherEncryptBtn.setOnClickListener {
            encrypt()
        }

        cipherDecryptBtn.setOnClickListener {
            decrypt()
        }

        cipherClearBtn.setOnClickListener {
            clear()
        }

        cipherExpandBtn.setOnClickListener {
            cipherExpandableLayout.toggle()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_cipher, container, false)
        return v
    }

    override fun initialize() {
        cipherExpandBtn.text=resources.getString(R.string.vignere_cipher)
        cipherKeyLayout.helperText=""
    }

    override fun encrypt() {
        val output= VigenereCiphere.encrypt(cipherInput.text.toString(), cipherKey.text.toString())
        cipherOutput.setText(output)
        clearFocus(cipherInput, cipherKey)
    }

    override fun decrypt() {
        val output= VigenereCiphere.decrypt(cipherInput.text.toString(), cipherKey.text.toString())
        cipherOutput.setText(output)
        clearFocus(cipherInput, cipherKey)
    }

    override fun setInputTextListener() {
        cipherInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val enableButtons: Boolean =
                    !(s.toString().isEmpty() || cipherKey.text.toString().isEmpty())
                enableButtons(cipherEncryptBtn, cipherDecryptBtn, enableButtons)
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun setKeyTextListener() {
        cipherKey.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val enableButtons: Boolean = !(s.toString().isEmpty() || cipherInput.getText().toString().isEmpty())
                enableButtons(cipherEncryptBtn, cipherDecryptBtn, enableButtons)
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun clear() {
        clearFields(cipherInput,cipherKey,cipherOutput)
    }
}