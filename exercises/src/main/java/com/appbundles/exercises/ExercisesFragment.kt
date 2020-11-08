package com.appbundles.exercises

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bundles.BaseSplitFragment
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_exercises.*
import com.appbundles.cryptographer.R.drawable


class ExercisesFragment : BaseSplitFragment(){

    private lateinit var viewModel: ExercisesViewModel
    private lateinit var exercise: Exercise

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setChipChangeListeners()

        initViewModel()

        generateExercise()

        setAnswerTextChangeListener()

        exNextBtn.setOnClickListener {
            generateExercise()
        }

        exAnswerBtn.setOnClickListener {
            showExerciseAnswer()
        }

        exConfigureBtn.setOnClickListener {
            exExpand.toggle()
        }

    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this).get(ExercisesViewModel::class.java)

        viewModel.methodValue.observe(viewLifecycleOwner, Observer {
            when (it) {
                Exercise.CAESAR -> exercise = CaesarExercise().generate(context!!)
                Exercise.AFFINE -> exercise = AffineExercise().generate(context!!)
                Exercise.VIGENERE -> exercise = VigenereExercise().generate(context!!)
                Exercise.PLAYFAIR -> exercise = PlayfairExercise().generate(context!!)
                Exercise.ORTHO -> exercise = OrthogonalExercise().generate(context!!)
                Exercise.ORTHO_REVERSE -> exercise = ReverseOrthogonalExercise().generate(context!!)
                Exercise.DIAGONAL -> exercise = DiagonalExercise().generate(context!!)
            }
            showExercise()
        })
    }

    private fun generateExercise(){
        viewModel.generateExercise(
            exChipCaesar.isChecked,
            exChipAffine.isChecked,
            exChipVigenere.isChecked,
            exChipPlayfair.isChecked,
            exChipOrtho.isChecked,
            exChipOrthoReverse.isChecked,
            exChipDiagonal.isChecked
        )
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