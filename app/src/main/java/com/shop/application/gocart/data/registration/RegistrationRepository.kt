package com.shop.application.gocart.data.registration

import com.shop.application.gocart.api.GocartApi
import com.shop.application.gocart.api.response.NumVerifyResponse
import com.shop.application.gocart.api.response.OtpResponse
import com.shop.application.gocart.api.response.WebResponse
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

interface RegistrationRepository {
    suspend fun numberVerify(requestBody: RequestBody): Response<NumVerifyResponse>
    suspend fun sendOtp(requestBody: RequestBody): Response<OtpResponse>
    suspend fun verifyOtp(requestBody: RequestBody): Response<OtpResponse>

    suspend fun getString(): Response<WebResponse>

    suspend fun businessInfo(requestBody: RequestBody): Response<WebResponse>
}

class RegistrationRepositoryImpl @Inject constructor(private val api: GocartApi): RegistrationRepository {
    override suspend fun numberVerify(requestBody: RequestBody): Response<NumVerifyResponse> {
        return api.numberVerify(requestBody)
    }

    override suspend fun getString(): Response<WebResponse> = api.getString()

    override suspend fun sendOtp(requestBody: RequestBody): Response<OtpResponse> {
        return api.sendOTP(requestBody)
    }

    override suspend fun verifyOtp(requestBody: RequestBody): Response<OtpResponse> {
        return api.verifyOTP(requestBody)
    }

    override suspend fun businessInfo(requestBody: RequestBody): Response<WebResponse> = api.businessInfo(requestBody)
}