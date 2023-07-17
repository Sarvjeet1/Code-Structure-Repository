package com.example.mvvmdemo.view.view.activities.base


import android.app.Activity
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.example.mvvmdemo.R
import com.example.mvvmdemo.utils.constants.AppConst
import com.example.mvvmdemo.utils.constants.DateTimeConst
import com.example.mvvmdemo.utils.constants.DialogConst
import com.example.mvvmdemo.utils.enums.DialogEventType
import com.example.mvvmdemo.utils.enums.NotificationType
import com.example.mvvmdemo.utils.helpers.AppHelper
import com.example.mvvmdemo.utils.helpers.DialogHelper
import com.example.mvvmdemo.utils.helpers.LogHelper
import com.example.mvvmdemo.utils.helpers.SharedPreferencesHelper
import com.example.mvvmdemo.utils.listeners.DialogListener
import com.example.mvvmdemo.utils.listeners.NotificationListener
import com.example.mvvmdemo.utils.receiver.NetworkConnectivityReceiver
import java.util.regex.Pattern

/**
 * All the activities are using this BaseActivity class as parent class
 */
abstract class BaseActivity : AppCompatActivity(), NetworkConnectivityReceiver.ConnectivityReceiverListener, DialogListener {

    private var TAG = javaClass.simpleName

    private val EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$"

    private var closeAppMilliseconds: Long = 0
    private var snackbarMessage: Snackbar? = null
    private var rootView: View? = null
    private var toastMessage: Toast? = null

    private var touchedView: View? = null

    private var fromActivityTags: ArrayList<String>? = null

    private var drawerLayout: DrawerLayout? = null
    private var drawerGravity: Int? = null

    private var flag: Boolean? = null

    private val networkConnectivityReceiver = NetworkConnectivityReceiver()


    override fun onCreate(savedInstanceState: Bundle?) {
        LogHelper.debug(tag = TAG, msg = "onCreate")
        super.onCreate(savedInstanceState)

    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        LogHelper.debug(tag = TAG, msg = "onPostCreate")
        super.onPostCreate(savedInstanceState)
    }

    override fun onStart() {
        LogHelper.debug(tag = TAG, msg = "onStart")
        super.onStart()
    }

    override fun onResume() {
        LogHelper.debug(tag = TAG, msg = "onResume")
        super.onResume()
    }

    override fun onPostResume() {
        LogHelper.debug(tag = TAG, msg = "onPostResume")
        super.onPostResume()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        LogHelper.debug(tag = TAG, msg = "onCreateOptionsMenu")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        LogHelper.debug(tag = TAG, msg = "onPrepareOptionsMenu")
        return super.onPrepareOptionsMenu(menu)
    }

    internal fun isActive(): Boolean {
        return null != baseContext && !isFinishing
    }

    /**
     * Show message as Toast
     *
     * @param message Text which will be shown in Toast
     */
    internal fun showToastMessage(message: String?) {
        if (!message.isNullOrBlank()) {
            cancelToastMessage()

            toastMessage = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
            toastMessage?.show()
        }
    }

    /**
     * Cancel Toast message
     */
    internal fun cancelToastMessage() {
        toastMessage?.cancel()
    }

    /**
     * Show message as Snackbar
     *
     * @param message Text which will be shown in Snackbar
     * @param isSuccess
     */
    private fun showSnackbarMessage(message: String?, isSuccess: Boolean) {
        if (!message.isNullOrBlank()) {
            closeSnackbarMessage()

            if (null == this.rootView)
                return

            snackbarMessage = Snackbar.make(this.rootView!!, message, Snackbar.LENGTH_LONG).apply {
                val bgColor =
                    if (isSuccess) R.color.grayDarkest
                    else R.color.orange

                view.apply {
                    setBackgroundColor(ContextCompat.getColor(this@BaseActivity, bgColor))

//                    layoutParams.apply {
//                        if (this is CoordinatorLayout.LayoutParams)
//                            gravity = Gravity.TOP
//                        else if (this is FrameLayout.LayoutParams)
//                            gravity = Gravity.TOP
//                    }
                }
            }
            snackbarMessage?.show()
        }
    }

    /**
     * Close/Dismiss Snackbar message
     */
    internal fun closeSnackbarMessage() {
        snackbarMessage?.dismiss()
    }

    /**
     * Method : animate screen from left to rigth
     */
    internal fun animationLeftToRight() {

        overridePendingTransition(R.anim.right_in, R.anim.right_out)
    }

