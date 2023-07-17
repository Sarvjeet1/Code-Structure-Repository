package com.example.mvvmdemo.models.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TempModel(
    @SerializedName("email") val email: String,
    @SerializedName("password") val paswrd: String,
    @SerializedName("user_type") val userType: String,
    @SerializedName("device_type") val deviceType: String,
    @SerializedName("device_token") val deviceToken: String
) : Serializable