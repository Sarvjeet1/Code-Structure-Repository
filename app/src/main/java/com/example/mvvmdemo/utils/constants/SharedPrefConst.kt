package com.example.mvvmdemo.utils.constants

/**
 * The constants which are used regarding SharedPreferences are listed here
 */
interface SharedPrefConst {

    object PREF {
        const val LOGIN_CREDENTIAL = "prefLoginCredential"
    }

    object KEY {
        const val APP_CONTENT_MODEL = "appContentModel"

        const val AUTH_TOKEN = "token"
        const val USER_INFO_MODEL = "userInfoModel"

        const val REMEMBER_ME_MODEL = "rememberMeModel"
        const val STAY_SIGN_IN_STATUS = "staySignInStatus"
        const val AUTH_TOKEN_ERROR_MSG = "authTokenErrorMsg"

        const val USER_SETTINGS_MODEL = "userSettingsModel"
        const val IS_APP_LAUNCH_FIRST_TIME = "isAppLaunchFirstTime"

    }

}