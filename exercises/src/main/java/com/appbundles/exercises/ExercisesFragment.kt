package com.appbundles.exercises

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bundles.BaseSplitFragment
import kotlinx.android.synthetic.main.fragment_exercises.*
import rita.RiTa


class ExercisesFragment : BaseSplitFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        exConfigureBtn.setOnClickListener {
            exerciseExpand.toggle()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v= inflater.inflate(R.layout.fragment_exercises, container, false)

        return v
    }

}