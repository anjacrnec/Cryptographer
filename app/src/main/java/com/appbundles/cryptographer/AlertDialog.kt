package com.appbundles.cryptographer

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment



class AlertDialog():DialogFragment(){

    private  var title: String?=null
    private  var body: String?=null
    private  var optionOne: String?=null
    private var optionTwo: String?=null
    private  var optionThree: String?=null
    private var check:String?=null
    private var icon:Int?=null
    private var progress:Boolean?=null

    private lateinit var dialogTitle:TextView
    private lateinit var dialogBody:TextView
    private lateinit var dialogOptionOne:Button
    private lateinit var dialogOptionTwo:Button
    private lateinit var dialogOptionThree:Button
    private lateinit var dialogCheckbox: CheckBox
    private lateinit var dialogIcon: ImageView
    private lateinit var dialogProgress: ProgressBar
    private var dialogType:Int=R.drawable.ic_placeholder

    private var onClickListener: OnClickListener? = null

    interface OnClickListener {

        fun onDownloadingHide(){}
        fun onDownloadingCancel(){}
        fun onDownloadingFinish(){}


        fun onDownloadStorageYes(){}
        fun onDownloadStorageNo(){}

        fun onDownloadExercisesNo(){}
        fun onDownloadExerciseYes(includeSave:Boolean){}
        fun onDownloadExerciseNever(){}
    }

    companion object{

        const val DIALOG_DOWNLOAD_EXERCISE= 1
        const val DIALOG_DOWNLOAD_STORAGE = 2
        const val DIALOG_DOWNLOAD_TUTORIAL=3
        const val DIALOG_DOWNLOADING= 4

        private const val DIALOG_TITLE: String = "title"
        private const val DIALOG_BODY = "body"
        private const val DIALOG_OPTION_ONE = "one"
        private const val DIALOG_OPTION_TWO = "two"
        private const val DIALOG_OPTION_THREE = "three"
        private const val DIALOG_CB="cb"
        private const val DIALOG_ICON="icon"
        private const val DIALOG_TYPE="type"
        private const val DIALOG_PROGRESS="progress"

        fun newInstance(type:Int):AlertDialog?{
            when(type){
                DIALOG_DOWNLOAD_EXERCISE->
                    return  newInstance(
                       DIALOG_DOWNLOAD_EXERCISE,
                        "Title",
                        "In order to use this feature you need to...",
                        "Include exercises feature",
                        "Yes",
                        "No",
                        "Never",
                        R.drawable.ic_download,
                        false
                    )
            }
            return null
        }

        fun newInstance(type:Int,title: String?, body: String?, check:String?, optionOne: String?, optionTwo: String?, optionThree: String?, icon:Int?,progress:Boolean): AlertDialog {
            val args = Bundle()
            args.putString(DIALOG_TITLE, title)
            args.putString(DIALOG_BODY, body)
            args.putString(DIALOG_OPTION_ONE, optionOne)
            args.putString(DIALOG_OPTION_TWO, optionTwo)
            args.putString(DIALOG_OPTION_THREE, optionThree)
            args.putString(DIALOG_CB, check)
            if (icon != null) {
                args.putInt(DIALOG_ICON,icon)
            }
            args.putInt(DIALOG_TYPE, type)
            args.putBoolean(DIALOG_PROGRESS,progress)
            val fragment = AlertDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(DIALOG_TITLE)
        body = arguments?.getString(DIALOG_BODY)
        optionOne = arguments?.getString(DIALOG_OPTION_ONE)
        optionTwo = arguments?.getString(DIALOG_OPTION_TWO)
        optionThree=arguments?.getString(DIALOG_OPTION_THREE)
        check=arguments?.getString(DIALOG_CB)
        icon=arguments?.getInt(DIALOG_ICON)
        progress=arguments?.getBoolean(DIALOG_PROGRESS)
        dialogType= arguments?.getInt(DIALOG_TYPE)!!

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.alert_dialog, null)

        dialogTitle=view.findViewById(R.id.dialog_title)
        dialogBody=view.findViewById(R.id.dialog_body)
        dialogOptionOne=view.findViewById(R.id.dialog_option_one)
        dialogOptionTwo=view.findViewById(R.id.dialog_option_two)
        dialogOptionThree=view.findViewById(R.id.dialog_option_three)
        dialogCheckbox=view.findViewById(R.id.dialog_checkbox)
        dialogIcon=view.findViewById(R.id.dialog_icon)
        dialogProgress=view.findViewById(R.id.dialog_progress)

        if(!title.isNullOrEmpty())
            dialogTitle.text=title
        else
            dialogTitle.visibility=View.GONE

        if(!body.isNullOrEmpty())
            dialogBody.text=body
        else
            dialogBody.visibility=View.GONE

        if(icon!=null)
            dialogIcon.setImageResource(icon!!)
        else
            dialogIcon.visibility=View.VISIBLE

        if(!check.isNullOrEmpty())
            dialogCheckbox.text=check
        else
            dialogCheckbox.visibility=View.GONE

        if(!optionOne.isNullOrEmpty())
             dialogOptionOne.text=optionOne
        else
            dialogOptionOne.visibility=View.GONE

        if(!optionTwo.isNullOrEmpty())
            dialogOptionTwo.text=optionTwo
        else
            dialogOptionTwo.visibility=View.GONE

        if(!optionThree.isNullOrEmpty())
             dialogOptionThree.text=optionThree
        else
            dialogOptionThree.visibility=View.GONE

        if(progress!=null && progress==true)
            dialogProgress.visibility=View.VISIBLE
        else
            dialogProgress.visibility=View.GONE

        when(dialogType){

            DIALOG_DOWNLOAD_STORAGE->{
                dialogOptionOne.setOnClickListener { onClickListener?.onDownloadStorageYes() }
                dialogOptionTwo.setOnClickListener { onClickListener?.onDownloadStorageNo() }
            }

            DIALOG_DOWNLOAD_EXERCISE->{
                dialogOptionOne.setOnClickListener { onClickListener?.onDownloadExerciseYes(dialogCheckbox.isChecked) }
                dialogOptionTwo.setOnClickListener { onClickListener?.onDownloadExercisesNo() }
                dialogOptionThree.setOnClickListener { onClickListener?.onDownloadExerciseNever() }
            }

            DIALOG_DOWNLOADING->{
                dialogOptionOne.setOnClickListener { onClickListener?.onDownloadingHide() }
                dialogOptionTwo.setOnClickListener {  onClickListener?.onDownloadingCancel()}
                dialogOptionThree.setOnClickListener { onClickListener?.onDownloadingFinish() }
            }
        }


        return view
    }

