package com.example.mvvmdemo.utils.service

import android.app.NotificationManager
import com.example.mvvmdemo.R
import com.example.mvvmdemo.utils.constants.AppConst
import com.example.mvvmdemo.utils.helpers.GsonHelper
import com.example.mvvmdemo.utils.helpers.LogHelper
import com.example.mvvmdemo.utils.helpers.NotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService : FirebaseMessagingService() {
    private val TAG: String = javaClass.simpleName

    private var notificationManager: NotificationManager? = null

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        LogHelper.debug(
            tag = TAG,
            msg = "onMessageReceived0: remoteMessage.data- ${remoteMessage.data}"
        )

        LogHelper.debug(
            tag = TAG,
            msg = "onMessageReceived0: remoteMessage.notification- ${remoteMessage.notification}"
        )

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {

            fetchAndUpdateNotificationData(remoteMessage.data)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Here we are fetching the notification data and wrapping it into notificationResponseModel
     *
     * @param data Object representing the message received from Firebase Cloud Messaging.
     */
    private fun fetchAndUpdateNotificationData(data: Map<String, String>) {

//        val type = data[AppConst.KEY.TYPE]
//        val claimId = data[AppConst.KEY.MODULE_ID]
//        val assignmentId = if (data.containsKey(AppConst.KEY.ASSIGNMENT_ID)) data[AppConst.KEY.ASSIGNMENT_ID] else "0"
//        val title = data[AppConst.KEY.TITLE] ?: getString(R.string.blank_string)
//        var message = data[AppConst.KEY.BODY] ?: getString(R.string.blank_string)
//
//        LogHelper.debug(tag = TAG, msg = "type = $type")
//        LogHelper.debug(tag = TAG, msg = "claimId = $claimId")
//        LogHelper.debug(tag = TAG, msg = "assignmentId = $assignmentId")
//        LogHelper.debug(tag = TAG, msg = "title = $title")
//        LogHelper.debug(tag = TAG, msg = "message = $message")
//
//        if (!type.isNullOrBlank() && !claimId.isNullOrBlank()) {
//            var notificationType: NotificationType? = null
//
//            notificationType = NotificationType.valueOf(value = type)
//
//            val notificationResponseModel =
//                NotificationResponseModel().apply {
//                    this.type = notificationType
//                    this.claimId = claimId.toInt()
//                    this.assignmentId = assignmentId?.toInt()
//                    this.title = title
//                    this.body = message
//                }

//            LogHelper.debug(tag = TAG, msg = "notificationResponseModel = ${GsonHelper.convertJavaObjectToJsonString(model = notificationResponseModel)}")
//
//            NotificationHelper.getNotification()
//                ?.onNotificationReceived(
//                    type = notificationResponseModel?.type,
//                    message = notificationResponseModel?.body,
//                    data = notificationResponseModel
//                )
//        }
    }

//    /**
//     * Here updating the claimId in SharedPreferences
//     *
//     * @param notificationResponseModel
//     */
//    private fun callStoreClaimInfo(notificationResponseModel: NotificationResponseModel) {
//        notificationResponseModel.apply {
//            SharedPreferencesHelper.storeClaimId(claimId = claimId)
//            LogHelper.debug(tag = TAG, msg = "clickedSelectClaim: claimId- $claimId")
//        }
//    }

//    /**
//     * Called when message is received.
//     *
//     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
//     */
//    // [START receive_message]
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        var notificationModel: NotificationResponse? = null
//
//        // Check if message contains a data payload.
//        if (remoteMessage.data.isNotEmpty()) {
//            LogHelper.debug(
//                tag = TAG,
//                msg = "onMessageReceived: remoteMessage. data- ${remoteMessage.data}"
//            )
//
//            var jsonString: String? = null
//            try {
//                val jsonObject = JSONObject(remoteMessage.data as Map<*, *>)
//                jsonString = jsonObject.toString().replace("\\\\", "").replace("\"[", "[")
//                    .replace("]\"", "]".replace("\\", ""))
//
//                LogHelper.debug(
//                    tag = TAG,
//                    msg = "onMessageReceived: jsonString- $jsonString"
//                )
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//            if (!jsonString.isNullOrBlank()) {
//                notificationModel = GsonHelper.convertJsonStringToJavaObject(
//                    from = jsonString,
//                    to = NotificationResponse::class.java
//                ) as NotificationResponse?
//
//                NotificationHelper.getNotification()
//                    ?.onNotificationReceived(
//                        type = notificationModel?.module,
//                        message = notificationModel?.body,
//                        data = notificationModel
//                    )
//            }
//        }
//
//        // Also if you intend on generating your own notifications as a result of a received FCM
//        // message, here is where that should be initiated. See sendNotification method below.
//    }
//    // [END receive_message]

    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        LogHelper.debug(tag = TAG, msg = "onNewToken: token- $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token = token)
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
    private fun scheduleJob() {
        // [START dispatch_job]

//        val work = OneTimeWorkRequest.Builder(MyWorker::class.java)
//            .build()
//        buildWorkManager.getInstance().beginWith(work).enqueue()

        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        LogHelper.debug(tag = TAG, msg = "Short lived task is done.")
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        // Implement this method to send token to your app server.
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String) {
//        val intent = Intent(this, StartActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent = PendingIntent.getActivity(
//            this, 0,
//            intent,
//            PendingIntent.FLAG_ONE_SHOT
//        )
//
//        val channelId = getString(R.string.notifications_admin_channel_name)
//        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.drawable.ic_avera_logo)
//            .setContentTitle(getString(R.string.app_name))
//            .setContentText(messageBody)
//            .setAutoCancel(true)
//            .setSound(defaultSoundUri)
//            .setContentIntent(pendingIntent)
//
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                channelId,
//                "Channel human readable title",
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

}
