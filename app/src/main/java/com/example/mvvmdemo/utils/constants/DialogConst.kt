package com.example.mvvmdemo.utils.constants

/**
 * All data and constants regarding the Dialogs are listed here
 */
interface DialogConst {

    object REQUEST_CODE {
        const val PERMISSION_DENIED = 1001
        const val PERMISSION_DENIED_WITH_NEVER_ASK_AGAIN = 1002

        const val NOTIFICATION = 1003

        const val FORCE_LOGOUT = 1004
        const val LOGOUT = 1005
        const val STAY_SIGN_IN = 1006

        const val IMAGE_PICKER = 1007
        const val DELETE = 1008

        const val CAMERA = 1009
        const val GALLERY = 1010
        const val FILE_MANAGER = 1011
        const val DATE_PICKER = 1038

    }

}