    fun setOptionOne(text: String?){
        Handler(Looper.getMainLooper()).postDelayed({
            run {
                if(text.isNullOrEmpty())
                    dialogOptionOne.visibility=View.GONE
                else
                    dialogOptionOne.text=text
            }
        }, 100)
    }

    fun setOptionTwo(text:String?){
            Handler(Looper.getMainLooper()).postDelayed({
                run {
                    if(text.isNullOrEmpty())
                        dialogOptionTwo.visibility=View.GONE
                    else
                        dialogOptionTwo.text=text
                }
            }, 100)
    }

    fun setOptionThree(text: String?){
        Handler(Looper.getMainLooper()).postDelayed({
            run {
                if(text.isNullOrEmpty())
                    dialogOptionThree.visibility=View.GONE
                else {
                    dialogOptionThree.visibility=View.VISIBLE
                    dialogOptionThree.text = text
                }
            }
        }, 100)
    }

    fun setIcon(icon:Int){
        Handler(Looper.getMainLooper()).postDelayed({
            run {
                if(dialogIcon.visibility==View.GONE)
                    dialogIcon.visibility=View.VISIBLE
                if(dialogProgress.visibility==View.VISIBLE)
                    dialogProgress.visibility=View.GONE
                dialogIcon.setImageResource(icon)
            }
        }, 100)

    }


    fun setTitle(title:String){
        Handler(Looper.getMainLooper()).postDelayed({
            run {
                dialogTitle.text = title
            }
        }, 100)
    }

    override fun onResume() {
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        super.onResume()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onClickListener = activity as OnClickListener?
    }


}