package com.example.mvvmdemo.utils.helpers

import com.example.mvvmdemo.utils.constants.FirebaseConst
import com.example.mvvmdemo.utils.listeners.FirebaseListener
import com.google.firebase.messaging.FirebaseMessaging

/**
 * This is the helper class which contains all the methods or functions regarding Firebase.
 */
object FirebaseHelper {
    private var TAG = javaClass.simpleName

    /**
     * Here connecting to Firebase and fetching the fcm token
     *
     * @param listener
     */
    internal fun fetchFcmToken(listener: FirebaseListener) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                LogHelper.error(TAG, "fetchFcmToken: ${task.exception}")

                listener.onFirebaseFailure(
                    requestCode = FirebaseConst.REQUEST_CODE.FETCH_FCM_TOKEN,
                    message = task.exception?.message
                )

                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val fcmToken = task.result

            if (fcmToken != null)
                listener.onFirebaseSuccess(
                    requestCode = FirebaseConst.REQUEST_CODE.FETCH_FCM_TOKEN,
                    data = task.result
                )
            else
                listener.onFirebaseFailure(
                    requestCode = FirebaseConst.REQUEST_CODE.FETCH_FCM_TOKEN,
                    message = task.exception?.message
                )
        }.addOnFailureListener { exception ->
            LogHelper.error(TAG, "fetchFcmToken: $exception")

            listener.onFirebaseFailure(
                requestCode = FirebaseConst.REQUEST_CODE.FETCH_FCM_TOKEN,
                message = exception.message
            )
        }
    }
}