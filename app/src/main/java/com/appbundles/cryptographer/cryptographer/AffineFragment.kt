package com.appbundles.cryptographer.cryptographer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appbundles.cryptographer.R
import com.appbundles.cryptographer.cryptography.AffineCipher
import kotlinx.android.synthetic.main.fragment_affine.*

class AffineFragment: CipherFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        affineEncryptBtn.setOnClickListener {
            encrypt()
        }

        affineDecryptBtn.setOnClickListener {
            decrypt()
        }

        affineClearBtn.setOnClickListener {
            clear()
        }

        affineExpandBtn.setOnClickListener {
            affineExpandLayout.toggle()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_affine, container, false)
            return v
        }

    override fun initialize() {

    }

    override fun encrypt() {
        val input = affineInput.text.toString()
        val keyA = affineKeyA.text.toString().toInt()
        val keyB = affineKeyB.text.toString().toInt()
        val output = AffineCipher.encrypt(input, keyA, keyB)
        affineOutput.setText(output)
        clearFocus(affineInput, affineKeyA, affineKeyB)
    }

    override fun decrypt() {
        val input = affineInput.text.toString()
        val keyA = affineKeyA.text.toString().toInt()
        val keyB = affineKeyB.text.toString().toInt()
        val output = AffineCipher.decrypt(input, keyA, keyB)
        affineOutput.setText(output)
        clearFocus(affineInput, affineKeyA, affineKeyB)
    }

    override fun setInputTextListener() {
        affineInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val enableButtons: Boolean = !(s.toString().isEmpty()
                        || affineKeyA.text.toString().isEmpty() || affineKeyB.text.toString()
                    .isEmpty())
                enableButtons(affineEncryptBtn, affineDecryptBtn, enableButtons)
            }

            override fun afterTextChanged(s: Editable) {}
        })

    }

    override fun setKeyTextListener() {
        affineKeyB.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val enableButtons: Boolean = !(s.toString().isEmpty() || affineKeyA.text.toString()
                    .isEmpty() || affineKeyB.text.toString().isEmpty())
                enableButtons(affineEncryptBtn, affineDecryptBtn, enableButtons)
            }

            override fun afterTextChanged(s: Editable) {}
        })

        affineKeyA.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var enableButtons = !(s.toString().isEmpty() || !AffineCipher.isKeyAValid(
                    s.toString().toInt()
                )
                        || affineKeyB.text.toString().isEmpty() || affineInput.text.toString()
                    .isEmpty())
                enableButtons(affineEncryptBtn, affineDecryptBtn, enableButtons)
            }

            override fun afterTextChanged(s: Editable) {}
        })


    }

    override fun clear() {
        clearFields(affineInput, affineKeyA, affineKeyB, affineOutput)
    }
}
