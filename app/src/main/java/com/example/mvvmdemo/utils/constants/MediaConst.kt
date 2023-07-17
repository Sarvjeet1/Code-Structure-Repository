package com.example.mvvmdemo.utils.constants

/**
 * The constants which are used in App are listed here
 */
interface MediaConst {

    object REQUEST_CODE {
        const val CAMERA_IMAGE: Int = 1008
        const val GALLERY_IMAGE: Int = 1009
    }

    object KEY {
        const val EXTENSION_JPG: String = ".jpg"
        const val EXTENSION_MP4: String = ".mp4"
    }

    object DEFAULT_VALUE {
        const val MAX_IMAGE_SIZE_ORIGINAL = 1080
        const val MAX_IMAGE_SIZE_THUMBNAIL = 200
        const val MAX_IMAGE_SIZE_VIDEO_ORIGINAL = 1080
        const val MAX_IMAGE_SIZE_VIDEO_THUMBNAIL = 300

        const val IMAGE_COMPRESSION_QUALITY_ORIGINAL = 80 // in percentage
        const val IMAGE_COMPRESSION_QUALITY_THUMBNAIL = 70 // in percentage
        const val IMAGE_COMPRESSION_QUALITY_VIDEO_ORIGINAL = 80 // in percentage
    }

}
