package com.example.mvvmdemo.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DateSelectedModel(
    @SerializedName("dayOfMonth") var dayOfMonth: Int,
    @SerializedName("monthOfYear") var monthOfYear: Int,
    @SerializedName("year") var year: Int
) : Serializable {

}
