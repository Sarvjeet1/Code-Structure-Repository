package com.example.mvvmdemo.utils.constants

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull

/**
 * All data and constants regarding the APIs are listed here
 */
interface ApiConst {

    object URL {
        internal const val BASE_URL = ""
        internal const val BASE_MEDIA_URL = ""
    }

    object RESPONSE_STATUS {
        const val SUCCESS="Success"
        const val ERROR="Error"
    }

    object DEFAULT {
        const val TIMEOUT_IN_MINUTE = 2L
        const val AUTHTOKEN_BEARER = "Bearer "
    }

    object MEDIA_TYPE {
        val JSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()
        val IMAGE: MediaType = "image/*".toMediaType()
        val IMAGE_JPEG: MediaType = "image/jpeg".toMediaType()
        val VIDEO_MP_FOUR: MediaType = "video/mp4".toMediaType()

        val MEDIA_TYPE_IMAGE: String = "image/*"
        val MEDIA_TYPE_VIDEO: String = "video/*"

        val MEDIA_TYPE_PDF: MediaType = "application/pdf".toMediaType()
    }

    object API {
        const val FETCH_APP_CONTENT = "/app-contents"

        const val HELP = ""
        const val ABOUT = ""
        const val PRIVACY_POLICY = ""
        const val TERMS_AND_CONDITIONS = ""

        const val LOGIN = ""
        const val LOGOUT = ""
        const val DELETE_ACCOUNT = ""
        const val REGISTER = ""

        const val FORGOT_PASWRD = ""
        const val VERIFY_OTP = ""
        const val RESET_PASWRD = ""

        fun  updateNotificationReadStatus(notificationId: String) = "/customer/read-notification/$notificationId"

    }

    object KEY {
        const val ACCEPT = "Accept"
        const val CONTENT_TYPE = "Content-Type"
        const val AUTHTOKEN = "Authorization"

        const val FILE = "file"
        const val IMAGE = "image"
        const val TYPE = "type"
        const val MEDIA = "media"
        const val KEY = "key"

    }

    object VALUE {
        const val ACCEPT = "application/json"
        const val CONTENT_TYPE = "application/json"

        const val DEVICE_TYPE = "android"

    }

    object REQUEST_CODE {

        const val LOGIN = "LOGIN"
        const val LOGOUT = "LOGOUT"
        const val DELETE_ACCOUNT = "DELETE_ACCOUNT"
        const val REGISTER = "REGISTER"

        const val FORGOT_PASWRD = "FORGOT_PASWRD"
        const val VERIFY_OTP = "VERIFY_OTP"
        const val RESET_PASWRD = "RESET_PASWRD"

        const val  GET_MOVIE = "get_movie_list"
        const val  GET_MOVIE_PAGINATION = "get_movie_pagination_list"
    }

}