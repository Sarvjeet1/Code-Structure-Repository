package com.example.mvvmdemo.view.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.mvvmdemo.R
import com.example.mvvmdemo.databinding.DialogImageVideoPickerBinding
import com.example.mvvmdemo.databinding.DialogPositiveNegativeAlertBinding
import com.example.mvvmdemo.utils.enums.DialogEventType
import com.example.mvvmdemo.utils.helpers.AppHelper
import com.example.mvvmdemo.utils.listeners.DialogListener
import com.example.mvvmdemo.view.dialog.base.BaseDialog
import com.example.mvvmdemo.view.view.activities.base.BaseActivity

/**
 * This is the dialog class used for Image or video Picker Options.
 *
 * @param baseActivity instance of BaseActivity
 * @param requestCode
 * @param listener
 */
class ImageVideoPickerDialog(
    private val baseActivity: BaseActivity,
    var requestCode: Int,
    var listener: DialogListener
) : BaseDialog(
    baseActivity = baseActivity,
    styleResId = R.style.DialogBottomStyle
) {

    private lateinit var binding: DialogImageVideoPickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogImageVideoPickerBinding.inflate(layoutInflater)
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

    }

    /**
     * Setting listeners to views
     */
    private fun setListeners() {
        binding.apply {
            tvTakePhoto.setOnClickListener(clickListener)
            tvChoosePhoto.setOnClickListener(clickListener)
            tvTakeVideo.setOnClickListener(clickListener)
            tvChooseVideos.setOnClickListener(clickListener)
            tvCancel.setOnClickListener(clickListener)
        }
    }

    /**
     * Click event on views handles here
     */
    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        if (AppHelper.shouldProceedClick())
            when (view.id) {
                R.id.tvTakePhoto -> clickedTakePhoto()

                R.id.tvChoosePhoto -> clickedChoosePhotos()

                R.id.tvTakeVideo -> clickedTakeVideo()

                R.id.tvChooseVideos -> clickedChooseVideos()

                R.id.tvCancel -> clickedCancel()
            }
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
     *  This is called when user tap on ChoosePhotos view
     */
    private fun clickedChoosePhotos() {
        dismiss()

        listener.onDialogEventListener(
            dialogEventType = DialogEventType.CHOOSE_PHOTOS,
            requestCode = requestCode,
            model = null
        )
    }

    /**
     *  This is called when user tap on Take Video view
     */
    private fun clickedTakeVideo() {
        dismiss()

        listener.onDialogEventListener(
            dialogEventType = DialogEventType.TAKE_VIDEO,
            requestCode = requestCode,
            model = null
        )
    }

    /**
     *  This is called when user tap on ChooseVideos view
     */
    private fun clickedChooseVideos() {
        dismiss()

        listener.onDialogEventListener(
            dialogEventType = DialogEventType.CHOOSE_VIDEOS,
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
