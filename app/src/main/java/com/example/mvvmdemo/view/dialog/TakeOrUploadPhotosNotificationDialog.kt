package com.example.mvvmdemo.view.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.mvvmdemo.R
import com.example.mvvmdemo.databinding.DialogTakeOrUploadNotificationBinding
import com.example.mvvmdemo.utils.enums.DialogEventType
import com.example.mvvmdemo.utils.helpers.AppHelper
import com.example.mvvmdemo.utils.listeners.DialogListener
import com.example.mvvmdemo.view.dialog.base.BaseDialog
import com.example.mvvmdemo.view.view.activities.base.BaseActivity

/**
 * This is the dialog class used for showing alert with only positive button.
 *
 * @param baseActivity instance of BaseActivity
 * @param requestCode
 * @param listener
 */
class TakeOrUploadPhotosNotificationDialog(
    private val baseActivity: BaseActivity,
    var requestCode: Int,
    var listener: DialogListener?
) : BaseDialog(
    baseActivity = baseActivity,
    styleResId = R.style.DialogCenterStyle
) {

    private lateinit var binding: DialogTakeOrUploadNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogTakeOrUploadNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDialog()

        init()
        setListeners()
    }

    /**
     * Configuring dialog's properties
     */
    private fun initDialog() {
        setCancelable(false)

        window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        baseActivity,
                        R.color.transparentBlack
                    )
                )
            )
        }
    }

    /**
     * Initializing the objects and data to initial state
     */
    private fun init() {
        updateDataInUI()
    }

    /**
     * Here updating views w.r.t. data
     */
    private fun updateDataInUI() {

    }

    /**
     * Setting listeners to views
     */
    private fun setListeners() {
        binding.apply {
            ivBack.setOnClickListener(clickListener)
            btnOkay.setOnClickListener(clickListener)
        }
    }

    /**
     * Click event on views handles here
     */
    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        if (AppHelper.shouldProceedClick())
            when (view.id) {
                R.id.ivBack -> clickedCross()

                R.id.btnOkay -> clickedCross()
            }
    }

    /**
     *  This is called when user tap on cross view
     */
    private fun clickedCross() {
        dismiss()

        listener?.onDialogEventListener(
            dialogEventType = DialogEventType.POSITIVE,
            requestCode = requestCode,
            model = null
        )
    }

}
