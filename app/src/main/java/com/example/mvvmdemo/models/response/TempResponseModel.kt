package com.example.mvvmdemo.models.response

import com.google.gson.annotations.SerializedName

class TempResponseModel {

    @SerializedName("claim_id")
    var claimId: Int? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("contact_information_id")
    var contactInformationId: Int? = null

    @SerializedName("claim_customer_id")
    var claimCustomerId: Int? = null

    @SerializedName("insurance_id")
    var insuranceId: Int? = null

    @SerializedName("dispatch_id")
    var dispatchId: Int? = null

    @SerializedName("customer_id")
    var customerId: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("image")
    var image: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("claim_number")
    var claimNumber: String? = null

    @SerializedName("services_title")
    var servicesTitle: String? = null

}