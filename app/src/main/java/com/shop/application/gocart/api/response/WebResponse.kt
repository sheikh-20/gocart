package com.shop.application.gocart.api.response

import kotlinx.serialization.Serializable

@Serializable
data class WebResponse(
    val code: Int? = null,
    val data: Data? = null,
    val message: String? = null,
    val status: Boolean? = null
) {
    @Serializable
    data class Data(val name: String?)
}
