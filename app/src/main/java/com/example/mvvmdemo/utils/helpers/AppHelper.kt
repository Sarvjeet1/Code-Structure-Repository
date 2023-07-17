package com.example.mvvmdemo.utils.helpers

import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.telephony.SmsManager
import android.text.*
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmdemo.BuildConfig
import com.example.mvvmdemo.R
import com.example.mvvmdemo.application.AppController
import com.example.mvvmdemo.models.PaginatedInfoModel
import com.example.mvvmdemo.utils.constants.AppConst
import com.example.mvvmdemo.utils.constants.DateTimeConst
import java.io.IOException
import java.security.SecureRandom
import java.util.*
import java.util.regex.Pattern


object AppHelper {
    private var TAG = javaClass.simpleName

    private var lastClickedMilliseconds: Long = 0L

    private var isNetworkConnected: Boolean = false

    internal var number: Int = AppConst.NUMBER.ZERO

    val completeString: String = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    var random: SecureRandom = SecureRandom()

    internal fun getAppVersionCode(): Int =
        BuildConfig.VERSION_CODE

    internal fun getAppName(): String? =
        AppController.getContext()?.getString(R.string.app_name)

    /**
     * It gives network connected status
     *
     * @return network connected status
     */
    internal fun isNetworkConnected(): Boolean {
        return isNetworkConnected
    }

    internal fun updateNetworkConnectedStatus(status: Boolean) {
        isNetworkConnected = status
    }

    /**
     * It gives user loggedIn status
     *
     * @return user loggedIn status
     */
    internal fun isUserLoggedIn(): Boolean =
        SharedPreferencesHelper.getUserId() != AppConst.NUMBER.ZERO

    /**
     * It sets the ForegroundColorSpan to SpannableString
     *
     * @param context
     * @param spannableStringBuilder
     * @param indexStart start of the span's range
     * @param indexEnd end of the span's range
     * @param colorResId color's resource id
     */
    internal fun setColorSpanToSpannableString(
        context: Context,
        spannableStringBuilder: SpannableStringBuilder,
        indexStart: Int,
        indexEnd: Int,
        colorResId: Int
    ) =
        spannableStringBuilder.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    context,
                    colorResId
                )
            ),
            indexStart,
            indexEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

    /**
     * It checks and returns the status whether App is running or not
     *
     * @param context
     *
     * @return App running status
     */
    internal fun isAppRunning(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        return null != activityManager.runningAppProcesses?.firstOrNull { processInfo -> processInfo.processName == BuildConfig.APPLICATION_ID }
    }

    /**
     * It is called to checks and returns the status whether user has clicked any view once or not
     *
     * @return user clicked status
     */
    internal fun shouldProceedClick(): Boolean {
        var status: Boolean = false

        val timeDiff = System.currentTimeMillis() - lastClickedMilliseconds

        if (timeDiff > DateTimeConst.DURATION_IN_MILLISECONDS.CLICK_DELAY || timeDiff < DateTimeConst.DURATION_IN_MILLISECONDS.ZERO) {
            lastClickedMilliseconds = System.currentTimeMillis()
            status = true
        }

        return status
    }

    /**
     * It is called to remove emojis
     */
    internal var emojiFilter: InputFilter = object : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            for (i in start until end) {
                val type = Character.getType(source[i])

                when (type) {
                    Character.SURROGATE.toInt(),
                    Character.OTHER_SYMBOL.toInt(),
                    Character.ENCLOSING_MARK.toInt() -> return ""
                }
            }

            return null
        }
    }

    /**
     * To check email validation
     *
     * @param email email id which is checked for validation
     *
     * @return email validation defaultStatus as boolean
     */
    internal fun isValidEmail(email: String): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
