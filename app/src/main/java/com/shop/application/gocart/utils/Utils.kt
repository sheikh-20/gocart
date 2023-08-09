package com.shop.application.gocart.utils

fun errorResponse(statusCode: Int, message: String): String {
    if (statusCode == 404) {
        return "$statusCode -> Not found"
    }
    return "$statusCode -> $message"
}