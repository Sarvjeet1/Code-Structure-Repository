package com.example.mvvmdemo.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvvmdemo.R
import com.example.mvvmdemo.utils.listeners.FirebaseListener
import com.example.mvvmdemo.view.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sign_in.*

/**
 * This is the fragment class used in StartActivity.
 */
class SignInFragment : BaseFragment(), FirebaseListener, View.OnFocusChangeListener {

    private var fcmToken: String? = null
    private var rememberMeModel: RememberMeModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_sign_in, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        resetEditTexts()

        setListeners()
        init()
    }

    /**
     * Setting listeners to views
     */
    private fun setListeners() {
        ivPaswrdVisibility.setOnClickListener(clickListener)
        tvForgotPass.setOnClickListener(clickListener)
        btnSignIn.setOnClickListener(clickListener)
        etEmail.addTextChangedListener(emailTextWatcher)
    }

    /**
     * Initializing the objects and data to initial state
     */
    private fun init() {
        enableDisableViews(
            status = true,
            enableDisableType = EnableDisableType.ALL,
            message = null,
            swipeRefresh = null,
            layoutLoadMore = null,
            progressView = null
        )

        enableDisableHomeIcon(status = false)
        getStartActivity()?.setUpToolbar(title = null, showHeader = false)

        initPaswrdVisibilityForEditText(
            etPaswrd = etPaswrd,
            ivPaswrdVisibility = ivPaswrdVisibility
        )

        initEditTextFilters()
        initEditTextFocusChangeListener()

        initUI()
        updateDataInUI()
    }

    /**
     * Setting filters to editTexts
     */
    private fun initEditTextFilters() {
        AppHelper.emojiFilter.apply {
            etEmail.filters = etEmail.filters + this
            etPaswrd.filters = etPaswrd.filters + this
        }
    }

    /**
     * Setting FocusChangeListener to editTexts
     */
    private fun initEditTextFocusChangeListener() {
        etEmail.onFocusChangeListener = this
        etPaswrd.onFocusChangeListener = this
    }

    /**
     * Here initially setting views
     */
    private fun initUI() {
        view?.post {
            updateRememberMeCheck(status = false)
        }
    }

    /**
     * Click event on views handles here
     */
    private val clickListener: View.OnClickListener = View.OnClickListener { view ->
        if (AppHelper.shouldProceedClick())
            when (view.id) {
                R.id.ivPaswrdVisibility -> callShowHidePaswrdVisibilityForEditText(
                    etPaswrd = etPaswrd,
                    ivPaswrdVisibility = ivPaswrdVisibility
                )

                R.id.tvForgotPass -> clickedForgotPaswrd()

                R.id.btnSignIn -> clickedSignIn()

//                R.id.tvSignUp -> clickedSignUp()
            }
    }

    /**
     * Click event on views handles here
     */
    private val emailTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            if (etEmail.text!!.isNotBlank() && AppHelper.isValidEmail(
                    etEmail.text!!.trim().toString()
                )
            ) {
                ivEmail.setImageResource(R.drawable.ic_check_green)
            } else {
                ivEmail.setImageResource(R.drawable.ic_email)
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

    /**
     * Here updating views w.r.t. data
     */
    private fun updateDataInUI() {
        SharedPreferencesHelper.getRememberMeModel()?.apply {
            view?.post {
                if (rememberMeStatus) {
                    updateRememberMeCheck(status = true)
                    updateEmailEditText(emailId = email)
                    updatePasswordEditText(paswrd = paswrd)
                }
            }
        }
    }

    /**
     *  This is called when user tap on ForgotPaswrd TextView
     */
    private fun clickedForgotPaswrd() {
        callLoadForgotPaswrdFragment()
    }

    /**
     *  This is called when user tap on SignIn Button
     */
    private fun clickedSignIn() {
        LogHelper.debug(tag = TAG, msg = "clickedSignIn")
        if (!validateForm()) return

        if (isInternetConnected(shouldShowToast = true))
            callLoginProcess()
    }

    /**
     * Here validating the field entries entered by the user
     *
     * @return status for validation success/failure
     */
    private fun validateForm(): Boolean {
        var valid = true

        etEmail.error = when {
            etEmail.text!!.isBlank() -> {
                valid = false
                getString(R.string.required)
            }

            !AppHelper.isValidEmail(etEmail.text!!.trim().toString()) -> {
                valid = false
                getString(R.string.invalid_format)
            }

            else -> null
        }

        etPaswrd.error = when {
            etPaswrd.text!!.isBlank() -> {
                valid = false
                getString(R.string.required)
            }

            else -> null
        }

        return valid
    }

    private fun callLoginProcess() {
        enableDisableViews(
            status = false,
            enableDisableType = EnableDisableType.LOADING_DIALOG,
            message = null,
            swipeRefresh = null,
            layoutLoadMore = null,
            progressView = null
        )

        fetchFcmToken()
    }

    /**
     * Here connecting to firebase and fetching the fcm token
     */
    private fun fetchFcmToken() {
        LogHelper.debug(TAG, "fetchFcmToken")

        FirebaseHelper.fetchFcmToken(listener = this)
    }

    private fun onFetchFcmTokenSuccess(token: String) {
        fcmToken = token

        callLoginApi()
    }

    private fun onFetchFcmTokenFailure(msg: String?) {
        onLoginFailure(errors = null, msg = msg ?: getString(R.string.error_occurred))
    }

    /**
     * Here connecting to server and uses Login API
     */
    private fun callLoginApi() {
        rememberMeModel = RememberMeModel(
//            rememberMeStatus = cbRememberMe.isChecked,
            rememberMeStatus = false,
            email = etEmail.text!!.trim().toString(),
            paswrd = etPaswrd.text!!.trim().toString()
        )

        val loginModel = LoginModel(
            email = rememberMeModel!!.email!!,
            paswrd = rememberMeModel!!.paswrd!!,
            userType = AppHelper.getAppUserType(),
            deviceType = ApiConst.VALUE.DEVICE_TYPE,
            deviceToken = fcmToken!!
        )

        addToApiRequestCodesList(requestCode = ApiConst.REQUEST_CODE.LOGIN)

        if (activity == null) {
            onLoginFailure(errors = null, msg = getString(R.string.error_occurred_please_try_again))
            return
        }

        ApiHelper.hitApiToLoginUser(
            context = activity!!,
            requestCode = ApiConst.REQUEST_CODE.LOGIN,
            loginModel = loginModel,
            listener = this
        )
    }

    /**
     * It is called when successful response or data retrieved from Login API.
     *
     * @param data Data in String format retrieved from the API response
     * @param msg Success message
     */
    private fun onLoginSuccess(data: Any?, msg: String?) {
        if (isActive()) {
            val userAuthModel: UserAuthModel? =
                GsonHelper.convertJsonStringToJavaObject(
                    data,
                    UserAuthModel::class.java
                ) as UserAuthModel?

            if (null != userAuthModel) {
                if (isActive())
                    activity?.runOnUiThread {
                        if (isActive()) {
                            afterLoginSuccess(
                                userAuthModel = userAuthModel,
                                rememberMeModel = rememberMeModel!!
                            )

                            enableDisableViews(
                                status = true,
                                enableDisableType = EnableDisableType.ALL,
                                message = null,
                                swipeRefresh = null,
                                layoutLoadMore = null,
                                progressView = null
                            )

                            SharedPreferencesHelper.storeClaimId(claimId = userAuthModel.claimId)
                            LogHelper.debug(tag = TAG, msg = "clickedSelectUser: claimId- ${userAuthModel.claimId}")

                            callGoToHomeActivity()
                        }
                    }
            } else {
                onLoginFailure(errors = null, msg = getString(R.string.error_occurred))
            }
        }
    }

    /**
     * It is called when error occurred in getting successful response or data from Login API.
     *
     * @param errors
     * @param msg Error message
     */
    private fun onLoginFailure(errors: Any?, msg: String?) {
        if (isActive()) {
            val loginErrorModel: LoginErrorModel? =
                if (null != errors)
                    GsonHelper.convertJsonStringToJavaObject(
                        from = errors,
                        to = LoginErrorModel::class.java
                    ) as LoginErrorModel?
                else
                    null

            if (isActive())
                activity?.runOnUiThread {
                    if (isActive()) {
                        enableDisableViews(
                            status = true,
                            enableDisableType = EnableDisableType.ALL,
                            message = null,
                            swipeRefresh = null,
                            layoutLoadMore = null,
                            progressView = null
                        )

                        if (null != loginErrorModel)
                            updateErrorsOnEditTexts(loginErrorModel = loginErrorModel)
                        else
                            showErrorMessageView(message = msg, isForApi = true)
                    }
                }
        }
    }

    /**
     * Here updating Errors On EditTexts when a API gives failure response
     *
     * @param loginErrorModel
     */
    private fun updateErrorsOnEditTexts(loginErrorModel: LoginErrorModel) {
        etEmail.error = loginErrorModel.email?.get(AppConst.NUMBER.ZERO)
        etPaswrd.error = loginErrorModel.password?.get(AppConst.NUMBER.ZERO)
    }

    /**
     * Here updating the remember me checkBox view with data
     *
     * @param status checked status
     */
    private fun updateRememberMeCheck(status: Boolean) {
//        cbRememberMe.isChecked = status
    }

    /**
     * Here updating the Email id editText view with data
     *
     * @param emailId email address of the user for login
     */
    private fun updateEmailEditText(emailId: String?) {
        etEmail.setText(emailId)
    }

    /**
     * Here updating the Paswrd editText view with data
     *
     * @param paswrd paswrd of the user for login
     */
    private fun updatePasswordEditText(paswrd: String?) {
        etPaswrd.setText(paswrd)
    }

    /**
     * Here resetting the editTexts w.r.t. error, text and focus
     */
    private fun resetEditTexts() {
        view?.post {
            etEmail.apply {
                error = null
                text!!.clear()
                clearFocus()
            }

            etPaswrd.apply {
                error = null
                text!!.clear()
                clearFocus()
            }
        }
    }

    override fun onFocusChange(view: View, hasFocus: Boolean) {
        llEmail.isSelected = false
        llPaswrd.isSelected = false

        when (view.id) {
            R.id.etEmail -> llEmail.isSelected = true
            R.id.etPaswrd -> llPaswrd.isSelected = true

        }
    }

    /**
     *It replaces the current fragment with the ForgotPaswrdFragment
     */
    private fun callLoadForgotPaswrdFragment() =
        getStartActivity()?.loadForgotPaswrdFragment(isInit = false, isForward = true)

    /**
     * This calls HomeActivity while finishing the current activity
     */
    private fun callGoToHomeActivity() =
        getStartActivity()?.checkAndGoToHomeActivityAfterAuthentication(moduleId = null)

    /**
     * This calls ClaimActivity while finishing the current activity
     */
    private fun callGoToClaimActivity() =
        getStartActivity()?.checkAndGoToClaimActivityAfterAuthentication()

    private fun getStartActivity(): StartActivity? =
        activity as StartActivity?

    override fun onFirebaseSuccess(requestCode: String, data: Any?) {
        when (requestCode) {
            FirebaseConst.REQUEST_CODE.FETCH_FCM_TOKEN ->
                onFetchFcmTokenSuccess(token = data as String)
        }
    }

    override fun onFirebaseFailure(requestCode: String, message: String?) {
        when (requestCode) {
            FirebaseConst.REQUEST_CODE.FETCH_FCM_TOKEN ->
                onFetchFcmTokenFailure(msg = message)
        }
    }

    override fun onApiSuccess(requestCode: String, data: Any, message: String?) {
        super.onApiSuccess(requestCode = requestCode, data = data, message = message)

        when (requestCode) {
            ApiConst.REQUEST_CODE.LOGIN ->
                onLoginSuccess(data = data, msg = message)
        }
    }

    override fun onApiFailure(requestCode: String, errors: Any?, message: String?) {
        super.onApiFailure(requestCode = requestCode, errors = errors, message = message)

        when (requestCode) {
            ApiConst.REQUEST_CODE.LOGIN ->
                onLoginFailure(errors = errors, msg = message)
        }
    }

    override fun onApiForceLogout(requestCode: String, message: String?) {
        LogHelper.debug(tag = TAG, msg = "onApiForceLogout: requestCode- $requestCode")
        removeFromApiRequestCodesList(requestCode = requestCode)

        onLoginFailure(errors = null, msg = message)
    }

    override fun enableDisableViews(
        status: Boolean,
        enableDisableType: EnableDisableType?,
        message: String?,
        swipeRefresh: SwipeRefreshLayout?,
        layoutLoadMore: View?,
        progressView: View?
    ) {

        super.enableDisableViews(
            status = status,
            enableDisableType = enableDisableType,
            message = message,
            swipeRefresh = swipeRefresh,
            layoutLoadMore = layoutLoadMore,
            progressView = progressView
        )

        val visibleValue = if (status) View.VISIBLE else View.GONE

    }

    override fun onDestroyView() {
        destroyObjects()

        super.onDestroyView()
    }

    private fun destroyObjects() {

    }
}