package com.example.mvvmdemo.utils.constants

/**
 * The constants which are used in App are listed here
 */
interface AppConst {

    object KEY {
        const val TAG_FROM = "tagFrom"
        const val FRAGMENT_NAME = "fragmentName"

        const val EMAIL_ID = "emailId"
        const val OTP = "otp"

        const val TYPE: String = "type"
        const val BODY = "body"
        const val TITLE = "title"

        const val WEBVIEW_TYPE = "webviewType"
        const val PDF_URL = "pdfUrl"
        const val ADDRESS = "ADDRESS"
    }

    object NUMBER {
        const val MINUS_ONE = -1
        const val ZERO = 0
        const val ONE = 1
        const val TWO = 2
        const val THREE = 3
        const val FOUR = 4
        const val FIVE = 5
        const val SIX = 6
        const val SEVEN = 7
        const val EIGHT = 8
        const val NINE = 9
        const val TEN = 10
        const val ELEVEN = 11
        const val TWELVE = 12
        const val THIRTEEN = 13
        const val FOURTEEN = 14
        const val FIFTEEN = 15
        const val SIXTEEN = 16
        const val SEVENTEEN = 17
        const val EIGHTEEN = 18
        const val NINTEEN = 19
        const val TWENTY = 20
        const val TWENTY_ONE = 21
        const val TWENTY_TWO = 22
        const val TWENTY_THREE = 23
        const val TWENTY_FOUR = 24
        const val TWENTY_FIVE = 25
        const val TWENTY_SIX = 26
        const val FIFTY_NINE = 59

        const val HUNDRED = 100
        const val TWO_HUNDRED = 2 * HUNDRED
        const val TWO_HUNDRED_FIFTY_FIVE = 255

        const val THOUSAND = 1000
    }

    object DURATION {
        const val TIME = 300
        const val ZERO: Long = 0
        const val ONE_SECOND: Long = 1000
        const val TWENTY_SECOND: Long = 20

        const val ONE_MINUTE: Long = 60 * ONE_SECOND
        const val FIVE_MINUTE: Long = 5 * ONE_MINUTE
    }

    object DEFAULT_VALUE {
        const val APP_USER_TYPE = "home-owner"
        const val WEBVIEW_MIME_TYPE = "text/html"
        const val WEBVIEW_ENCODING = "UTF-8"

        const val VISIBLE_THRESHOLD = NUMBER.ONE
        const val RESEND_OTP_DURATION: Long = DateTimeConst.DURATION_IN_SECONDS.TWO_MINUTE

    }

    object CHAR {
        const val SPACE = ' '
        const val FORWARD_SLASH = '/'
        const val COMMA = ','
        const val COLON = ':'
        const val UNDER_SCORE = '_'
        const val DASH = '-'
    }

    object VALUE {
        const val TRUE = NUMBER.ONE
        const val FALSE = NUMBER.ZERO

        const val AM = "AM"
        const val PM = "PM"
    }

    object REGEX {
        const val EMAIL = "^\\w+@\\w+\\..{2,3}(.{2,3})?\$"
        const val PASSWORD =
            "^(?=.*?\\p{Lu})(?=.*?\\p{Ll})(?=.*?\\d)" + "(?=.*?[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).*$"
    }

}
