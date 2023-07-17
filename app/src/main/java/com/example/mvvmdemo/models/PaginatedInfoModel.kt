package com.example.mvvmdemo.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PaginatedInfoModel : Serializable {

    @SerializedName("currentPage")
    var currentPage: Int = 0

    @SerializedName("lastPage")
    var lastPage: Int = 0

    @SerializedName("total")
    var total: Int = 0

    @SerializedName("nextPageUrl")
    var nextPageUrl: String? = null

}
