package com.example.mvvmdemo.utils.helpers

import com.example.mvvmdemo.R
import com.example.mvvmdemo.application.AppController
import com.example.mvvmdemo.models.RememberMeModel
import com.example.mvvmdemo.models.response.AppContentModel
import com.example.mvvmdemo.models.response.TempResponseModel
import com.example.mvvmdemo.utils.constants.AppConst
import com.example.mvvmdemo.utils.constants.SharedPrefConst
import com.example.mvvmdemo.utils.manager.SharedPreferencesManager


/**
 * This is the helper class which contains all the methods or functions regarding SharedPreferences.
 */
object SharedPreferencesHelper {
    private var TAG = javaClass.simpleName

    /**
     * Here storing appContentModel from AppContent API Response in SharedPreferences
     *
     * @param appContentModel appContentModel which contains app content data
     */
    internal fun storeAppContentModel(appContentModel: AppContentModel) {
        LogHelper.debug(TAG, "storeAppContentModel")

        val value: String = GsonHelper.convertJavaObjectToJsonString(appContentModel)
        LogHelper.debug(TAG, "storeAppContentModel: value- $value")

        SharedPreferencesManager.with(context = AppController.getContext()!!)!!.edit()
            .putString(SharedPrefConst.KEY.APP_CONTENT_MODEL, value)
            .apply()
    }

    /**
     * It gives appContentModel details for app
     *
     * @return appContentModel
     */
    internal fun getAppContentModel(): AppContentModel? {
        val value: String? =
            SharedPreferencesManager.with(context = AppController.getContext()!!)!!
                .getString(SharedPrefConst.KEY.APP_CONTENT_MODEL, "")

        var appContentModel: AppContentModel? = null

        if (!value.isNullOrBlank())
            appContentModel =
                GsonHelper.convertJsonStringToJavaObject(
                    value,
                    AppContentModel::class.java
                ) as AppContentModel?

        return appContentModel
    }

    /**
     * Here storing authToken from login response of loggedIn user in SharedPreferences
     *
     * @param authToken authToken which contains authToken of loggedIn user
     */
    internal fun storeAuthToken(authToken: String) {
        LogHelper.debug(TAG, "storeAuthToken: authToken- $authToken")

        SharedPreferencesManager.with(context = AppController.getContext()!!)!!.edit()
            .putString(SharedPrefConst.KEY.AUTH_TOKEN, authToken)
            .apply()
    }

    /**
     * It gives authtoken of the current logged in user
     *
     * @return authtoken
     */
    internal fun getAuthtoken(): String {
        var authToken: String? =
            SharedPreferencesManager.with(context = AppController.getContext()!!)!!
                .getString(SharedPrefConst.KEY.AUTH_TOKEN, "")

        if (null == authToken)
            authToken = AppController.getContext()!!.getString(R.string.blank_string)

        return authToken
    }

    /**
     * Here storing userInfoModel from login response of loggedIn user in SharedPreferences
     *
     * @param customerInfoModel userInfoModel which contains user data
     */
    internal fun storeUserInfoModel(customerInfoModel: TempResponseModel) {
        LogHelper.debug(TAG, "storeUserInfoModel")

        val value: String = GsonHelper.convertJavaObjectToJsonString(customerInfoModel)
        LogHelper.debug(TAG, "storeUserInfoModel: value- $value")

        SharedPreferencesManager.with(context = AppController.getContext()!!)!!.edit()
            .putString(SharedPrefConst.KEY.USER_INFO_MODEL, value)
            .apply()
    }

    /**
     * It gives userInfoModel details of the current logged in user
     *
     * @return userInfoModel
     */
    internal fun getUserInfoModel(): TempResponseModel? {
        val value: String? =
            SharedPreferencesManager.with(context = AppController.getContext()!!)!!
                .getString(SharedPrefConst.KEY.USER_INFO_MODEL, "")

        var customerInfoModel: TempResponseModel? = null

        if (!value.isNullOrBlank())
            customerInfoModel =
                GsonHelper.convertJsonStringToJavaObject(
                    value,
                    TempResponseModel::class.java
                ) as TempResponseModel?

        return customerInfoModel
    }

