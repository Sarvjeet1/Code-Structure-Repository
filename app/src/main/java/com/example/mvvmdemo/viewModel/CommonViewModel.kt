package com.example.mvvmdemo.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmdemo.repository.CommonRepository
import com.example.mvvmdemo.retrofit.ResponseHandler
import retrofit2.Call

class CommonViewModel : ViewModel() {

    /**
     * Invoke when need to hit post api
     * @param call is instanceof Call<Any>
     * @param requestCode
     * @return live data
     */

    fun sendRequestToServer(call: Call<Any>, requestCode: String): MutableLiveData<ResponseHandler<Any>> {

        return CommonRepository.sendRequestToServer(call, requestCode)

    }
}