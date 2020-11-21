package com.appbundles.tutorial

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appbundles.cryptographer.main.MainActivity
import com.appbundles.cryptographer.Storage
import kotlinx.android.synthetic.main.fragment_tutorial.*


class TutorialFragment : Fragment() {

    private val INSTRUCTIONS_FIRST="instructions 1"
    private val INSTRUCTIONS_SECOND="instructions 2"
    private val INSTRUCTIONS_THIRD="instructions 3"
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadInstructions(R.raw.tutorial_instructions_1)

        tutorial_skip_btn.setOnClickListener {
            navigateToMain()
        }

        tutorial_next_btn.setOnClickListener {
            loadNextInstructions()
        }

        tutorial_previous_btn.setOnClickListener {
            loadPrevInstructions()
        }

        tutorial_skip_checkbox.setOnCheckedChangeListener { _, isChecked ->
            Storage.setUninstallTutorial(activity!!.applicationContext,isChecked)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tutorial, container, false)
    }

    private fun loadNextInstructions(){
        when(tutorial_image.tag.toString()){
           INSTRUCTIONS_FIRST-> {
               loadInstructions(R.raw.tutorial_instructions_2)
               tutorial_previous_btn.visibility=View.VISIBLE
           }
           INSTRUCTIONS_SECOND-> loadInstructions(R.raw.tutorial_instrcutions_3)
           INSTRUCTIONS_THIRD->{
                navigateToMain()
           }
       }
    }

    private fun loadPrevInstructions(){
        when(tutorial_image.tag.toString()){
            INSTRUCTIONS_SECOND-> {
                loadInstructions(R.raw.tutorial_instructions_1)
                tutorial_previous_btn.visibility=View.GONE
            }
            INSTRUCTIONS_THIRD-> loadInstructions(R.raw.tutorial_instructions_2)
        }
    }

    private fun loadInstructions(gif:Int) {
        ImageUtility.loadImage(gif,tutorial_image)
        when (gif) {
            R.raw.tutorial_instructions_1 -> tutorial_image.tag = INSTRUCTIONS_FIRST
            R.raw.tutorial_instructions_2 -> tutorial_image.tag = INSTRUCTIONS_SECOND
            R.raw.tutorial_instrcutions_3 -> tutorial_image.tag = INSTRUCTIONS_THIRD
        }
    }

    private fun navigateToMain(){
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        Storage.setSkipTutorial(activity!!.applicationContext,true)
        activity?.finish()
    }


}