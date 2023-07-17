package com.example.mvvmdemo.utils.listeners

/**
 * Interface definition for a callback to be invoked when a API gives response.
 */
interface ApiListener {

    /**
     * Called when a API gives success response
     *
     * @param requestCode
     * @param data
     * @param message
     */
    fun onApiSuccess(requestCode: String, data: Any, message: String?)

    /**
     * Called when a API gives failure response
     *
     * @param requestCode
     * @param errors
     * @param message
     */
    fun onApiFailure(requestCode: String, errors: Any?, message: String?)

    /**
     * Called when a API gives token expire or account disabled response
     *
     * @param requestCode
     * @param message
     */
    fun onApiForceLogout(requestCode: String, message: String?)

}