package com.example.mvvmdemo.utils.listeners

/**
 * Interface definition for a callback to be invoked when a Firebase process gives response.
 */
interface FirebaseListener {

    /**
     * Called when a Firebase process gives success response
     *
     * @param requestCode
     * @param data
     */
    fun onFirebaseSuccess(requestCode: String, data: Any?)

    /**
     * Called when a Firebase process gives failure response
     *
     * @param requestCode
     * @param message
     */
    fun onFirebaseFailure(requestCode: String, message: String?)

}