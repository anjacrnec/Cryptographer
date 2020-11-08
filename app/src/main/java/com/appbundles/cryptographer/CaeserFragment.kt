package com.appbundles.cryptographer


import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appbundles.cryptographer.cryptography.CaesarCipher
import kotlinx.android.synthetic.main.fragment_cipher.*

class CaeserFragment :CipherFragment(){


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

    override fun initialize(){
        cipherExpandBtn.text=resources.getString(R.string.caeser_encryption)
        cipherKeyLayout.helperText=resources.getString(R.string.caeser_helper_key)
        cipherKey.inputType=InputType.TYPE_CLASS_NUMBER
    }

    override fun encrypt() {
        val output = CaesarCipher.encrypt(cipherInput.text.toString(), cipherKey.text.toString().toInt())
        clearFocus(cipherInput,cipherKey)
        cipherOutput.setText(output)
    }

    override fun decrypt() {
        val output = CaesarCipher.decrypt(cipherInput.text.toString(), cipherKey.text.toString().toInt())
        clearFocus(cipherInput,cipherKey)
        cipherOutput.setText(output)
    }

    override fun setInputTextListener() {
        cipherInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val enableButtons =
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
                val enableButtons = !(s.toString().isEmpty() || !CaesarCipher.isKeyValid(
                    s.toString().toInt()
                ) || cipherInput.text.toString().isEmpty())
                enableButtons(cipherEncryptBtn, cipherDecryptBtn, enableButtons)
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun clear() {
        clearFields(cipherInput,cipherKey,cipherOutput)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cipher, container, false)
    }



}