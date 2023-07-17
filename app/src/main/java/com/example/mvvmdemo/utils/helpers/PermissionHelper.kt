package com.example.mvvmdemo.utils.helpers

import android.app.Activity
import com.example.mvvmdemo.utils.constants.PermissionConst
import com.example.mvvmdemo.utils.listeners.PermissionListener
import com.example.mvvmdemo.utils.manager.PermissionManager

/**
 * This is the helper class which contains all the methods or functions regarding Permissions.
 */
object PermissionHelper {

    internal fun goToAppSettings(activity: Activity) {
        PermissionManager.goToAppSettings(activity = activity)
    }

    internal fun checkAndRequestCameraImagePermissions(
        activity: Activity,
        listener: PermissionListener?
    ): Boolean =
        PermissionManager.checkAndRequestPermissions(
            activity = activity,
            permissions = PermissionConst.PERMISSION.CAMERA_IMAGE_ARRAY,
            requestCode = PermissionConst.REQUEST_CODE.CAMERA_IMAGE,
            listener = listener
        )

    internal fun checkAndRequestGalleryImagePermissions(
        activity: Activity,
        listener: PermissionListener?
    ): Boolean =
        PermissionManager.checkAndRequestPermissions(
            activity = activity,
            permissions = PermissionConst.PERMISSION.GALLERY_IMAGE_ARRAY,
            requestCode = PermissionConst.REQUEST_CODE.GALLERY_IMAGE,
            listener = listener
        )

    internal fun checkAndRequestReadWritePermissions(
        activity: Activity,
        listener: PermissionListener?
    ): Boolean =
        PermissionManager.checkAndRequestPermissions(
            activity = activity,
            permissions = PermissionConst.PERMISSION.GALLERY_IMAGE_ARRAY,
            requestCode = PermissionConst.REQUEST_CODE.READ_WRITE_STORAGE,
            listener = listener
        )

    internal fun checkAndRequestCameraAndGalleryImagePermissions(
        activity: Activity,
        listener: PermissionListener?
    ): Boolean =
        PermissionManager.checkAndRequestPermissions(
            activity = activity,
            permissions = PermissionConst.PERMISSION.CAMERA_AND_GALLERY_IMAGE_ARRAY,
            requestCode = PermissionConst.REQUEST_CODE.CAMERA_AND_GALLERY_IMAGE,
            listener = listener
        )

    internal fun checkAndRequestReadContactsPermissions(
        activity: Activity,
        listener: PermissionListener?
    ): Boolean =
        PermissionManager.checkAndRequestPermissions(
            activity = activity,
            permissions = PermissionConst.PERMISSION.READ_CONTACTS_ARRAY,
            requestCode = PermissionConst.REQUEST_CODE.READ_CONTACTS,
            listener = listener
        )

    internal fun checkAndRequestSendSmsPermissions(
        activity: Activity,
        listener: PermissionListener?
    ): Boolean =
        PermissionManager.checkAndRequestPermissions(
            activity = activity,
            permissions = PermissionConst.PERMISSION.SEND_SMS_ARRAY,
            requestCode = PermissionConst.REQUEST_CODE.SEND_SMS,
            listener = listener
        )

    internal fun checkAndRequestLocationPermissions(
        activity: Activity,
        listener: PermissionListener?
    ): Boolean =
        PermissionManager.checkAndRequestPermissions(
            activity = activity,
            permissions = PermissionConst.PERMISSION.LOCATION_ARRAY,
            requestCode = PermissionConst.REQUEST_CODE.LOCATION,
            listener = listener
        )

    internal fun hasImagePickerPermissions(activity: Activity): Boolean =
        null == PermissionConst.PERMISSION.CAMERA_AND_GALLERY_IMAGE_ARRAY.find { permission ->
            !PermissionManager.hasPermission(activity = activity, permission = permission)
        }

    internal fun hasImageVideoPickerPermissions(activity: Activity): Boolean =
        null == PermissionConst.PERMISSION.VIDEO_CAMERA_GALLERY_IMAGE_ARRAY.find { permission ->
            !PermissionManager.hasPermission(activity = activity, permission = permission)
        }

    internal fun hasStoragePermissions(activity: Activity): Boolean =
        null == PermissionConst.PERMISSION.GALLERY_IMAGE_ARRAY.find { permission ->
            !PermissionManager.hasPermission(activity = activity, permission = permission)
        }

    internal fun requestImagePickerPermissions(activity: Activity) {
        PermissionConst.PERMISSION.CAMERA_AND_GALLERY_IMAGE_ARRAY.find { permission ->
            PermissionManager.shouldShowRational(activity = activity, permission = permission)
        }

        PermissionManager.requestPermissions(
            activity = activity,
            permissions = PermissionConst.PERMISSION.CAMERA_AND_GALLERY_IMAGE_ARRAY,
            requestCode = PermissionConst.REQUEST_CODE.CAMERA_AND_GALLERY_IMAGE
        )
    }

    internal fun requestStoragePermissions(activity: Activity) {
        PermissionConst.PERMISSION.GALLERY_IMAGE_ARRAY.find { permission ->
            PermissionManager.shouldShowRational(activity = activity, permission = permission)
        }

        PermissionManager.requestPermissions(
            activity = activity,
            permissions = PermissionConst.PERMISSION.GALLERY_IMAGE_ARRAY,
            requestCode = PermissionConst.REQUEST_CODE.READ_WRITE_STORAGE
        )
    }

    internal fun requestImageVideoPickerPermissions(activity: Activity) {
        PermissionConst.PERMISSION.VIDEO_CAMERA_GALLERY_IMAGE_ARRAY.find { permission ->
            PermissionManager.shouldShowRational(activity = activity, permission = permission)
        }

        PermissionManager.requestPermissions(
            activity = activity,
            permissions = PermissionConst.PERMISSION.VIDEO_CAMERA_GALLERY_IMAGE_ARRAY,
            requestCode = PermissionConst.REQUEST_CODE.VIDEO_CAMERA_AND_GALLERY_IMAGE
        )
    }

    internal fun onRequestPermissionsResult(
        activity: Activity,
        requestCode: Int,
        grantResults: IntArray
    ) {
        val permissions: Array<String>? =
            when (requestCode) {
                PermissionConst.REQUEST_CODE.CAMERA_IMAGE ->
                    PermissionConst.PERMISSION.CAMERA_IMAGE_ARRAY

                PermissionConst.REQUEST_CODE.GALLERY_IMAGE ->
                    PermissionConst.PERMISSION.GALLERY_IMAGE_ARRAY

                PermissionConst.REQUEST_CODE.CAMERA_AND_GALLERY_IMAGE ->
                    PermissionConst.PERMISSION.CAMERA_AND_GALLERY_IMAGE_ARRAY

                PermissionConst.REQUEST_CODE.READ_CONTACTS ->
                    PermissionConst.PERMISSION.READ_CONTACTS_ARRAY

                PermissionConst.REQUEST_CODE.SEND_SMS ->
                    PermissionConst.PERMISSION.SEND_SMS_ARRAY

                PermissionConst.REQUEST_CODE.LOCATION ->
                    PermissionConst.PERMISSION.LOCATION_ARRAY

                PermissionConst.REQUEST_CODE.READ_WRITE_STORAGE ->
                    PermissionConst.PERMISSION.GALLERY_IMAGE_ARRAY

                else -> null
            }

        if (null != permissions)
            PermissionManager.onRequestPermissionsResult(
                activity = activity,
                requestCode = requestCode,
                grantResults = grantResults,
                permissions = permissions
            )
    }

}

