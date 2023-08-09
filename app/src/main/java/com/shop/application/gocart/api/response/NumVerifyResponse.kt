package com.shop.application.gocart.api.response

import kotlinx.serialization.Serializable

@Serializable
data class NumVerifyResponse(
    val code: Int? = null,
    val message: String? = null,
    val status: Boolean? = null
)
