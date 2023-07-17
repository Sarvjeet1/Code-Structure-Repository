package com.example.mvvmdemo.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RememberMeModel(
    @SerializedName("rememberMeStatus") val rememberMeStatus: Boolean,
    @SerializedName("email") val email: String?,
    @SerializedName("paswrd") val paswrd: String?
) : Serializable