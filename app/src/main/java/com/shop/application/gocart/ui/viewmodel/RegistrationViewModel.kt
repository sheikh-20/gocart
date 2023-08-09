package com.shop.application.gocart.ui.viewmodel

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.shop.application.gocart.data.preference.PreferenceRepository
import com.shop.application.gocart.data.registration.RegistrationRepository
import com.shop.application.gocart.api.request.BusinessInfo
import com.shop.application.gocart.api.request.NumberVerify
import com.shop.application.gocart.api.request.OtpVerify
import com.shop.application.gocart.api.response.NumVerifyResponse
import com.shop.application.gocart.api.response.WebResponse
import com.shop.application.gocart.ui.home.HomeActivity
import com.shop.application.gocart.ui.registration.OtpState
import com.shop.application.gocart.utils.errorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

sealed interface RegistrationUiState {
    object Default: RegistrationUiState
    object Loading: RegistrationUiState
    data class Success<T>(val result: T? = null): RegistrationUiState
    data class Failure(val message: String? = ""): RegistrationUiState
}
@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationRepository: RegistrationRepository,
    private val preferenceRepository: PreferenceRepository
    ): ViewModel() {

    companion object {
        private const val TAG = "RegistrationViewModel"
    }

    var country by mutableStateOf(0)
        private set
    fun updateCountryField(value: String) {
        country = value.toIntOrNull() ?: 0
    }

    var phoneNumber by mutableStateOf(0L)
        private set
    fun updatePhoneNumberField(value: String) {
        phoneNumber = value.toLongOrNull() ?: 0L
        if (country.toString().length == 2 && phoneNumber.toString().length == 10) {
            numberVerify(NumberVerify(country, phoneNumber))
        } else {
            _registrationUiState.value = RegistrationUiState.Default
        }
    }

    var numberVerifyResponse by mutableStateOf(NumVerifyResponse())
        private set
    fun updateNumberVerifyResponseField(value: NumVerifyResponse) {
        numberVerifyResponse = value
    }

    private var _registrationUiState = MutableStateFlow<RegistrationUiState>(RegistrationUiState.Default)
    val registrationUiState: StateFlow<RegistrationUiState> = _registrationUiState.asStateFlow()

//    val uiState: StateFlow<UiState> =
//        preferenceRepository.getPreferenceFlow.map {
//            UiState(it.toString())
//        }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000),
//                initialValue = UiState()
//            )
//
//    private fun updateRegistrationUiState(webResponse: WebResponse) {
//        _registrationUiState.update {
//            it.copy(response = webResponse)
//        }
//    }

    fun numberVerify(numberVerify: NumberVerify) = viewModelScope.launch(Dispatchers.IO) {
        _registrationUiState.value = RegistrationUiState.Loading

        try {
            val gson = Gson().toJson(numberVerify)
            val jsonData = JSONObject(gson)

            val mediaType = "application/json".toMediaType()
            val requestBody = jsonData.toString().toRequestBody(mediaType)

            val response = registrationRepository.numberVerify(requestBody)
            if (response.isSuccessful) {
                _registrationUiState.value = RegistrationUiState.Success<NumVerifyResponse>(result = response.body())
            } else {
                _registrationUiState.value = RegistrationUiState.Failure(message = errorResponse(response.code(), response.message()))
            }
        } catch (exception: IOException) {
            _registrationUiState.value = RegistrationUiState.Failure(message = exception.message.toString())
        }
    }

    fun sendOTP(numberVerify: NumberVerify) = viewModelScope.launch {
        try {
            val gson = Gson().toJson(numberVerify)
            val jsonData = JSONObject(gson)

            val mediaType = "application/json".toMediaType()
            val requestBody = jsonData.toString().toRequestBody(mediaType)

            val response = registrationRepository.sendOtp(requestBody)

            if (response.isSuccessful) {
                Timber.tag(TAG).d(response.message().toString())
            } else if (response.code() == 400) {
                Timber.tag(TAG).e(response.message().toString())
            } else if (response.code() == 404) {
                Timber.tag(TAG).e(response.message().toString())
            } else {
                Timber.tag(TAG).e(response.message().toString())
            }

        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception.message.toString())
        }
    }

    fun verifyOTP(otpVerify: OtpVerify, otpState: OtpState, activity: Activity?) = viewModelScope.launch {
        try {
            val gson = Gson().toJson(otpVerify)
            val jsonData = JSONObject(gson)

            val mediaType = "application/json".toMediaType()
            val requestBody = jsonData.toString().toRequestBody(mediaType)

            val response = registrationRepository.verifyOtp(requestBody)

            if (response.isSuccessful) {

                Timber.tag(TAG).d(response.message().toString())
            } else if (response.code() == 400) {
                Timber.tag(TAG).e(response.message().toString())
            } else if (response.code() == 404) {

                Timber.tag(TAG).e(response.message().toString())
            } else {


                Timber.tag(TAG).e(response.message().toString())
            }

        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception.message.toString())
        }
    }

    fun updateBusinessInfo(businessInfo: BusinessInfo) = viewModelScope.launch {
        try {
            val gson = Gson().toJson(businessInfo)
            val jsonData = JSONObject(gson)

            val mediaType = "application/json".toMediaType()
            val requestBody = jsonData.toString().toRequestBody(mediaType)

            val response = registrationRepository.businessInfo(requestBody)

            if (response.isSuccessful) {
                Timber.tag(TAG).d(response.message().toString())
            } else if (response.code() == 400) {
                Timber.tag(TAG).e(response.message().toString())
            } else if (response.code() == 404) {
                Timber.tag(TAG).e(response.message().toString())
            } else {
                Timber.tag(TAG).e(response.message().toString())
            }

        } catch (exception: IOException) {
            Timber.tag(TAG).e(exception.message.toString())
        }
    }
}