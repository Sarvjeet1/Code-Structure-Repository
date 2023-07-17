package com.example.mvvmdemo.view.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.mvvmdemo.R
import com.example.mvvmdemo.databinding.DialogPositiveNegativeAlertBinding
import com.example.mvvmdemo.utils.enums.DialogEventType
import com.example.mvvmdemo.utils.helpers.AppHelper
import com.example.mvvmdemo.utils.listeners.DialogListener
import com.example.mvvmdemo.view.dialog.base.BaseDialog
import com.example.mvvmdemo.view.view.activities.base.BaseActivity

/**
 * This is the dialog class used for showing alert with both positive and negative buttons.
 *
 * @param baseActivity instance of BaseActivity
 * @param requestCode
 * @param title
 * @param message
 * @param positiveButtonText
 * @param negativeButtonText
 * @param data
 * @param listener
 */
class PositiveNegativeAlertDialog(
    private val baseActivity: BaseActivity,
    var requestCode: Int,
    var title: String?,
    var message: String?,
    var positiveButtonText: String,
    var negativeButtonText: String,
    var data: Any?,
    var listener: DialogListener
) : BaseDialog(
    baseActivity = baseActivity,
    styleResId = R.style.DialogCenterStyle
) {

    private lateinit var binding: DialogPositiveNegativeAlertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogPositiveNegativeAlertBinding.inflate(layoutInflater)
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
        showHideTitleView()
        updateDataInUI()
    }

    private fun showHideTitleView() {
        binding.tvTitle.visibility = if (title.isNullOrBlank()) View.GONE else View.VISIBLE
    }

    /**
     * Here updating views w.r.t. data
     */
    private fun updateDataInUI() {
        binding.apply {
            btnPositive.text = positiveButtonText
            btnNegative.text = negativeButtonText
            tvTitle.text = title
            tvMessage.text = message
        }
    }

    /**
     * Setting listeners to views
     */
    private fun setListeners() {
        binding.apply {
            btnPositive.setOnClickListener(clickListener)
            btnNegative.setOnClickListener(clickListener)
        }
    }

    /**
     * Click event on views handles here
     */
    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        if (AppHelper.shouldProceedClick())
            when (view.id) {
                R.id.btnPositive -> clickedPositiveButton()

                R.id.btnNegative -> clickedNegativeButton()
            }
    }

    /**
     *  This is called when user tap on Positive button
     */
    private fun clickedPositiveButton() {
        dismiss()

        listener.onDialogEventListener(
            dialogEventType = DialogEventType.POSITIVE,
            requestCode = requestCode,
            model = data
        )
    }

    /**
     *  This is called when user tap on Negative button
     */
    private fun clickedNegativeButton() {
        dismiss()

        listener.onDialogEventListener(
            dialogEventType = DialogEventType.NEGATIVE,
            requestCode = requestCode,
            model = data
        )
    }

}