package com.appbundles.cryptographer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bundles.BaseSplitFragment
import kotlinx.android.synthetic.main.fragment_status.*

class StatusFragment:BaseSplitFragment() {

    var STATUS_TITLE="title"
    var STATUS_ICON="icon"
    var STATUS_PROGESS="progress"

    private var title: String? = null
    private var icon: Int? = null
    private var progress: Boolean? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateStatus()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
           title = it.getString(STATUS_TITLE)
            progress=it.getBoolean(STATUS_PROGESS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_status, container, false)
    }



    private fun updateStatus()
    {
        status_title.text=title
        if(icon==null)
            status_icon.visibility=View.GONE
        if(progress!=null && progress==true)
            status_progress.visibility=View.VISIBLE
    }

    fun updateStatus(title:String, icon:Int?, progress:Boolean ){
        status_title.text=title
        if(icon==null)
            status_icon.visibility=View.GONE
        if(progress!=null && progress==true)
            status_progress.visibility=View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance(status: String, progress:Boolean) =
            StatusFragment().apply {
                arguments = Bundle().apply {
                    putString(STATUS_TITLE, status)
                    putBoolean(STATUS_PROGESS, progress)
                }
            }
    }

}