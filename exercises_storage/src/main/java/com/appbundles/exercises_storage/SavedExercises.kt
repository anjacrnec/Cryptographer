package com.appbundles.exercises_storage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bundles.BaseSplitFragment
import kotlinx.android.synthetic.main.fragment_saved_exercises.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SavedExercises.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavedExercises : BaseSplitFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StoredExerciseDatabase.getDatabaseInstance(activity!!.applicationContext)

        btnAdd.setOnClickListener {
            insert()
        }

        btnShow.setOnClickListener {
            val rep=StoredExerciseRepository( (StoredExerciseDatabase.getDatabaseInstance(activity!!.applicationContext)).storedExerciseDao())
           rep.getListWords()
            var string=""
            for(ex in rep.getListWords()!!)
                string=string+ex.title+" "+ex.body+" "+ex.title
            Log.e("STORED_",string);
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_exercises, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SavedExercises.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SavedExercises().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}