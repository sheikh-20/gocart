package com.shop.application.gocart.api

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import com.shop.application.gocart.data.preference.PreferenceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(repository: PreferenceRepository): Interceptor {

    private var authToken: String = ""

    private val uiState: SharedFlow<String> =
        repository.getPreferenceFlow.map {
            Log.d("Auth Token", it.toString())
            authToken = it.toString()
            it.toString()
        }
            .shareIn(scope = CoroutineScope(Dispatchers.IO), SharingStarted.Eagerly, 1)

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader(name = "Accept", value = "application/json")
        request.addHeader("Authorization", "Bearer $authToken")
        return chain.proceed(request.build())
    }
}