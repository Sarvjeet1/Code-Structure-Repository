//package com.example.mvvmdemo.view.fragments.base
//
//import android.app.Fragment
//import android.content.Context
//import android.os.Bundle
//import android.util.Log
//import android.view.*
//import android.view.inputmethod.InputMethodManager
//import android.widget.FrameLayout
//import androidx.fragment.app.Fragment
//import com.example.mvvmdemo.R
//import com.example.mvvmdemo.utils.helpers.LogHelper
//import com.example.mvvmdemo.utils.listeners.DialogListener
//import com.example.mvvmdemo.view.view.activities.base.BaseActivity
//
//
//abstract class BaseFragment : Fragment(), DialogListener {
//
//    protected val TAG = BaseFragment::class.java.name
//
//    protected var isCurrentlyVisibleToUser: Boolean = false
//
//    override fun onAttach(context: Context) {
//        LogHelper.debug(tag = TAG, msg = "onAttach")
//        super.onAttach(context)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        LogHelper.debug(tag = TAG, msg = "onCreate")
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        LogHelper.debug(tag = TAG, msg = "onCreateView")
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        LogHelper.debug(tag = TAG, msg = "onViewCreated")
//        super.onViewCreated(view, savedInstanceState)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        LogHelper.debug(tag = TAG, msg = "onActivityCreated")
//        super.onActivityCreated(savedInstanceState)
//
////        hideKeyboardInitially()
//    }
//
//    override fun onStart() {
//        LogHelper.debug(tag = TAG, msg = "onStart")
//        super.onStart()
//    }
//
//    override fun onResume() {
//        LogHelper.debug(tag = TAG, msg = "onResume")
//        super.onResume()
//
//        hideKeyboardInitially()
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        LogHelper.debug(tag = TAG, msg = "onCreateOptionsMenu")
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        LogHelper.debug(tag = TAG, msg = "setUserVisibleHint")
//        super.setUserVisibleHint(isVisibleToUser)
//
//        isCurrentlyVisibleToUser = isVisibleToUser
//    }
//
//    protected fun isActive(): Boolean {
//        return activity != null && isAdded
//    }
//
//    protected fun getBaseActivity(): BaseActivity? =
//        activity as BaseActivity?
//
//    /**
//     * Here swipeRefreshLayout is being setup
//     *
//     * @param swipeRefresh
//     */
//    protected fun setUpSwipeRefreshLayout(swipeRefresh: SwipeRefreshLayout) =
//        getBaseActivity()?.setUpSwipeRefreshLayout(swipeRefresh = swipeRefresh)
//
//    /**
//     * Show Success message view
//     *
//     * @param message Text which will be shown in view
//     * @param shouldShowMessage Status to show Snackbar message view
//     */
//    internal fun showSuccessMessageView(message: String?) =
//        getBaseActivity()?.showSuccessMessageView(message = message)
//
//    /**
//     * It gives the current fragment attached in the container
//     *
//     * @param message force logout message
//     * @param listener dialog listener
//     */
//    internal fun forceLogout(message: String?, listener: DialogListener) =
//        getBaseActivity()?.forceLogout(message = message, listener = listener)
//
//
//    /**
//     * It adds fragment to the container with out custom animation
//     *
//     * @param containerId layout id to which the fragment is added
//     * @param fragment it will be added to container
//     */
//    protected open fun addFragmentWithoutAnimation(containerId: Int, fragment: Fragment) {
//        childFragmentManager.beginTransaction()
//            .apply {
//                add(containerId, fragment, fragment.javaClass.simpleName)
//                commitAllowingStateLoss()
//            }
//    }
//
//    /**
//     * It adds fragment to the container
//     *
//     * @param containerId layout id to which the fragment is added
//     * @param fragment it will be added to container
//     * @param isForward status to show animation forward or backward
//     */
//    protected fun addFragment(containerId: Int, fragment: Fragment, isForward: Boolean) {
//        childFragmentManager.beginTransaction()
//            .apply {
//                when (isForward) {
//                    true -> setCustomAnimations(R.anim.right_in, R.anim.right_out)
//                    false -> setCustomAnimations(R.anim.left_in, R.anim.left_out)
//                }
//
//                add(containerId, fragment, fragment.javaClass.simpleName)
//                commitAllowingStateLoss()
//            }
//    }
//
//    /**
//     * It removes fragment from the container without custom animation
//     *
//     * @param fragment it will be removed from the container
//     */
//    protected open fun removeFragmentWithoutAnimation(fragment: Fragment) {
//        childFragmentManager.beginTransaction()
//            .apply {
//                remove(fragment)
//                commitAllowingStateLoss()
//            }
//    }
//
//    /**
//     * It removes fragment from the container
//     *
//     * @param fragment it will be removed from the container
//     * @param isForward status to show animation forward or backward
//     */
//    protected fun removeFragment(fragment: Fragment, isForward: Boolean) {
//        childFragmentManager.beginTransaction()
//            .apply {
//                when (isForward) {
//                    true -> setCustomAnimations(R.anim.right_in, R.anim.right_out)
//                    false -> setCustomAnimations(R.anim.left_in, R.anim.left_out)
//                }
//
//                remove(fragment)
//                commitAllowingStateLoss()
//            }
//    }
//
//    /**
//     * It replaces fragment in the container and shows the current fragment
//     *
//     * @param containerId layout id in which the fragment is replaced
//     * @param fragment it will be replaced to container
//     * @param isForward status to show animation forward or backward
//     */
//    protected fun replaceFragment(containerId: Int, fragment: Fragment, isForward: Boolean) {
//        childFragmentManager.beginTransaction()
//            .apply {
//                when (isForward) {
//                    true -> setCustomAnimations(R.anim.right_in, R.anim.right_out)
//                    false -> setCustomAnimations(R.anim.left_in, R.anim.left_out)
//                }
//
//                replace(containerId, fragment, fragment.javaClass.simpleName)
//                commitAllowingStateLoss()
//            }
//    }
//
//    /**
//     * It replaces and shows the same fragment in the container by creating new instance
//     *
//     * @param containerId layout id in which the fragment is removed and added
//     * @param oldFragment old fragment instance which will be removed from the container
//     * @param newFragment new fragment instance which will be added to the container
//     * @param isForward status to show animation forward or backward
//     *
//     * @return new fragment instance which is currently added in the container
//     */
//    protected fun replaceSameFragmentAndReturnNewInstance(
//        containerId: Int,
//        oldFragment: Fragment,
//        newFragment: Fragment,
//        isForward: Boolean
//    ): Fragment {
//        removeFragment(fragment = oldFragment, isForward = isForward)
//        addFragment(containerId = containerId, fragment = newFragment, isForward = isForward)
//
//        return newFragment
//    }
//
////    /**
////     * This calls StartActivity while finishing the current activity after logout
////     */
////    protected fun callGoToStartActivityAfterLogout() =
////        getBaseActivity()?.callGoToStartActivityAfterLogout()
//
//    protected fun showKeyboard(view: View) {
//        val inputMethodManager =
//            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.toggleSoftInputFromWindow(
//            view.applicationWindowToken,
//            InputMethodManager.SHOW_FORCED,
//            0
//        )
//    }
//
//    private fun hideKeyboardInitially() {
////        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
////        imm.hideSoftInputFromWindow(view?.windowToken, 0)
//
//        val inputManager =
//            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputManager.hideSoftInputFromWindow(activity!!.window.decorView.applicationWindowToken, 0)
//        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
//    }
//
//    override fun onApiSuccess(requestCode: String, data: Any, message: String?) {
//        LogHelper.debug(tag = TAG, msg = "onApiSuccess: requestCode- $requestCode")
//        removeFromApiRequestCodesList(requestCode = requestCode)
//
//        when (requestCode) {
//
//        }
//    }
//
//    override fun onApiFailure(requestCode: String, errors: Any?, message: String?) {
//        LogHelper.debug(tag = TAG, msg = "onApiFailure: requestCode- $requestCode")
//        removeFromApiRequestCodesList(requestCode = requestCode)
//
//        when (requestCode) {
//
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
//    override fun onDialogEventListener(
//        dialogEventType: DialogEventType,
//        requestCode: Int,
//        model: Any?
//    ) {
//        when (dialogEventType) {
//            DialogEventType.POSITIVE ->
//                onDialogPositiveEvent(requestCode = requestCode, model = model)
//
//            else ->
//                LogHelper.error(
//                    tag = TAG,
//                    msg = "onDialogEventListener: dialogEventType- INVALID"
//                )
//        }
//    }
//
//    protected open fun onDialogPositiveEvent(requestCode: Int, model: Any?) {
//        LogHelper.debug(tag = TAG, msg = "onDialogPositiveEvent: requestCode- $requestCode")
//
//        when (requestCode) {
//            DialogConst.REQUEST_CODE.FORCE_LOGOUT ->
//                callGoToStartActivityAfterLogout()
//
//            else ->
//                LogHelper.error(
//                    tag = TAG,
//                    msg = "onDialogPositiveEvent: dialogEventType- INVALID"
//                )
//        }
//    }
//
//    protected open fun onDialogNegativeEvent(requestCode: Int, model: Any?) {
//        LogHelper.debug(
//            tag = TAG,
//            msg = "onDialogNegativeEvent: requestCode- $requestCode"
//        )
//
//        when (requestCode) {
//            else ->
//                LogHelper.error(
//                    tag = TAG,
//                    msg = "onDialogNegativeEvent: dialogEventType- INVALID"
//                )
//        }
//    }
//
//    /**
//     * It checks and call to close the app
//     */
//    protected fun callForCloseApp() =
//        getBaseActivity()?.callForCloseApp()
//
//    override fun onPause() {
//        LogHelper.debug(tag = TAG, msg = "onPause")
//        super.onPause()
//    }
//
//    override fun onStop() {
//        LogHelper.debug(tag = TAG, msg = "onStop")
////        closeSnackbarMessage()
//        super.onStop()
//    }
//
//    override fun onDestroyView() {
//        LogHelper.debug(tag = TAG, msg = "onDestroyView")
//        destroyObjects()
//        super.onDestroyView()
//
//        Runtime.getRuntime().gc()
//    }
//
//    private fun destroyObjects() {
//        cancelAllPendingApiCalls()
//    }
//
//    override fun onDestroy() {
//        LogHelper.debug(tag = TAG, msg = "onDestroy")
//        super.onDestroy()
//    }
//
//    override fun onDetach() {
//        LogHelper.debug(tag = TAG, msg = "onDetach")
//        super.onDetach()
//    }
//
//}
//
