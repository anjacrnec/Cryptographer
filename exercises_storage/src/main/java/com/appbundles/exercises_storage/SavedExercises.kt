package com.appbundles.exercises_storage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bundles.BaseSplitFragment
import kotlinx.android.synthetic.main.fragment_saved_exercises.*


class SavedExercises : BaseSplitFragment() {

    var listStoredExercises:List<StoredExercise>?=null
    private lateinit var adapter:StoredExercisesAdapter
    private lateinit var storageViewModel: StorageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StoredExerciseDatabase.getDatabaseInstance(activity!!.applicationContext)

        storageViewModel = ViewModelProviders.of(this).get(StorageViewModel::class.java)
        storageViewModel.getStoredExercises().observe(viewLifecycleOwner, Observer { list ->
            adapter= StoredExercisesAdapter(list)
            recyclerStoredExercises.adapter = adapter
            recyclerStoredExercises.layoutManager = LinearLayoutManager(activity)
            adapter.notifyDataSetChanged()
        })


        val rep=StoredExerciseRepository( (StoredExerciseDatabase.getDatabaseInstance(activity!!.applicationContext)).storedExerciseDao())
        listStoredExercises= rep.getListWords()
        if(listStoredExercises!=null) {
            adapter = StoredExercisesAdapter(listStoredExercises!!)
            recyclerStoredExercises.adapter = adapter
            recyclerStoredExercises.layoutManager = LinearLayoutManager(activity)
        }


    }

     fun insert(){
        val rep=StoredExerciseRepository( (StoredExerciseDatabase.getDatabaseInstance(activity!!.applicationContext)).storedExerciseDao())
        rep.insert(StoredExercise("asdra","body","answer"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_exercises, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SavedExercises().apply {
                arguments = Bundle().apply {

                }
            }
    }
}