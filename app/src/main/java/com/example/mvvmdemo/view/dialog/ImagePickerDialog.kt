package com.example.mvvmdemo.view.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.mvvmdemo.R
import com.example.mvvmdemo.databinding.DialogImagePickerBinding
import com.example.mvvmdemo.databinding.DialogPositiveNegativeAlertBinding
import com.example.mvvmdemo.utils.enums.DialogEventType
import com.example.mvvmdemo.utils.helpers.AppHelper
import com.example.mvvmdemo.utils.listeners.DialogListener
import com.example.mvvmdemo.view.dialog.base.BaseDialog
import com.example.mvvmdemo.view.view.activities.base.BaseActivity

/**
 * This is the dialog class used for Image Picker Options.
 *
 * @param baseActivity instance of BaseActivity
 * @param requestCode
 * @param shouldShowDelete
 * @param listener
 */
class ImagePickerDialog(
    private val baseActivity: BaseActivity,
    var requestCode: Int,
    var shouldShowDelete: Boolean,
    var listener: DialogListener
) : BaseDialog(
    baseActivity = baseActivity,
    styleResId = R.style.DialogBottomStyle
) {

    private lateinit var binding: DialogImagePickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogImagePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initDialog()

        init()
        setListeners()
    }

    /**
     * Configuring dialog's properties
     */
    private fun initDialog() {
        setCancelable(true)

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
        showHideDeleteOptionsUI(status = shouldShowDelete)
    }

    /**
     * Here visibility of Delete options views is set to visible/gone
     */
    private fun showHideDeleteOptionsUI(status: Boolean) {
        val visibleValue = if (status) View.VISIBLE else View.GONE

        binding.apply {
            tvDeletePhoto.visibility = visibleValue
            viewDeletePhoto.visibility = visibleValue
        }
    }

    /**
     * Setting listeners to views
     */
    private fun setListeners() {
        binding.apply {
            tvDeletePhoto.setOnClickListener(clickListener)
            tvTakePhoto.setOnClickListener(clickListener)
            tvChoosePhoto.setOnClickListener(clickListener)
            tvCancel.setOnClickListener(clickListener)
        }
    }

    /**
     * Click event on views handles here
     */
    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        if (AppHelper.shouldProceedClick())
            when (view.id) {
                R.id.tvDeletePhoto -> clickedDeletePhoto()

                R.id.tvTakePhoto -> clickedTakePhoto()

                R.id.tvChoosePhoto -> clickedChoosePhoto()

                R.id.tvCancel -> clickedCancel()
            }
    }

    /**
     *  This is called when user tap on Delete view
     */
    private fun clickedDeletePhoto() {
        dismiss()

        listener.onDialogEventListener(
            dialogEventType = DialogEventType.DELETE_PHOTO,
            requestCode = requestCode,
            model = null
        )
    }

    /**
     *  This is called when user tap on TakePhoto view
     */
    private fun clickedTakePhoto() {
        dismiss()

        listener.onDialogEventListener(
            dialogEventType = DialogEventType.TAKE_PHOTO,
            requestCode = requestCode,
            model = null
        )
    }

    /**
     *  This is called when user tap on ChoosePhoto view
     */
    private fun clickedChoosePhoto() {
        dismiss()

        listener.onDialogEventListener(
            dialogEventType = DialogEventType.CHOOSE_PHOTO,
            requestCode = requestCode,
            model = null
        )
    }

    /**
     *  This is called when user tap on Cancel view
     */
    private fun clickedCancel() {
        dismiss()
    }

}
