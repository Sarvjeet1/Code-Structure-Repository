package com.example.mvvmdemo.utils.helpers

import com.example.mvvmdemo.utils.listeners.NotificationListener

object NotificationHelper {

    private var notification: NotificationListener? = null

    fun with(notificationListener: NotificationListener) {
        this.notification = notificationListener
    }

    fun getNotification(): NotificationListener? = notification
}