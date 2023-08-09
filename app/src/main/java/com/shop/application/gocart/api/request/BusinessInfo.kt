package com.shop.application.gocart.api.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class BusinessInfo(
    @SerializedName("user_name")
    val userName: String,

    @SerializedName("business_name")
    val businessName: String
)
