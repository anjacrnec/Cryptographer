package com.appbundles.cryptographer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appbundles.cryptographer.cryptography.DiagonalCipher
import com.appbundles.cryptographer.cryptography.OrthogonalCipher
import com.appbundles.cryptographer.cryptography.ReverseOrthogonalCipher
import com.appbundles.cryptographer.cryptography.TranspositionalCipher
import kotlinx.android.synthetic.main.fragment_transpositional.*

class TranspositionalFragment:CipherFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        transEncryptBtn.setOnClickListener {
            encrypt()
        }

        transClearBtn.setOnClickListener {
            clear()
        }

        transExpandBtn.setOnClickListener {
            transExpandLayout.toggle()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transpositional, container, false)
    }

    override fun initialize() {
        transRadioGroup.setOnCheckedChangeListener { _, _ ->
            var enableButtons = false
            val transpositionalHelper = getString(R.string.transpositional_helper_key)
            val diagonalHelper =
                transpositionalHelper + getString(R.string.transpositional_diagonal_key)
            if (rbDiagonal.isChecked) {
                transKeyLayout.helperText = diagonalHelper
                if (!DiagonalCipher.isKeyShorter(
                        transInput.text.toString(),
                        transKey.text.toString()
                    )
                ) enableButtons = false
            } else {
                transKeyLayout.helperText = transpositionalHelper
                if (!transInput.text.toString()
                        .isEmpty() && !transKey.text.toString()
                        .isEmpty() && TranspositionalCipher.isKeyValid(
                        transKey.text.toString()
                    )
                ) enableButtons = true
            }
            enableButtons(transEncryptBtn, enableButtons)
        }
    }

    override fun encrypt() {
        val input = transInput.text.toString()
        val key = transKey.text.toString()
        var output=""
        when {
            rbOrthogonal.isChecked -> {
                output = OrthogonalCipher.encrypt(input, key)
            }
            rbOrthogonalReverse.isChecked -> {
                output = ReverseOrthogonalCipher.encrypt(input, key)
            }
            rbDiagonal.isChecked -> {
                output = DiagonalCipher.encrypt(input, key)
            }
        }

        transOutput.setText(output)
        clearFocus(transInput, transKey)
    }

    override fun decrypt() {

    }

    override fun setInputTextListener() {
        transInput.addTextChangedListener(object : TextWatcher {
            var enableButtons = false
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty() || transKey.text.toString().isEmpty()) {
                    enableButtons = false
                } else enableButtons = !(rbDiagonal.isChecked && !DiagonalCipher.isKeyShorter(
                    s.toString(),
                    transKey.text.toString()
                ))
                enableButtons(transEncryptBtn, enableButtons)
            }

            override fun afterTextChanged(s: Editable) {}
        })


    }

    override fun setKeyTextListener() {
        transKey.addTextChangedListener(object : TextWatcher {
            var enableButtons = false
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty() || transInput.text.toString().isEmpty()) {
                    enableButtons = false
                } else if (!TranspositionalCipher.isKeyValid(s.toString())) {
                    enableButtons = false
                } else enableButtons = !(rbDiagonal.isChecked && !DiagonalCipher.isKeyShorter(
                    transInput.text.toString(),
                    s.toString()
                ))
                enableButtons(transEncryptBtn, enableButtons)
            }

            override fun afterTextChanged(s: Editable) {}
        })

    }

    override fun clear() {
        clearFields(transInput, transKey, transOutput)
        rbOrthogonal.isChecked = true
    }

}