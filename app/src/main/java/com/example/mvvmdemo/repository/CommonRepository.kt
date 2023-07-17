package com.example.mvvmdemo.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.retrofit.ResponseHandler
import com.example.mvvmdemo.utils.helpers.GsonHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CommonRepository {

    val TAG: String = this::class.java.simpleName

    /**
     * Here setUp call  api
     * @param call is instanceof Call<Any>
     * @param requestCode
     * @return MutableLiveData
     */
    internal fun sendRequestToServer(call: Call<Any>, requestCode: String): MutableLiveData<ResponseHandler<Any>> {

        val liveData: MutableLiveData<ResponseHandler<Any>> = MutableLiveData<ResponseHandler<Any>>()

        call.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                if (response.isSuccessful) {

                    Log.d(TAG, "Url ----- ${response.raw().request}")
                    Log.d(TAG, "Response ----- ${GsonHelper.convertJavaObjectToJsonString(response.body())}")

                    liveData.value = ResponseHandler.onSuccess(response.body(), response.message(), requestCode)

                } else {

                    liveData.value = ResponseHandler.onError(response.message(), requestCode)

                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                liveData.value = ResponseHandler.onError(t.message.toString(), requestCode)
            }
        })

        return liveData
    }
}