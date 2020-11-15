package com.appbundles.exercises

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.appbundles.cryptographer.AlertDialog
import com.appbundles.cryptographer.App
import com.appbundles.cryptographer.MainCallback
import com.appbundles.cryptographer.features.Features
import com.example.bundles.BaseSplitFragment
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_exercises.*


class ExercisesFragment : BaseSplitFragment(),AlertDialog.OnClickListener{


    private lateinit var exercise: Exercise
    private lateinit var mainCallback: MainCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainCallback = context as MainCallback
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setChipChangeListeners()

        generateExercise()

        showExercise()

        setAnswerTextChangeListener()

        exNextBtn.setOnClickListener {
            generateExercise()
            showExercise()
        }

        exAnswerBtn.setOnClickListener {
            showExerciseAnswer()
        }

        exConfigureBtn.setOnClickListener {
            exExpand.toggle()
        }


        exSaveBtn.setOnClickListener{
            if(App.getStorageFeatureUtil().isInstalled()){

            } else {
                if(mainCallback.getCurrentSession().type!=App.getStorageFeatureUtil().featureName)
                    mainCallback.showDialog()
                else
                    Snackbar.make(exSaveBtn,"This module is already being downloaded",Snackbar.LENGTH_SHORT).show()
            }


        }

    }

    private fun generateExercise(){
        val method=RandomUtil.randomItem(
            hashMapOf(
                Exercise.CAESAR to exChipCaesar.isChecked,
                Exercise.AFFINE to exChipAffine.isChecked,
                Exercise.VIGENERE to exChipVigenere.isChecked,
                Exercise.PLAYFAIR to exChipPlayfair.isChecked,
                Exercise.ORTHO to exChipOrtho.isChecked,
                Exercise.ORTHO_REVERSE to exChipOrthoReverse.isChecked,
                Exercise.DIAGONAL to exChipDiagonal.isChecked
            )
        )
        exercise= Exercise.generateExercise(method,context!!)
    }

    private fun showExerciseAnswer(){
        exAnswer.setText(exercise.answer)
    }

    private fun showExercise(){
        exCipher.text=exercise.method
        if(exercise.type==Exercise.ENCRYPT) {
            exPlainText.text = exercise.plainText
            exCipherText.text="?"
        } else {
            exPlainText.text = "?"
            exCipherText.text=exercise.cipherText
        }
        exKey.text=exercise.key
        exBody.text=exercise.body
        exAnswer.setText("")
        exAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    private fun enableNextExercise(){
        for (i in 0 until exChipGroup.childCount) {
            val chip = exChipGroup.getChildAt(i) as Chip
            if (chip.isChecked) {
                exNextBtn.isEnabled = true
                return
            }
        }
        exNextBtn.isEnabled=false
        Snackbar.make(exSaveBtn, R.string.ciphers_disabled, Snackbar.LENGTH_SHORT).show()
    }

    private fun setAnswerTextChangeListener(){
        exAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        exAnswer.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) exAnswerLayout.isErrorEnabled = false

                if (s.toString().equals(exercise.answer, ignoreCase = true)) {
                    exAnswerLayout.isErrorEnabled = true
                    exAnswerLayout.error = resources.getString(R.string.correct_answer)
                    exAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

                } else if (!s.toString().isEmpty()) {
                    exAnswerLayout.isErrorEnabled = true
                    exAnswerLayout.error = resources.getString(R.string.wrong_answer)
                    exAnswer.setCompoundDrawablesWithIntrinsicBounds(0, 0, com.appbundles.cryptographer.R.drawable.ic_error, 0)
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setChipChangeListeners(){
        setChipChangeListener(exChipCaesar)
        setChipChangeListener(exChipAffine)
        setChipChangeListener(exChipVigenere)
        setChipChangeListener(exChipPlayfair)
        setChipChangeListener(exChipOrtho)
        setChipChangeListener(exChipOrthoReverse)
        setChipChangeListener(exChipDiagonal)
    }

    private fun setChipChangeListener(chip: Chip){
        chip.setOnCheckedChangeListener { _, _ ->
            enableNextExercise()
        }
    }



}