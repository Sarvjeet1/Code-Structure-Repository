package com.example.mvvmdemo.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.mvvmdemo.R
import com.example.mvvmdemo.utils.constants.AppConst
import com.example.mvvmdemo.view.view.activities.base.BaseActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        redirectToHomeScreen()
    }

    /**
     * Method invoke to redirect to Main Activity
     */
    private fun redirectToHomeScreen() {

        Handler(mainLooper).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                animationLeftToRight()
                finish()
        }, AppConst.NUMBER.FIVE.toLong())
    }
}