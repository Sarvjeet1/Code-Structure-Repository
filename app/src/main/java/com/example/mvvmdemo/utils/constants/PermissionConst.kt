package com.example.mvvmdemo.utils.constants

import android.Manifest

/**
 * All data and constants regarding the Permissions are listed here
 */
interface PermissionConst {

    object REQUEST_CODE {
        internal const val CAMERA_IMAGE = 101
        internal const val GALLERY_IMAGE = 102
        internal const val CAMERA_AND_GALLERY_IMAGE = 103
        internal const val AGORA_STREAMING = 104
        internal const val READ_CONTACTS = 106
        internal const val SEND_SMS = 107
        internal const val LOCATION = 108
        internal const val READ_WRITE_STORAGE = 109
        internal const val VIDEO_CAMERA_AND_GALLERY_IMAGE = 110
    }

    object PERMISSION {
        private const val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        private const val CAMERA = Manifest.permission.CAMERA
        private const val RECORD_AUDIO = Manifest.permission.RECORD_AUDIO
        private const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
        private const val SEND_SMS = Manifest.permission.SEND_SMS
        private const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        private const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION

        internal val CAMERA_IMAGE_ARRAY =
            arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)

        internal val GALLERY_IMAGE_ARRAY = arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)

        internal val CAMERA_AND_GALLERY_IMAGE_ARRAY =
            arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)

        internal val VIDEO_CAMERA_GALLERY_IMAGE_ARRAY =
            arrayOf(CAMERA, RECORD_AUDIO, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)

        internal val READ_CONTACTS_ARRAY = arrayOf(READ_CONTACTS)
        internal val SEND_SMS_ARRAY = arrayOf(SEND_SMS)

        internal val LOCATION_ARRAY = arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)

    }

}
