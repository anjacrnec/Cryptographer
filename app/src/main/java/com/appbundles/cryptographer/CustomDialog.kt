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
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.DialogFragment



class CustomDialog():DialogFragment(){

    private lateinit var title: String
    private lateinit var body: String
    private  var optionOne: String?=null
    private var optionTwo: String?=null
    private  var optionThree: String?=null
    private var check:String?=null
    private var icon:Int?=null

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
        fun onDownloadSave(){}
        fun onDownloadExerciseYes(includeSave:Boolean){}
        fun onDownloadExerciseNever(){}
        fun onCancelDialog(){}

    }

    companion object{

        const val DIALOG_DOWNLOAD_EXERCISE= 1
        const val DIALOG_DOWNLOAD_SAVE = 2
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


        fun newInstance(title: String, body: String, optionOne: String, optionTwo: String):CustomDialog{
            val args = Bundle()
            args.putString(DIALOG_TITLE, title)
            args.putString(DIALOG_BODY, body)
            args.putString(DIALOG_OPTION_ONE, optionOne)
            args.putString(DIALOG_OPTION_TWO, optionTwo)
            val fragment = CustomDialog()
            fragment.arguments = args
            args.putInt(DIALOG_TYPE, DIALOG_DOWNLOADING)
            return fragment
        }

        fun newInstance(title: String, body: String, optionOne: String, optionTwo: String, icon:Int):CustomDialog{
            val args = Bundle()
            args.putString(DIALOG_TITLE, title)
            args.putString(DIALOG_BODY, body)
            args.putString(DIALOG_OPTION_ONE, optionOne)
            args.putString(DIALOG_OPTION_TWO, optionTwo)
            args.putInt(DIALOG_ICON,icon)
            val fragment = CustomDialog()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(title: String, body: String, check:String?, optionOne: String, optionTwo: String, optionThree: String, icon:Int): CustomDialog {
            val args = Bundle()
            args.putString(DIALOG_TITLE, title)
            args.putString(DIALOG_BODY, body)
            args.putString(DIALOG_OPTION_ONE, optionOne)
            args.putString(DIALOG_OPTION_TWO, optionTwo)
            args.putString(DIALOG_OPTION_THREE, optionThree)
            args.putString(DIALOG_CB, check)
            args.putInt(DIALOG_ICON,icon)
            args.putInt(DIALOG_TYPE, DIALOG_DOWNLOAD_EXERCISE)
            val fragment = CustomDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(DIALOG_TITLE).toString()
        body = arguments?.getString(DIALOG_BODY).toString()
        optionOne = arguments?.getString(DIALOG_OPTION_ONE).toString()
        optionTwo = arguments?.getString(DIALOG_OPTION_TWO).toString()
        optionThree=arguments?.getString(DIALOG_OPTION_THREE).toString()
        check=arguments?.getString(DIALOG_CB)
        icon=arguments?.getInt(DIALOG_ICON)
        dialogType= arguments?.getInt(DIALOG_TYPE)!!

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_custom, null)

        dialogTitle=view.findViewById(R.id.dialog_title)
        dialogBody=view.findViewById(R.id.dialog_body)
        dialogOptionOne=view.findViewById(R.id.dialog_option_one)
        dialogOptionTwo=view.findViewById(R.id.dialog_option_two)
        dialogOptionThree=view.findViewById(R.id.dialog_option_three)
        dialogCheckbox=view.findViewById(R.id.dialog_checkbox)
        dialogIcon=view.findViewById(R.id.dialog_icon)
        dialogProgress=view.findViewById(R.id.dialog_progress)

        dialogTitle.text=title
        dialogBody.text=body

         dialogIcon.setImageResource(icon!!)

        if(check.isNullOrEmpty())
            dialogCheckbox.visibility=View.GONE
        else
            dialogCheckbox.text=check

        dialogOptionOne.text=optionOne
        dialogOptionTwo.text=optionTwo
        dialogOptionThree.text=optionThree

        when(dialogType){

            DIALOG_DOWNLOAD_EXERCISE->{
                dialogProgress.visibility=View.GONE
                dialogOptionOne.setOnClickListener { onClickListener?.onDownloadExerciseYes(dialogCheckbox.isChecked) }
                dialogOptionTwo.setOnClickListener { onClickListener?.onCancelDialog() }
                dialogOptionThree.setOnClickListener { onClickListener?.onDownloadExerciseNever() }
            }

            DIALOG_DOWNLOADING->{
                dialogIcon.visibility=View.GONE
                dialogBody.visibility=View.GONE
                dialogOptionOne.setOnClickListener { onClickListener?.onDownloadingHide() }
                dialogOptionTwo.setOnClickListener {  onClickListener?.onDownloadingCancel()}
                dialogOptionThree.visibility=View.GONE
            }
        }


        return view
    }

    fun setOptionOne(text: String?){
        Handler(Looper.getMainLooper()).postDelayed({
            run {
                if(text.isNullOrEmpty())
                    dialogOptionOne.visibility==View.GONE
                else
                    dialogOptionOne.text=text
            }
        }, 100)
    }

    fun setOptionTwo(text:String?){
            Handler(Looper.getMainLooper()).postDelayed({
                run {
                    if(text.isNullOrEmpty())
                        dialogOptionTwo.visibility==View.GONE
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