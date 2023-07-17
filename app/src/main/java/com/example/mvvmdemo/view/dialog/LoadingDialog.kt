package com.example.mvvmdemo.view.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.mvvmdemo.R
import com.example.mvvmdemo.databinding.DialogLoadingBinding
import com.example.mvvmdemo.view.dialog.base.BaseDialog
import com.example.mvvmdemo.view.view.activities.base.BaseActivity

/**
 * This is the dialog class used for showing loading progress.
 *
 * @param baseActivity instance of BaseActivity
 * @param message Loading's message
 */
class LoadingDialog(
    private val baseActivity: BaseActivity,
    var message: String?
) : BaseDialog(
    baseActivity = baseActivity,
    styleResId = R.style.DialogCenterStyle
) {

    private lateinit var binding: DialogLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDialog()

        init()
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
        updateMessageText()
    }

    /**
     *  Here updating the message text view with data
     */
    private fun updateMessageText() {
        binding.tvMsg.text = message
    }

}
