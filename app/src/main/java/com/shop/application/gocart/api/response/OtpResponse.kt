package com.shop.application.gocart.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtpResponse(
    val code: Int? = null,
    val data: Data? = null,
    val message: String? = null,
    val status: Boolean? = null
) {
    @Serializable
    data class Data(val token: String?)
}
