//package com.example.mvvmdemo.utils.manager
//
//import android.content.Context
//import com.example.mvvmdemo.R
//import com.example.mvvmdemo.utils.constants.ApiConst
//import com.example.mvvmdemo.utils.helpers.LogHelper
//import com.example.mvvmdemo.utils.listeners.ApiListener
//import okhttp3.*
//import okhttp3.RequestBody.Companion.toRequestBody
//import java.io.IOException
//import java.util.concurrent.TimeUnit
//
///**
// * This is the ApiManager' Singleton class used in this project.
// */
//internal class ApiManager private constructor() {
//    private var TAG = javaClass.simpleName
//
//    private var httpClient: OkHttpClient? = null
//    private val httpClientBuilder = OkHttpClient.Builder()
//    private val headersBuilder = Headers.Builder()
//
//    private val apiCallsList = HashMap<String, Call>()
//
//    companion object {
//        private val mInstance = ApiManager()
//
//        @Synchronized
//        fun getInstance(): ApiManager {
//            return mInstance
//        }
//    }
//
//    fun getHttpClient(): OkHttpClient {
//        if (httpClient == null)
//            httpClient = configureClient()
//
//        return httpClient!!
//    }
//
//    private fun configureClient(): OkHttpClient {
//        httpClientBuilder.apply {
//            readTimeout(ApiConst.DEFAULT.TIMEOUT_IN_MINUTE, TimeUnit.MINUTES)
//            writeTimeout(ApiConst.DEFAULT.TIMEOUT_IN_MINUTE, TimeUnit.MINUTES)
//            connectTimeout(ApiConst.DEFAULT.TIMEOUT_IN_MINUTE, TimeUnit.MINUTES)
//            retryOnConnectionFailure(true)
//        }
//
//        return httpClientBuilder.build()
//    }
//
//    private fun getHeaders(): Headers {
//        headersBuilder.apply {
//            set(ApiConst.KEY.ACCEPT, ApiConst.VALUE.ACCEPT)
//            set(ApiConst.KEY.CONTENT_TYPE, ApiConst.VALUE.CONTENT_TYPE)
//            set(ApiConst.KEY.AUTHTOKEN, "${ApiConst.DEFAULT.AUTHTOKEN_BEARER}${getAuthtoken()}")
//        }
//        LogHelper.info(tag = TAG, msg = "getAuthtoken :  ${headersBuilder[ApiConst.KEY.AUTHTOKEN]}")
//        return headersBuilder.build()
//    }
//
//    internal fun get(mContext: Context, requestCode: String, url: String, listener: ApiListener?) {
//        val request = Request.Builder().headers(getHeaders()).url(url).get().build()
//
//        return execute(
//            mContext = mContext,
//            requestCode = requestCode,
//            request = request,
//            listener = listener
//        )
//    }
//
//    internal fun post(
//        mContext: Context,
//        requestCode: String,
//        url: String,
//        requestJson: String?,
//        listener: ApiListener?
//    ) {
//        val body = (requestJson ?: mContext.getString(R.string.blank_string))
//            .toRequestBody(ApiConst.MEDIA_TYPE.JSON)
//
//        val request = Request.Builder().headers(getHeaders()).url(url).post(body).build()
//
//        return execute(
//            mContext = mContext,
//            requestCode = requestCode,
//            request = request,
//            listener = listener
//        )
//    }
//
//    internal fun post(
//        mContext: Context,
//        requestCode: String,
//        url: String,
//        body: MultipartBody,
//        listener: ApiListener?
//    ) {
//        val request = Request.Builder().headers(getHeaders()).url(url).post(body).build()
//
//        return execute(
//            mContext = mContext,
//            requestCode = requestCode,
//            request = request,
//            listener = listener
//        )
//    }
//
//    internal fun put(
//        mContext: Context,
//        requestCode: String,
//        url: String,
//        requestJson: String?,
//        listener: ApiListener?
//    ) {
//        val body = (requestJson ?: mContext.getString(R.string.blank_string))
//            .toRequestBody(ApiConst.MEDIA_TYPE.JSON)
//
//        val request = Request.Builder().headers(getHeaders()).url(url).put(body).build()
//
//        return execute(
//            mContext = mContext,
//            requestCode = requestCode,
//            request = request,
//            listener = listener
//        )
//    }
//
//    internal fun patch(
//        mContext: Context,
//        requestCode: String,
//        url: String,
//        requestJson: String?,
//        listener: ApiListener?
//    ) {
//        val body = (requestJson ?: mContext.getString(R.string.blank_string))
//            .toRequestBody(ApiConst.MEDIA_TYPE.JSON)
//
//        val request = Request.Builder().headers(getHeaders()).url(url).patch(body).build()
//
//        return execute(
//            mContext = mContext,
//            requestCode = requestCode,
//            request = request,
//            listener = listener
//        )
//    }
//
//    internal fun patch(
//        mContext: Context,
//        requestCode: String,
//        url: String,
//        body: MultipartBody,
//        listener: ApiListener?
//    ) {
//        val request = Request.Builder().headers(getHeaders()).url(url).patch(body).build()
//
//        return execute(
//            mContext = mContext,
//            requestCode = requestCode,
//            request = request,
//            listener = listener
//        )
//    }
//
//    internal fun delete(
//        mContext: Context,
//        requestCode: String,
//        url: String,
//        listener: ApiListener?
//    ) {
//        val request = Request.Builder().headers(getHeaders()).url(url).delete().build()
//
//        return execute(
//            mContext = mContext,
//            requestCode = requestCode,
//            request = request,
//            listener = listener
//        )
//    }
//
//    private fun execute(
//        mContext: Context,
//        requestCode: String,
//        request: Request,
//        listener: ApiListener?
//    ) {
//        try {
//            // Execute the request and retrieve the response.
//            val call = getHttpClient().newCall(request)
//            addToApiCallsList(requestCode = requestCode, call = call)
//
//            call.enqueue(object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    LogHelper.error(tag = TAG, msg = "execute: onFailure- ${e.message}")
//
//                    removeFromApiCallsList(requestCode = requestCode, call = call)
//
//                    listener?.onApiFailure(
//                        requestCode = requestCode,
//                        errors = null,
//                        message = mContext.getString(R.string.msg_for_api_failure)
//                    )
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    val myResponse = response.body?.string()
//                    LogHelper.info(tag = TAG, msg = "execute: onResponse- $myResponse")
//
//                    removeFromApiCallsList(requestCode = requestCode, call = call)
//
//                    var responseEnvelope: ResponseEnvelope? = null
//
//                    try {
//                        responseEnvelope = GsonHelper.convertJsonStringToJavaObject(
//                            from = myResponse,
//                            to = ResponseEnvelope::class.java
//                        ) as ResponseEnvelope?
//                    } catch (exception: Exception) {
//                        LogHelper.error(
//                            tag = TAG,
//                            msg = "execute: exception- ${exception.localizedMessage}"
//                        )
//                    }
//
//                    afterGettingResponse(
//                        mContext = mContext,
//                        requestCode = requestCode,
//                        response = response,
//                        responseEnvelope = responseEnvelope,
//                        listener = listener
//                    )
//                }
//            })
//        } catch (e: Exception) {
//            LogHelper.error(TAG, "execute: " + e.printStackTrace())
//            listener?.onApiFailure(
//                requestCode = requestCode,
//                errors = null,
//                message = mContext.getString(R.string.msg_for_api_failure)
//            )
//        }
//    }
//
//    private fun afterGettingResponse(
//        mContext: Context,
//        requestCode: String,
//        response: Response,
//        responseEnvelope: ResponseEnvelope?,
//        listener: ApiListener?
//    ) {
//        when {
//            null == responseEnvelope ->
//                listener?.onApiFailure(
//                    requestCode = requestCode,
//                    errors = null,
//                    message = mContext.getString(R.string.msg_for_api_failure)
//                )
//
//            ApiConst.RESPONSE_CODE.CODE_401 == response.code ->
//                listener?.onApiForceLogout(
//                    requestCode = requestCode,
//                    message = responseEnvelope.message
//                )
//
//            null == responseEnvelope.data -> {
//                val message =
//                    if (!responseEnvelope.message.isNullOrBlank())
//                        responseEnvelope.message
//                    else
//                        mContext.getString(R.string.error_occurred)
//
//                listener?.onApiFailure(
//                    requestCode = requestCode,
//                    errors = responseEnvelope.errors,
//                    message = message
//                )
//            }
//
//            response.isSuccessful ->
//                listener?.onApiSuccess(
//                    requestCode = requestCode,
//                    data = responseEnvelope.data,
//                    message = responseEnvelope.message
//                )
//
//            !responseEnvelope.message.isNullOrBlank() ->
//                listener?.onApiFailure(
//                    requestCode = requestCode,
//                    errors = responseEnvelope.errors,
//                    message = responseEnvelope.message
//                )
//
//            else ->
//                listener?.onApiFailure(
//                    requestCode = requestCode,
//                    errors = responseEnvelope.errors,
//                    message = mContext.getString(R.string.msg_for_api_failure)
//                )
//        }
//    }
//
//    internal fun getForGoogle(
//        mContext: Context,
//        requestCode: String,
//        url: String,
//        listener: ApiListener?
//    ) {
//        val request = Request.Builder().headers(getHeaders()).url(url).get().build()
//
//        return executeForGoogle(
//            mContext = mContext,
//            requestCode = requestCode,
//            request = request,
//            listener = listener
//        )
//    }
//
//    private fun executeForGoogle(
//        mContext: Context,
//        requestCode: String,
//        request: Request,
//        listener: ApiListener?
//    ) {
//        try {
//            // Execute the request and retrieve the response.
//            val call = getInstance().getHttpClient().newCall(request)
//            addToApiCallsList(requestCode = requestCode, call = call)
//
//            call.enqueue(object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    removeFromApiCallsList(requestCode = requestCode, call = call)
//                    LogHelper.error(tag = TAG, msg = "execute: onFailure- ${e.message}")
//
//                    listener?.onApiFailure(
//                        requestCode = requestCode,
//                        errors = null,
//                        message = mContext.getString(R.string.msg_for_api_failure)
//                    )
//                }
//
//                @Throws(IOException::class)
//                override fun onResponse(call: Call, response: Response) {
//                    val myResponse = response.body!!.string()
//                    LogHelper.info(tag = TAG, msg = "execute: onResponse- $myResponse")
//
//                    removeFromApiCallsList(requestCode = requestCode, call = call)
//
//                    if (!response.isSuccessful) {
//                        listener?.onApiFailure(
//                            requestCode = requestCode,
//                            errors = null,
//                            message = response.message
//                        )
//
//                        return
//                    }
//
////                    val responseModel: GoogleMapPathModel? = GsonHelper.convertJsonStringToJavaObject(
////                        from = myResponse,
////                        to = GoogleMapPathModel::class.java
////                    ) as GoogleMapPathModel?
////
////                    when {
////                        null != responseModel -> listener?.onApiSuccess(
////                            requestCode = requestCode,
////                            data = responseModel,
////                            message = response.message()
////                        )
////
////                        else -> listener?.onApiFailure(
////                            requestCode = requestCode,
////                            message = mContext.getString(R.string.msg_for_api_failure)
////                        )
////                    }
//                }
//            })
//        } catch (e: Exception) {
//            LogHelper.error(TAG, "execute: " + e.printStackTrace())
//            listener?.onApiFailure(
//                requestCode = requestCode,
//                errors = null,
//                message = mContext.getString(R.string.msg_for_api_failure)
//            )
//        }
//    }
//
//    /**
//     * Here api request is added to the apiCallsList.
//     *
//     * @param call It is a request that has been prepared for execution
//     */
//    private fun addToApiCallsList(requestCode: String, call: Call) {
//        apiCallsList[requestCode] = call
//    }
//
//    /**
//     * Here api request is removed from the apiCallsList.
//     *
//     * @param call It is a request that has been prepared for execution
//     */
//    private fun removeFromApiCallsList(requestCode: String, call: Call?) {
//        if (null != call && !call.isCanceled())
//            call.cancel()
//
//        apiCallsList.remove(requestCode)
//    }
//
//    private fun isApiCallsListEmpty(): Boolean = apiCallsList.isEmpty()
//
//    /**
//     * Here pending calls i.e., api requests are cancelled and removed
//     * from the apiCallsList
//     */
//    internal fun cancelPendingApiCalls(requestCodeList: ArrayList<String>) {
//        while (!requestCodeList.isNullOrEmpty()) {
//            val requestCode: String = requestCodeList.removeAt(0)
//            val call: Call? = apiCallsList[requestCode]
//
//            removeFromApiCallsList(requestCode, call)
//        }
//    }
//
//    /**
//     * Here pending call i.e., api request is cancelled and removed
//     * from the apiCallsList
//     */
//    internal fun cancelPendingApiCall(requestCode: String) {
//        val call: Call? = apiCallsList[requestCode]
//
//        removeFromApiCallsList(requestCode, call)
//    }
//
//    /**
//     * Here all pending calls i.e., api requests are cancelled and removed
//     * from the apiCallsList
//     */
//    private fun cancelAllPendingApiCalls() {
//        val entries = apiCallsList.entries
//
//        for (entry in entries) {
//            val requestCode: String = entry.key
//            val call: Call = entry.value
//
//            call.cancel()
//            apiCallsList.remove(requestCode)
//        }
//    }
//
//    private fun getAuthtoken(): String = SharedPreferencesHelper.getAuthtoken()
//
//}