    /**
     * It gives user id of the current logged in user
     *
     * @return user id
     */
    internal fun getUserId(): Int {
        var userId: Int = AppConst.NUMBER.ZERO
        val customerInfoModel: TempResponseModel? = getUserInfoModel()

        customerInfoModel?.id?.apply {
            userId = this
        }

        return userId
    }

    /**
     * Here storing rememberMe model of the current logged in user
     *
     *@param rememberMeModel rememberMeModel which contains rememberMeStatus with email and password
     */
    internal fun storeRememberMeModel(rememberMeModel: RememberMeModel) {
        LogHelper.debug(TAG, "storeRememberMeModel")
        val value: String = GsonHelper.convertJavaObjectToJsonString(rememberMeModel)
        LogHelper.debug(TAG, "storeRememberMeModel: value- $value")

        SharedPreferencesManager.with(context = AppController.getContext()!!)!!.edit()
            .putString(SharedPrefConst.KEY.REMEMBER_ME_MODEL, value)
            .apply()
    }

    /**
     * It gives rememberMe model of the current logged in user
     *
     * @return rememberMe model
     */
    internal fun getRememberMeModel(): RememberMeModel? {
        val value: String? =
            SharedPreferencesManager.with(context = AppController.getContext()!!)!!
                .getString(SharedPrefConst.KEY.REMEMBER_ME_MODEL, "")

        var rememberMeModel: RememberMeModel? = null

        if (!value.isNullOrBlank())
            rememberMeModel =
                GsonHelper.convertJsonStringToJavaObject(
                    value,
                    RememberMeModel::class.java
                ) as RememberMeModel?

        return rememberMeModel
    }

    /**
     * Here storing stay sign in status of the current logged in user
     *
     * @param staySignInStatus stay sign in status
     */
    internal fun storeStaySignInStatus(staySignInStatus: Boolean) {
        SharedPreferencesManager.with(context = AppController.getContext()!!)!!.edit()
            .putBoolean(SharedPrefConst.KEY.STAY_SIGN_IN_STATUS, staySignInStatus)
            .apply()
    }

    /**
     * It gives stay sign in status of the current logged in user
     *
     * @return stay sign in status
     */
    internal fun getStaySignInStatus(): Boolean {
        return SharedPreferencesManager.with(context = AppController.getContext()!!)!!
            .getBoolean(SharedPrefConst.KEY.STAY_SIGN_IN_STATUS, false)
    }

    /**
     * It gives status of the first time app launch
     *
     * @return first time app launch status
     */
    internal fun getFirstTimeAppLaunchStatus(): Boolean {
        return SharedPreferencesManager.with(context = AppController.getContext()!!)!!
            .getBoolean(SharedPrefConst.KEY.IS_APP_LAUNCH_FIRST_TIME, false)
    }

    /**
     * It gives privacyPolicyUrl from appContentModel details
     *
     * @return privacyPolicyUrl
     */
//    internal fun getPrivacyPolicyUrl(): String = getAppContentModel()!!.privacyPolicyUrl

    /**
     * It gives termsConditionsUrl from appContentModel details
     *
     * @return termsConditionsUrl
     */
//    internal fun getTermsAndConditionsUrl(): String = getAppContentModel()!!.termsConditionsUrl

    /**
     * Here removing user's all details from SharedPreference
     */
    internal fun removeAllDetails() =
        SharedPreferencesManager.with(context = AppController.getContext()!!)!!.edit().clear()
            .apply()

    /**
     * Here removing user's all details from SharedPreference except AppContentModel and RememberMeModel info
     */
    internal fun removeAllDetailsExceptRememberMeAndAppContent() {
        SharedPreferencesManager.with(context = AppController.getContext()!!)!!.apply {
            edit().apply {
                all.entries.filter { entry ->
                    entry.key != SharedPrefConst.KEY.REMEMBER_ME_MODEL
                            && entry.key != SharedPrefConst.KEY.APP_CONTENT_MODEL
                }.forEach { entry ->
                    remove(entry.key)
                }

                apply()
            }
        }
    }
}