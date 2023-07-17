package com.example.mvvmdemo.utils.listeners

import com.example.mvvmdemo.utils.enums.NotificationType

interface NotificationListener {

    /**
     * Called when a notification is received
     *
     * @param type type of the notification
     * @param message
     * @param data
     */
    fun onNotificationReceived(type: NotificationType?, message: String?, data: Any?)

}