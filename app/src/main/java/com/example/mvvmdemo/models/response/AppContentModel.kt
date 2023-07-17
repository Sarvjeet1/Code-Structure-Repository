package com.example.mvvmdemo.models.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AppContentModel(
    @SerializedName("server_timestamp") var serverTimestamp: Long
) : Serializable {

    @SerializedName("systemElapsedTime")
    var systemElapsedTime: Long? = null
}
