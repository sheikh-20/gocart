package com.shop.application.gocart.api.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class OtpVerify(
    @SerializedName("country") val country: Int,
    @SerializedName("phone_number") val phoneNumber: Long,
    @SerializedName("otp") val otp: Long,
    @SerializedName("type") val type: String
)
