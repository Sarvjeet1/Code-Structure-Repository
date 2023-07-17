package com.example.mvvmdemo.utils.listeners

import com.example.mvvmdemo.utils.enums.DialogEventType

/**
 * Interface definition for a callback to be invoked when a user performs action in Dialog.
 */
interface DialogListener {

    /**
     * Called when an dialog's event view type has been clicked
     *
     * @param dialogEventType event type of the dialog which has been clicked
     * @param requestCode
     * @param model
     */
    fun onDialogEventListener(dialogEventType: DialogEventType, requestCode: Int, model: Any?)

}