    /**
     * Method : animate screen from rigth to left
     */
    internal fun animationRightToLeft() {

        this.overridePendingTransition(R.anim.left_in, R.anim.left_out)
        finish()
    }

    /**
     * Method : check email validation
     * @return Boolean
     * @param email String
     */
    internal fun validateEmail(email: String?): Boolean {

        val pattern = Pattern.compile(
            EMAIL_REGEX,
            Pattern.CASE_INSENSITIVE
        )
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    /**
     * This method is called to hide or show the password for edit text.
     *
     *@param etPaswrd editText to which transformationMethod is removed or set to hide or show paswrd respectively
     *@param ivPaswrdVisibility imageView on which hide or show status is shown
     */
    internal fun showHidePaswrdVisibilityForEditText(
        etPaswrd: EditText,
        ivPaswrdVisibility: ImageView
    ) {

        val status: Boolean = !ivPaswrdVisibility.isSelected
        etPaswrd.apply {
            transformationMethod = if (status) null else PasswordTransformationMethod()
            setSelection(length())
        }

        ivPaswrdVisibility.apply {
            isSelected = status
        }
    }

    /**
     * Hide the keyboard
     */
    internal fun hideKeyboard(view: View) {

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Here NavigationDrawer is being setup
     *
     * @param drawerLayout
     * @param drawerGravity
     * @param toolbar
     */
    protected fun setUpNavigationDrawer(
        drawerLayout: DrawerLayout,
        drawerGravity: Int,
        toolbar: Toolbar
    ) {
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.isDrawerIndicatorEnabled = false
//        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_white)//add this for custom icon

        toggle.toolbarNavigationClickListener =
            View.OnClickListener { if (AppHelper.shouldProceedClick()) toggleNavigationDrawer() }

        this.drawerLayout = drawerLayout
        this.drawerGravity = drawerGravity
    }

    /**
     * Here navigationDrawer is toggled w.r.t. open/close
     */
    private fun toggleNavigationDrawer() {
        openCloseNavigationDrawer(status = !isNavigationDrawerOpen())
    }

    /**
     * It gives the open status of navigation drawer
     *
     * @return navigation drawer's open status
     */
    protected fun isNavigationDrawerOpen(): Boolean =
        drawerLayout?.isDrawerVisible(drawerGravity!!) ?: false

    /**
     * Here navigationDrawer is opened/closed as per status
     *
     * @param status boolean value which is used for open/close status
     */
    protected fun openCloseNavigationDrawer(status: Boolean) {
        drawerLayout?.apply {
            if (null != drawerGravity)
                when (status) {
                    true -> openDrawer(drawerGravity!!)
                    false -> closeDrawer(drawerGravity!!)
                }
        }
    }

    /**
     * Here swipeRefreshLayout is being setup
     *
     * @param swipeRefresh
     */
    internal fun setUpSwipeRefreshLayout(swipeRefresh: SwipeRefreshLayout) {
        swipeRefresh.apply {
            setColorSchemeResources(R.color.swipeRefreshScheme)
            setProgressBackgroundColorSchemeResource(R.color.swipeRefreshBgScheme)
        }
    }

    /**
     * Handling notificationListener when receives notificationListener
     * Showing showHidePositiveAlertDialog dialog with ok option.
     */
    private val notificationListener: NotificationListener = object : NotificationListener {

        override fun onNotificationReceived(type: NotificationType?, message: String?, data: Any?) {
            LogHelper.debug(
                tag = TAG,
                msg = "onNotificationReceived: type- ${type.toString()}, $message- $message, $data- $data"
            )

            if (null != this@BaseActivity.baseContext) {
                if (AppHelper.isUserLoggedIn())
                    afterNotificationReceived(
                        notificationType = type,
                        message = message,
                        data = data
                    )
            }
        }
    }

    private fun afterNotificationReceived(
        notificationType: NotificationType?,
        message: String?,
        data: Any?
    ) {
        when (notificationType) {

//            NotificationType.JOB_COMPLETED -> showCommonNotification(message = message, requestCode = NotificationType.JOB_COMPLETED, data = data)

            else ->
                onUnknownNotificationReceived(message = message)
        }
    }

    private fun onUnknownNotificationReceived(message: String?) {
//        showPositiveAlertNotification(message = message, requestCode = NotificationType.UNKNOWN)
    }

    /**
     * This calls showHidePositiveAlertDialog to show notification
     *
     * @param message
     * @param requestCode
     */
    private fun showPositiveAlertNotification(message: String?, requestCode: Int) {
        if (null != this@BaseActivity.baseContext && !message.isNullOrEmpty()) {
            runOnUiThread {
                if (null != this@BaseActivity.baseContext)
                    DialogHelper.showPositiveAlertDialog(
                        baseActivity = this,
                        requestCode = requestCode,
                        forceLogout = true,
                        title = null,
                        message = message,
                        positiveButtonText = getString(R.string.ok),
                        listener = this@BaseActivity
                    )
            }
        }
    }

    /**
     * This calls showPositiveNegativeAlertDialog to show common notification
     *
     * @param message
     * @param requestCode
     * @param data
     */
    private fun showCommonNotification(message: String?, requestCode: Int, data: Any?) {
        if (null != this@BaseActivity.baseContext && !message.isNullOrEmpty()) {
            runOnUiThread {
                if (null != this@BaseActivity.baseContext)
                    DialogHelper.showPositiveNegativeAlertDialog(
                        baseActivity = this@BaseActivity,
                        requestCode = requestCode,
                        title = null,
                        message = message,
                        positiveButtonText = getString(R.string.view),
                        negativeButtonText = getString(R.string.close),
                        data = data,
                        listener = this@BaseActivity
                    )
            }
        }
    }

//    /**
//     * This calls HomeActivity while finishing the current activity
//     *
//     * @param isForward status to show animation forward or backward
//     */
//    protected fun goToHomeActivity(isForward: Boolean, moduleId: String?) {
//        val intent = Intent(this, HomeActivity::class.java)
//            .apply {
//                putExtra(AppConst.KEY.MODULE_ID, moduleId)
//            }
//
//        when (isForward) {
//            true -> fireIntentForwardWithFinish(intent = intent)
//
//            false -> fireIntentBackwardWithFinish(intent = intent)
//        }
//    }

    /**
     * Clear all notificationListener from tray when activity creates or resumes.
     */
    private fun clearNotifications() {
        val notificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }

//    /**
//     * Use for switching to next activity without finishing current activity
//     *
//     * @param intent it is used to start the activity
//     */
//    fun fireIntentForward(intent: Intent) {
//        intent.apply {
//            putExtra(AppConst.KEY.TAG_FROM, getListAndAddTagInFromActivityTags(TAG))
//        }
//
//        startActivity(intent)
//        overridePendingTransition(R.anim.right_in, R.anim.right_out)
//    }
//
//    /**
//     * Use for switching to next activity while finishing current activity
//     *
//     * @param intent it is used to start the activity
//     */
//    protected fun fireIntentForwardWithFinish(intent: Intent) {
//        intent.apply {
//            putExtra(AppConst.KEY.TAG_FROM, getListAndAddTagInFromActivityTags(TAG))
//        }
//
//        startActivity(intent)
//        overridePendingTransition(R.anim.right_in, R.anim.right_out)
//        finish()
//    }
//
//    /**
//     * Use for switching to previous activity while finishing current activity
//     *
//     * @param intent it is used to start the activity
//     */
//    protected fun fireIntentBackwardWithFinish(intent: Intent) {
//        intent.apply {
//            putExtra(AppConst.KEY.TAG_FROM, getListAndRemoveLastTagFromActivityTags())
//        }
//
//        startActivity(intent)
//        overridePendingTransition(R.anim.left_in, R.anim.left_out)
//        finish()
//    }
//
//    /**
//     * Use for finishing current activity
//     */
//    internal fun finishCurrentActivity() {
//        removeLastTagFromActivityTags()
//
//        finish()
//        overridePendingTransition(R.anim.left_in, R.anim.left_out)
//    }

    /**
     * Use for restarting the same activity while finishing the same current activity
     */
    protected fun restartActivity() {
        finish()
        startActivity(intent)
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
    }

//    /**
//     * Use for switching to next activity for result without finishing current activity
//     *
//     * @param intent it is used to start the activity
//     * @param requestCode
//     * @param slideAnim
//     */
//    protected fun fireIntentForwardForResult(
//        intent: Intent,
//        requestCode: Int,
//        slideAnim: Boolean
//    ) {
//        intent.apply {
//            putExtra(AppConst.KEY.TAG_FROM, getListAndAddTagInFromActivityTags(TAG))
//        }
//
//        startActivityForResult(intent, requestCode)
//
//        if (slideAnim)
//            overridePendingTransition(R.anim.right_in, R.anim.right_out)
//        else
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//    }

    /**
     * Use for finishing current activity for result OK
     *
     * @param intent
     * @param slideAnim
     */
    internal fun finishCurrentActivityForResultOk(intent: Intent, slideAnim: Boolean) {
        setResult(Activity.RESULT_OK, intent)
        finish()

        if (slideAnim)
            overridePendingTransition(R.anim.left_in, R.anim.left_out)
        else
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    /**
     * Use for finishing current activity for result CANCELED
     *
     * @param slideAnim
     */
    internal fun finishCurrentActivityForResultCanceled(slideAnim: Boolean) {
        finish()

        if (slideAnim)
            overridePendingTransition(R.anim.left_in, R.anim.left_out)
        else
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    /**
     * It adds fragment to the container without custom animation
     *
     * @param containerId layout id to which the fragment is added
     * @param fragment it will be added to container
     */
    internal fun addFragmentWithoutAnimation(containerId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                add(containerId, fragment, fragment.javaClass.simpleName)
                commitAllowingStateLoss()
            }
    }

    /**
     * It adds fragment to the container
     *
     * @param containerId layout id to which the fragment is added
     * @param fragment it will be added to container
     * @param isForward status to show animation forward or backward
     */
    internal fun addFragment(containerId: Int, fragment: Fragment, isForward: Boolean) {
        supportFragmentManager.beginTransaction()
            .apply {
                when (isForward) {
                    true -> setCustomAnimations(R.anim.right_in, R.anim.right_out)
                    false -> setCustomAnimations(R.anim.left_in, R.anim.left_out)
                }

                add(containerId, fragment, fragment.javaClass.simpleName)
                commitAllowingStateLoss()
            }
    }

    /**
     * It removes fragment from the container without custom animation
     *
     * @param fragment it will be removed from the container
     */
    internal fun removeFragmentWithoutAnimation(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                remove(fragment)
                commitAllowingStateLoss()
            }
    }

    /**
     * It removes fragment from the container
     *
     * @param fragment it will be removed from the container
     * @param isForward status to show animation forward or backward
     */
    internal fun removeFragment(fragment: Fragment, isForward: Boolean) {
        supportFragmentManager.beginTransaction()
            .apply {
                when (isForward) {
                    true -> setCustomAnimations(R.anim.right_in, R.anim.right_out)
                    false -> setCustomAnimations(R.anim.left_in, R.anim.left_out)
                }

                remove(fragment)
                commitAllowingStateLoss()
            }
    }

    /**
     * It replaces fragment in the container and shows the current fragment
     *
     * @param containerId layout id in which the fragment is replaced
     * @param fragment it will be replaced to container
     * @param isForward status to show animation forward or backward
     */
    internal fun replaceFragment(containerId: Int, fragment: Fragment, isForward: Boolean) {
        supportFragmentManager.beginTransaction()
            .apply {
                when (isForward) {
                    true -> setCustomAnimations(R.anim.right_in, R.anim.right_out)
                    false -> setCustomAnimations(R.anim.left_in, R.anim.left_out)
                }

                replace(containerId, fragment, fragment.javaClass.simpleName)
                commitAllowingStateLoss()
            }
    }

    /**
     * It replaces and shows the same fragment in the container by creating new instance
     *
     * @param containerId layout id in which the fragment is removed and added
     * @param oldFragment old fragment instance which will be removed from the container
     * @param newFragment new fragment instance which will be added to the container
     * @param isForward status to show animation forward or backward
     *
     * @return new fragment instance which is currently added in the container
     */
    internal fun replaceSameFragmentAndReturnNewInstance(
        containerId: Int,
        oldFragment: Fragment,
        newFragment: Fragment,
        isForward: Boolean
    ): Fragment {
        removeFragment(fragment = oldFragment, isForward = isForward)
        addFragment(containerId = containerId, fragment = newFragment, isForward = isForward)

        return newFragment
    }

    /**
     * It gives the current fragment attached in the container
     *
     * @param containerId layout id in which the fragment are added
     *
     * @return fragment which is currently added in the container
     */
    protected fun getActiveFragment(containerId: Int): Fragment? =
        supportFragmentManager.findFragmentById(containerId)

    /**
     * It gives the current fragment attached in the container
     *
     * @param message force logout message
     * @param listener dialog listener
     */
    internal fun forceLogout(message: String?, listener: DialogListener) {
//        SocketHelper.disconnect(isForcefully = true, reason = null)

        if (SharedPreferencesHelper.getStaySignInStatus()) {
//            SharedPreferencesHelper.storeAuthTokenErrorMessage(errorMessage = message)
//
//            if (isActive())
//                runOnUiThread {
//                    if (isActive())
//                        DialogHelper.showStaySignInDialog(
//                            baseActivity = this,
//                            title = null,
//                            message = message,
//                            listener = listener
//                        )
//                }
        } else {
            SharedPreferencesHelper.removeAllDetailsExceptRememberMeAndAppContent()

            if (isActive())
                runOnUiThread {
                    if (isActive())
                        DialogHelper.showPositiveAlertDialog(
                            baseActivity = this,
                            requestCode = DialogConst.REQUEST_CODE.FORCE_LOGOUT,
                            forceLogout = true,
                            title = null,
                            message = message,
                            positiveButtonText = getString(R.string.ok),
                            listener = listener
                        )
                }
        }
    }

//    /**
//     * It is called when user successfully logged into the App. Here user login response and rememberMe info is stored.
//     *
//     * @param userAuthModel userAuthModel which contains authtoken and userModel
//     * @param rememberMeModel rememberMeModel which contains rememberMeStatus with email and password
//     */
//    private fun storeInfoAfterLoginSuccess(
//        userAuthModel: UserAuthModel,
//        rememberMeModel: RememberMeModel
//    ) {
//
//        LogHelper.debug(TAG, " Flag Value : $flag")
//
//        SharedPreferencesHelper.removeAllDetails()
//
//        SharedPreferencesHelper.storeAuthToken(authToken = userAuthModel.token)
//        SharedPreferencesHelper.storeUserInfoModel(customerInfoModel = userAuthModel.customerInfoModel)
//
//        SharedPreferencesHelper.storeRememberMeModel(rememberMeModel = rememberMeModel)
//        SharedPreferencesHelper.storeAuthTokenErrorMessage(errorMessage = null)
//
//        SharedPreferencesHelper.storeFirstTimeAppLaunchStatus(isAppLaunchFirstTime = true)
//
////        logUserInCrashlytics()
//    }

    /**
     * It shows/hides progressbar visibility which is used as a child in layouts
     *
     * @param status it checks and sets the current visible status
     */
    private fun progressBarVisibility(progressView: View?, status: Boolean) {
        progressView?.visibility = when (status) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    /**
     * It shows/hides load more visibility which is used as a child in layouts
     *
     * @param status it checks and sets the current visible status
     */
    private fun loadMoreVisibility(layoutLoadMore: View?, status: Boolean) {
        layoutLoadMore?.visibility = when (status) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    /**
     * It shows/hides loading dialog
     *
     * @param status it checks and shows/hides the loading dialog
     * @param message Message to be shown in loading dialog
     */
    internal fun showHideLoadingDialog(status: Boolean, message: String?) {
        DialogHelper.showHideLoadingDialog(
            baseActivity = this,
            message = message,
            status = status
        )
    }

    /**
     * This callback will be called when there is change in network connection change.
     */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        AppHelper.updateNetworkConnectedStatus(status = isConnected)

//        if (!isConnected)
//            showErrorMessageView(getString(R.string.no_internet_connection), isForApi = false)
    }

    /**
     * It is called to check whether Internet is Connected or not
     */
    internal fun isInternetConnected(shouldShowToast: Boolean): Boolean {
        return if (AppHelper.isNetworkConnected()) {
            true
        } else {
//            if (shouldShowToast)
//                showErrorMessageView(
//                    message = getString(R.string.no_internet_connection),
//                    isForApi = false
//                )

            false
        }
    }

//    override fun onApiSuccess(requestCode: String, data: Any, message: String?) {
//        LogHelper.debug(tag = TAG, msg = "onApiSuccess: requestCode- $requestCode")
//        removeFromApiRequestCodesList(requestCode = requestCode)
//
//        when (requestCode) {
//            ApiConst.REQUEST_CODE.DASHBOARD_TABS_STATUS ->
//                onUpdateDashboardTabsStatusSuccess(data = data, msg = message)
//        }
//    }
//
//    override fun onApiFailure(requestCode: String, errors: Any?, message: String?) {
//        LogHelper.debug(tag = TAG, msg = "onApiFailure: requestCode- $requestCode")
//        removeFromApiRequestCodesList(requestCode = requestCode)
//
//        when (requestCode) {
//            ApiConst.REQUEST_CODE.DASHBOARD_TABS_STATUS ->
//                onUpdateDashboardTabsStatusFailure(msg = message)
//        }
//    }
//
//    override fun onApiForceLogout(requestCode: String, message: String?) {
//        LogHelper.debug(tag = TAG, msg = "onApiForceLogout: requestCode- $requestCode")
//        removeFromApiRequestCodesList(requestCode = requestCode)
//
//        forceLogout(message = message, listener = this)
//    }
//
    override fun onDialogEventListener(
    dialogEventType: DialogEventType,
    requestCode: Int,
    model: Any?
    ) {
        when (dialogEventType) {
            DialogEventType.POSITIVE -> onDialogPositiveEvent(
                requestCode = requestCode,
                model = model
            )

            DialogEventType.NEGATIVE -> onDialogNegativeEvent(
                requestCode = requestCode,
                model = model
            )

            else -> LogHelper.error(
                tag = TAG,
                msg = "onDialogEventListener: dialogEventType- INVALID $requestCode"
            )
        }
    }

    protected open fun onDialogPositiveEvent(requestCode: Int, model: Any?) {
        LogHelper.debug(tag = TAG, msg = "onDialogPositiveEvent: requestCode- $requestCode")

        when (requestCode) {
//            DialogConst.REQUEST_CODE.FORCE_LOGOUT -> callGoToStartActivityAfterLogout()

//            DialogConst.REQUEST_CODE.JOB_COMPLETED -> onNotificationClicked(notificationResponseModel = model as NotificationResponseModel)

        }
    }

    protected open fun onDialogNegativeEvent(requestCode: Int, model: Any?) {
        LogHelper.debug(
            tag = TAG,
            msg = "onDialogNegativeEvent: requestCode- $requestCode"
        )

        when (requestCode) {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LogHelper.debug(tag = TAG, msg = "onActivityResult")
//        intent.putExtra(
//            AppConst.KEY.TAG_FROM,
//            getListAndCheckAndRemoveLastTagFromActivityTags()
//        )

        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * It is used to enable/disable multi touch
     *
     * @return multi touch status
     */
    private fun enableMultiTouch(): Boolean = false

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.pointerCount > 1 && !enableMultiTouch())
            return true

        currentFocus?.apply {
            if (this is EditText
                && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE)
                && !javaClass.simpleName.startsWith("android.webkit.")
            ) {
                val scrcoords = IntArray(2)
                this.getLocationOnScreen(scrcoords)

                val x = ev.rawX + left - scrcoords[0]
                val y = ev.rawY + top - scrcoords[1]

                if (x < left || x > right || y < top || y > bottom)
                    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(window.decorView.applicationWindowToken, 0)
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    protected fun hideSoftKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(window.decorView.applicationWindowToken, 0)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    /**
     * It checks and call to close the app
     */
    internal fun callForCloseApp() =
        when {
            System.currentTimeMillis() - closeAppMilliseconds < DateTimeConst.DURATION_IN_MILLISECONDS.CLOSE_APP_DELAY -> closeApp()

            else -> {
                closeAppMilliseconds = System.currentTimeMillis()
                showToastMessage(
                    message = getString(R.string.close_app_confirmation)
                )
            }
        }

    /**
     * It closes the app
     */
    private fun closeApp() = callSuperOnBackPressed()

    protected fun callSuperOnBackPressed() =
        super.onBackPressed()

    override fun onBackPressed() {
    }

    override fun onPause() {
        LogHelper.debug(tag = TAG, msg = "onPause")
        super.onPause()

//        callUnregisterNetworkConnectivityReceiver()

//        closeSnackbarMessage()
    }

    override fun onStop() {
        LogHelper.debug(tag = TAG, msg = "onStop")
        super.onStop()

//        SocketHelper.unregisterCallbackListener(this)
//        unsubscribeSocketEvents()
    }

    override fun onDestroy() {
        LogHelper.debug(tag = TAG, msg = "onDestroy")
        destroyObjects()
        super.onDestroy()

        Runtime.getRuntime().gc()
    }

    private fun destroyObjects() {
        rootView = null

        DialogHelper.hideAllDialogs()
//        cancelAllPendingApiCalls()

//        MediaHelper.destroyObjects()
    }








}