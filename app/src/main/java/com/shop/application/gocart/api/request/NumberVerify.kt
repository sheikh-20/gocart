package com.shop.application.gocart.api.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class NumberVerify(
    @SerializedName("country") val country: Int,
    @SerializedName("phone_number") val phoneNumber: Long
)
