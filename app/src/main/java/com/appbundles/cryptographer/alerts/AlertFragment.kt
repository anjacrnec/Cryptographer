package com.appbundles.cryptographer.alerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appbundles.cryptographer.R
import com.example.bundles.BaseSplitFragment
import kotlinx.android.synthetic.main.fragment_status.*

class AlertFragment:BaseSplitFragment() {

    private var title: String? = null
    private var icon: Int? = null
    private var progress: Boolean? = null
    private var body: String? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateStatus()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(Companion.STATUS_TITLE)
            progress=it.getBoolean(STATUS_PROGESS)
            body=it.getString(STATUS_BODY)
            icon=it.getInt(STATUS_ICON)
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
        if(body!=null)
            status_desc.text=body
        status_title.text=title
        if(icon==null)
            status_icon.visibility=View.GONE
        else
        {
            status_icon.visibility=View.VISIBLE
            status_icon.setImageResource(icon!!)
        }
        if(progress!=null && progress==true)
            status_progress.visibility=View.VISIBLE
        else
            status_progress.visibility=View.GONE

    }

    fun updateStatus(title:String?, body:String?, icon:Int?, progress:Boolean){
        if(title!=null)
            status_title.text=title
        if(body!=null)
            status_desc.text=body
        if(icon==null)
            status_icon.visibility=View.GONE
        else {
            status_icon.visibility=View.VISIBLE
            status_icon.setImageResource(icon)
        }
        if(progress)
            status_progress.visibility=View.VISIBLE
        else
            status_progress.visibility=View.GONE
    }

    companion object {
        private const val STATUS_ICON="icon"
        private const val  STATUS_PROGESS="progress"
        private const val STATUS_BODY="body"
        private const val STATUS_TITLE="title"

        const val STATUS_FRAGMENT="status"

        @JvmStatic
        fun newInstance(status: String?, body:String?,progress:Boolean, icon:Int?) =
            AlertFragment().apply {
                arguments = Bundle().apply {
                    putString(Companion.STATUS_TITLE, status)
                    putString(STATUS_BODY, body)
                    putBoolean(STATUS_PROGESS, progress)
                    icon?.let { putInt(STATUS_ICON,icon) }

                }
            }

    }

}