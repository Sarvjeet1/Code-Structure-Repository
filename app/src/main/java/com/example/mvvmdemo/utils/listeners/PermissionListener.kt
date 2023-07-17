package com.example.mvvmdemo.utils.listeners

/**
 * Interface definition for a callback to be invoked when a user performs action in Permission.
 */
interface PermissionListener {

    /**
     * Called when Permission is granted.
     *
     * @param requestCode
     */
    fun onPermissionGranted(requestCode: Int)

    /**
     * Called when Permission is denied.
     *
     * @param requestCode
     */
    fun onPermissionDenied(requestCode: Int)

    /**
     * Called when Permission is denied with never ask again.
     *
     * @param requestCode
     */
    fun onPermissionDeniedWithNeverAskAgain(requestCode: Int)

}