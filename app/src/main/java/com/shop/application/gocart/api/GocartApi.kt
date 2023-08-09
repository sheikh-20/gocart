package com.shop.application.gocart.api

import com.shop.application.gocart.api.response.NumVerifyResponse
import com.shop.application.gocart.api.response.OtpResponse
import com.shop.application.gocart.api.response.WebResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GocartApi {

    @GET("/api/v1/dealer/home")
    suspend fun getString(): Response<WebResponse>

    @POST("/api/v1/dealer/numberVerify")
    suspend fun numberVerify(@Body requestBody: RequestBody): Response<NumVerifyResponse>

    @POST("/api/v1/dealer/sendOtp")
    suspend fun sendOTP(@Body requestBody: RequestBody): Response<OtpResponse>

    @POST("/api/v1/dealer/verifyOtp")
    suspend fun verifyOTP(@Body requestBody: RequestBody): Response<OtpResponse>

    @POST("api/v1/dealer/businessInfo")
    suspend fun businessInfo(@Body requestBody: RequestBody): Response<WebResponse>
}