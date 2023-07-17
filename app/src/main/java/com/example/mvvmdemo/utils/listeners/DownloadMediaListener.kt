package com.example.mvvmdemo.utils.listeners

/**
 * Interface definition for a callback to be invoked when a download media gives response.
 */
interface DownloadMediaListener {

    /**
     * Called when a download media gives success response
     *
     * @param downloadedMediaPath
     */
    fun onDownloadMediaSuccess(downloadedMediaPath: String)

    /**
     * Called when a download media gives failure response
     */
    fun onDownloadMediaFailure()

}