//        Pattern.compile(AppConst.REGEX.EMAIL).matcher(email).matches()

    /**
     * To check url validation
     *
     * @param url url id which is checked for validation
     *
     * @return url validation defaultStatus as boolean
     */
    internal fun isValidUrl(url: String): Boolean =
        android.util.Patterns.WEB_URL.matcher(url).matches()

    /**
     * To check password validation
     *
     * @param password password which is checked for validation
     *
     * @return password validation defaultStatus as boolean
     */
    internal fun isValidPassword(password: String): Boolean =
        Pattern.compile(AppConst.REGEX.PASSWORD).matcher(password).matches()

    /**
     * Here text is automatically formatted to style bold.
     */
    internal var styleTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            s?.apply {
                if (length > 0) {
                    setSpan(
                        StyleSpan(Typeface.BOLD),
                        0,
                        length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }

    /**
     * Here card number is formatted by automatically adding space in-between the card number as **** **** **** ****.
     */
    internal class FormatCardNumberTextWatcher(private val editText: EditText) : TextWatcher {
        private var isUpdating = false

        private val currentStringBuilder = StringBuilder()

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (isUpdating) return

            currentStringBuilder.apply {
                clear()
                append(s.toString().replace(AppConst.CHAR.SPACE.toString(), "", true))
            }
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (isUpdating) return

            currentStringBuilder.apply {
                if (s.toString().replace(AppConst.CHAR.SPACE.toString(), "", true).length > length)
                    append(s[start])
                else if (length > AppConst.NUMBER.ZERO)
                    deleteCharAt(length - 1)
            }
        }

        override fun afterTextChanged(s: Editable) {
            if (isUpdating) return

            // Set flag to indicate that we are updating the Editable.
            isUpdating = true

            val stringBuilder = StringBuilder()
                .apply {
                    currentStringBuilder.indices.forEach { i ->
                        if (currentStringBuilder[i] != AppConst.CHAR.SPACE && i != 0 && i % 4 == 0)
                            append(AppConst.CHAR.SPACE)

                        append(currentStringBuilder[i])
                    }
                }

            editText.apply {
                setText(stringBuilder.toString())
                setSelection(stringBuilder.length)
            }

            isUpdating = false
        }
    }

    /**
     * Here card's expiry date is formatted to MM/YYYY
     */
    internal class FormatCardExpiryDateTextWatcher(private val editText: EditText) : TextWatcher {
        private var isUpdating = false

        private val currentStringBuilder = StringBuilder()

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (isUpdating) return

            currentStringBuilder.apply {
                clear()
                append(s.toString().replace(AppConst.CHAR.FORWARD_SLASH.toString(), "", true))
            }
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (isUpdating) return

            currentStringBuilder.apply {
                if (s.toString().replace(
                        AppConst.CHAR.FORWARD_SLASH.toString(),
                        "",
                        true
                    ).length > length
                )
                    append(s[start])
                else if (length > AppConst.NUMBER.ZERO)
                    deleteCharAt(length - 1)
            }
        }

        override fun afterTextChanged(s: Editable) {
            if (isUpdating) return

            // Set flag to indicate that we are updating the Editable.
            isUpdating = true

            val stringBuilder = StringBuilder()
                .apply {
                    currentStringBuilder.indices.forEach { i ->
                        if (currentStringBuilder[i] != AppConst.CHAR.FORWARD_SLASH && i == 2)
                            append(AppConst.CHAR.FORWARD_SLASH)

                        append(currentStringBuilder[i])
                    }
                }

            editText.apply {
                setText(stringBuilder.toString())
                setSelection(stringBuilder.length)
            }

            isUpdating = false
        }
    }

    /**
     * Here time is formatted to HH:MM
     */
    internal class FormatTimeTextWatcher(private val editText: EditText) : TextWatcher {
        private var isUpdating = false

        private val currentStringBuilder = StringBuilder()

        private var value: Int? = null

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (isUpdating) return

            currentStringBuilder.apply {
                clear()
                append(s.toString().replace(AppConst.CHAR.COLON.toString(), "", true))
            }
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (isUpdating) return

            currentStringBuilder.apply {
                if (s.toString().replace(
                        AppConst.CHAR.COLON.toString(),
                        "",
                        true
                    ).length > length
                )
                    append(s[start])
                else if (length > AppConst.NUMBER.ZERO)
                    deleteCharAt(length - 1)
            }
        }

        override fun afterTextChanged(s: Editable) {
            if (isUpdating) return

            // Set flag to indicate that we are updating the Editable.
            isUpdating = true

            val stringBuilder = StringBuilder()
                .apply {
                    currentStringBuilder.indices.forEach { i ->
                        when (i) {
                            AppConst.NUMBER.ZERO -> {
                                value = currentStringBuilder[i].toString().toInt()

                                if (value!! > AppConst.NUMBER.ONE)
                                    return@forEach
                            }

                            AppConst.NUMBER.ONE -> {
                                value = (currentStringBuilder[i - 1].toString()
                                        + currentStringBuilder[i].toString()).toInt()

                                if (value!! > AppConst.NUMBER.TWELVE || value!! < AppConst.NUMBER.ONE)
                                    return@forEach
                            }

                            AppConst.NUMBER.TWO -> {
                                value = currentStringBuilder[i].toString().toInt()

                                if (value!! > AppConst.NUMBER.FIVE)
                                    return@forEach
                            }

                            AppConst.NUMBER.THREE -> {
                                value = (currentStringBuilder[i - 1].toString()
                                        + currentStringBuilder[i].toString()).toInt()

                                if (value!! > AppConst.NUMBER.FIFTY_NINE)
                                    return@forEach
                            }
                        }

                        if ((currentStringBuilder[i] != AppConst.CHAR.COLON) && (i == AppConst.NUMBER.TWO))
                            append(AppConst.CHAR.COLON)

                        append(currentStringBuilder[i])
                    }
                }

            editText.apply {
                setText(stringBuilder.toString())
                setSelection(stringBuilder.length)
            }

            isUpdating = false
        }
    }

    /**
     * Here character count is calculated.
     */
    internal class CharacterCountTextWatcher(
        private val context: Context,
        private val editText: EditText,
        private val tvCount: TextView
    ) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            tvCount.text = String.format(context.getString(R.string.format_count_char), s.length)
        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    /**
     * It extracts only numbers from the string
     *
     * @param str
     * @return
     */
    internal fun getOnlyNumbersFromString(str: String): String {
        return str.replace("[^0-9]".toRegex(), "")
    }

    /**
     * It disables the edit text from input
     *
     * @param editText
     */
    private fun disableInput(editText: EditText) {
        editText.inputType = InputType.TYPE_NULL
        editText.setOnKeyListener { v, keyCode, event ->
            true  // Blocks input from hardware keyboards.
        }
    }

    /**
     * It disables the edit text completely
     *
     * @param editText
     */
    internal fun disableEditText(editText: EditText) {
        disableInput(editText)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)  // API 21
            editText.showSoftInputOnFocus = false
        else  // API 11-20
            editText.setTextIsSelectable(true)
    }

    /**
     * It extracts comma splitted string from the ArrayList
     *
     * @param dataList
     * @return
     */
    internal fun <T> getCommaSplittedStringFromArrayList(dataList: ArrayList<T>): String {
        return TextUtils.join(AppConst.CHAR.COMMA.toString(), dataList)
    }

    /**
     * Here sharing text.
     *
     * @param context
     * @param text
     */
    internal fun shareText(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, getAppName())
        intent.putExtra(Intent.EXTRA_TEXT, text)

        context.startActivity(intent)
    }

    /**
     * It checks the occurrence of sequence in string
     *
     * @param string
     * @param sequence
     * @return
     */
    internal fun checkStringContainsSequence(string: String?, sequence: String?): Boolean {
        return string != null && sequence != null && string.contains(sequence)
    }

    internal fun getRandomString(len: Int): String? {
        val sb = java.lang.StringBuilder(len)
        for (i in 0 until len) sb.append(completeString.get(random.nextInt(completeString.length)))
        return sb.toString()
    }

    internal fun getRandomInteger(random: Random?, min: Int, max: Int): Int {
        var rand = random

        if (rand == null)
            rand = Random()

        return rand.nextInt(max - min) + min
    }

    internal fun getRandomColor(): Int {
        val random = Random()

        return Color.rgb(
            getRandomInteger(
                random = random,
                min = AppConst.NUMBER.ZERO,
                max = AppConst.NUMBER.TWO_HUNDRED_FIFTY_FIVE
            ),
            getRandomInteger(
                random = random,
                min = AppConst.NUMBER.ZERO,
                max = AppConst.NUMBER.TWO_HUNDRED_FIFTY_FIVE
            ),
            getRandomInteger(
                random = random,
                min = AppConst.NUMBER.ZERO,
                max = AppConst.NUMBER.TWO_HUNDRED_FIFTY_FIVE
            )
        )
    }

    internal fun getRandomString(): String {
        val random = UUID.randomUUID().toString()
        return random
    }

    internal fun sendSms(phoneNumber: String, message: String) {
        val smsManager: SmsManager = SmsManager.getDefault()

        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
    }

    internal fun openSmsApp(context: Context, phoneNumber: String, message: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:${phoneNumber}")  // This ensures only SMS apps respond
            putExtra("sms_body", message)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    internal fun isLocationEnabled(context: Context): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    internal fun scrollEditText(editText: EditText) {
        editText.setOnTouchListener(View.OnTouchListener { v, event ->
//            if (v.id == R.id.editText) {
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> v.parent
                    .requestDisallowInterceptTouchEvent(false)
            }
//            }
            false
        })
    }

    internal fun fetchAddressFromLocation(
        context: Context?,
        name: String?,
        latitude: Double,
        longitude: Double,
        handler: Handler?
    ) {
        val thread: Thread = object : Thread() {
            override fun run() {
                val geocoder = Geocoder(context, Locale.getDefault())
                var result: String? = null

                try {
                    val addressList: List<Address> =
                        geocoder.getFromLocation(latitude, longitude, 1)

                    if (addressList.isNotEmpty()) {
                        val address: Address = addressList[0]
                        val sb = StringBuilder()

                        address.apply {
                            name?.let {
                                sb.append(name).append(", ")
                            }

                            sb.append(subAdminArea).append(", ")
                            sb.append(adminArea).append(", ")
                            sb.append(countryName)

                            LogHelper.apply {
                                debug(TAG, "fetchAddressFromLocation: mAdminArea- $adminArea")
                                debug(
                                    TAG,
                                    "fetchAddressFromLocation: mSubAdminArea- $subAdminArea"
                                )
                                debug(TAG, "fetchAddressFromLocation: mLocality- $locality")
                                debug(TAG, "fetchAddressFromLocation: mSubLocality- $subLocality")
                                debug(
                                    TAG,
                                    "fetchAddressFromLocation: mThoroughfare- $thoroughfare"
                                )
                                debug(
                                    TAG,
                                    "fetchAddressFromLocation: mSubThoroughfare- $subThoroughfare"
                                )
                                debug(TAG, "fetchAddressFromLocation: mPremises- $premises")
                                debug(TAG, "fetchAddressFromLocation: mPostalCode- $postalCode")
                                debug(TAG, "fetchAddressFromLocation: mCountryCode- $countryCode")
                                debug(TAG, "fetchAddressFromLocation: mCountryName- $countryName")
                                debug(TAG, "fetchAddressFromLocation: mLatitude- $latitude")
                                debug(TAG, "fetchAddressFromLocation: mLongitude- $longitude")
                                debug(
                                    TAG,
                                    "fetchAddressFromLocation: mHasLatitude- ${hasLatitude()}"
                                )
                                debug(
                                    TAG,
                                    "fetchAddressFromLocation: mHasLongitude- ${hasLongitude()}"
                                )
                                debug(TAG, "fetchAddressFromLocation: mPhone- $phone")
                                debug(TAG, "fetchAddressFromLocation: mUrl- $url")
                            }
                        }

                        result = sb.toString()
                    }
                } catch (e: IOException) {
                    LogHelper.error(TAG, "fetchAddressFromLocation: error- $e")
                } finally {
                    Message.obtain().apply {
                        target = handler

                        if (result != null) {
                            what = AppConst.NUMBER.ONE

                            val bundle = Bundle()
                            bundle.putString(AppConst.KEY.ADDRESS, result)

                            data = bundle
                        } else {
                            what = AppConst.NUMBER.ZERO
                        }

                        sendToTarget()
                    }
                }
            }
        }

        thread.start()
    }

    /**
     * It is called to check whether recyclerView should load more data or not
     *
     * @param paginatedInfoModel
     * @param dx The amount of horizontal scroll
     * @param dy The amount of vertical scroll
     * @param layoutManager
     * @param isListLoading
     * @param isReverseLayout
     */
    internal fun shouldLoadMore(
        paginatedInfoModel: PaginatedInfoModel,
        dx: Int?,
        dy: Int?,
        layoutManager: LinearLayoutManager,
        isListLoading: Boolean,
        isReverseLayout: Boolean
    ): Boolean {
        val scrollValue: Int = when {
            dx != null -> dx
            dy != null -> dy
            else -> 0
        }

        if ((!isReverseLayout && scrollValue <= 0 || !isMoreDataAvailable(paginatedInfoModel))
            || (isReverseLayout && scrollValue >= 0 || !isMoreDataAvailable(paginatedInfoModel))
        ) return false

        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        LogHelper.debug(
            tag = TAG,
            msg = "last -> ${layoutManager.findLastVisibleItemPosition()} " +
                    "first -> ${layoutManager.findFirstVisibleItemPosition()}  "
        )

        LogHelper.debug(
            tag = TAG,
            msg = "totalItemCount -> ${totalItemCount} " +
                    "lastVisibleItem -> ${lastVisibleItem} "
        )

        if (!isListLoading && (totalItemCount <= lastVisibleItem + AppConst.DEFAULT_VALUE.VISIBLE_THRESHOLD))
            return true

        return false
    }

    /**
     * It is called to check paginatedInfoModel for more data available to load or not
     *
     * @param paginatedInfoModel
     */
    internal fun isMoreDataAvailable(paginatedInfoModel: PaginatedInfoModel): Boolean =
//        !paginatedInfoModel.nextPageUrl.isNullOrBlank()
        paginatedInfoModel.currentPage != paginatedInfoModel.total

}