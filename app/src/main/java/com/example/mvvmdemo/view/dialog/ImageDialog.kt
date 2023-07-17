package com.example.mvvmdemo.view.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.mvvmdemo.R
import com.example.mvvmdemo.databinding.DialogImageBinding
import com.example.mvvmdemo.databinding.DialogPositiveNegativeAlertBinding
import com.example.mvvmdemo.utils.enums.ImageType
import com.example.mvvmdemo.utils.helpers.AppHelper
import com.example.mvvmdemo.utils.helpers.MediaHelper
import com.example.mvvmdemo.view.dialog.base.BaseDialog
import com.example.mvvmdemo.view.view.activities.base.BaseActivity

/**
 * This is the dialog class used for showing image with pinch zooming, panning, fling and double tap zoom.
 *
 * @param baseActivity instance of BaseActivity
 * @param imageUrl url of original image
 * @param imageThumbUrl url of thumb image
 * @param imageType
 */
class ImageDialog(
    private val baseActivity: BaseActivity,
    var imageUrl: String?,
    var imageThumbUrl: String?,
    var imageType: ImageType
) : BaseDialog(
    baseActivity = baseActivity,
    styleResId = R.style.DialogCenterStyle
) {

    private lateinit var binding: DialogImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogImageBinding.inflate(layoutInflater)
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
        binding.apply {
            MediaHelper.loadImage(
                context = baseActivity,
                imageView = ivImage as ImageView,
                tempUrl = imageThumbUrl,
                finalUrl = imageUrl,
                imageType = imageType
            )
        }
    }

    /**
     * Setting listeners to views
     */
    private fun setListeners() {
        binding.ivClose.setOnClickListener(clickListener)
    }

    /**
     * Click event on views handles here
     */
    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        if (AppHelper.shouldProceedClick())
            when (view.id) {
                R.id.ivClose -> clickedClose()
            }
    }

    /**
     *  This is called when user tap on Close
     */
    private fun clickedClose() {
        dismiss()
    }


}
