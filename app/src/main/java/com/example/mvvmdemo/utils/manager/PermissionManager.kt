package com.example.mvvmdemo.utils.manager

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.preference.PreferenceManager
import android.provider.Settings
import com.example.mvvmdemo.utils.listeners.PermissionListener


object PermissionManager {

    private var listener: PermissionListener? = null

    private fun setPermissionListener(listener: PermissionListener?) {
        this.listener = listener
    }

    private fun useRunTimePermissions(): Boolean =
        Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1

    public fun hasPermission(activity: Activity, permission: String): Boolean =
        if (useRunTimePermissions())
            activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        else true

    private fun hasPermissions(activity: Activity, permissions: Array<String>): Boolean =
        if (useRunTimePermissions())
            !permissions.filter { permission ->
                !hasPermission(activity = activity, permission = permission)
            }.any()
        else true

    internal fun requestPermissions(
        activity: Activity,
        permissions: Array<String>,
        requestCode: Int
    ) {
        if (useRunTimePermissions())
            activity.requestPermissions(permissions, requestCode)
    }

    internal fun shouldShowRational(activity: Activity, permission: String): Boolean {
        return if (useRunTimePermissions())
            activity.shouldShowRequestPermissionRationale(permission)
        else false
    }

    internal fun shouldAskForPermission(activity: Activity, permission: String): Boolean {
        return if (useRunTimePermissions())
            !hasPermission(activity, permission) &&
                    (!hasAskedForPermission(activity, permission) ||
                            shouldShowRational(activity, permission))
        else false
    }

    private fun shouldAskForPermissions(activity: Activity, permissions: Array<String>): Boolean =
        permissions.filter { permission ->
            shouldAskForPermission(activity = activity, permission = permission)
        }.any()

    internal fun goToAppSettings(activity: Activity) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.packageName, null)
        ).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        activity.startActivity(intent)
    }

    internal fun checkAndRequestPermissions(
        activity: Activity,
        permissions: Array<String>,
        requestCode: Int,
        listener: PermissionListener?
    ): Boolean =
        if (hasPermissions(activity = activity, permissions = permissions))
            true
        else {
            callRequestPermissions(
                activity = activity,
                permissions = permissions,
                requestCode = requestCode,
                listener = listener
            )
            false
        }

    private fun callRequestPermissions(
        activity: Activity,
        permissions: Array<String>,
        requestCode: Int,
        listener: PermissionListener?
    ) {
        if (shouldAskForPermissions(activity = activity, permissions = permissions)) {
            setPermissionListener(listener = listener)

            markedPermissionsAsAsked(activity = activity, permissions = permissions)

            requestPermissions(
                activity = activity,
                permissions = permissions,
                requestCode = requestCode
            )
        } else {
            listener?.onPermissionDeniedWithNeverAskAgain(requestCode = requestCode)
            setPermissionListener(listener = null)
        }
    }

    internal fun onRequestPermissionsResult(
        activity: Activity,
        requestCode: Int,
        grantResults: IntArray,
        permissions: Array<String>
    ) {
        val isPermissionDenied =
            grantResults.filter { result -> result == PackageManager.PERMISSION_DENIED }.any()

        when {
            isPermissionDenied ->
                listener?.onPermissionDenied(requestCode = requestCode)

            hasPermissions(activity = activity, permissions = permissions) ->
                listener?.onPermissionGranted(requestCode = requestCode)

            else ->
                listener?.onPermissionDenied(requestCode = requestCode)
        }

        setPermissionListener(listener = null)
    }

    private fun hasAskedForPermission(
        activity: Activity?,
        permission: String?
    ): Boolean {
        return PreferenceManager
            .getDefaultSharedPreferences(activity)
            .getBoolean(permission, false)
    }

    private fun markedPermissionAsAsked(activity: Activity?, permission: String) {
        PreferenceManager
            .getDefaultSharedPreferences(activity)
            .edit()
            .putBoolean(permission, true)
            .apply()
    }

    private fun markedPermissionsAsAsked(activity: Activity?, permissions: Array<String>) {
        permissions.forEach { permission ->
            markedPermissionAsAsked(activity = activity, permission = permission)
        }
    }

}