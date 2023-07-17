package com.example.mvvmdemo.retrofit

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @GET("photos")
    fun doGetPaginationListResources(@Query("page")  currentPage: Int): Call<Any>

    @GET("photos")
    fun doGetListResources(): Call<Any>

    @POST("login")
    fun Login(@Body any: Any): Call<Any>


}