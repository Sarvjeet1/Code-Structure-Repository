package com.example.mvvmdemo.view.dialog.base

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import com.example.mvvmdemo.view.view.activities.base.BaseActivity

/**
 * All the dialogs are using this BaseDialog class as parent class
 *
 * @param baseActivity instance of BaseActivity
 * @param styleResId style resource id of Dialog
 */
abstract class BaseDialog(
    private var baseActivity: BaseActivity,
    private var styleResId: Int
) : Dialog(baseActivity, styleResId) {
    protected val TAG: String = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
    }

}
