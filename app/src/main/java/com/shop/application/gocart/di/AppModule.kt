package com.shop.application.gocart.di

import android.content.Context
import android.graphics.Bitmap.Config
import android.os.Build
import androidx.core.os.BuildCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.internal.GsonBuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.shop.application.gocart.BuildConfig
import com.shop.application.gocart.api.GocartApi
import com.shop.application.gocart.api.NetworkInterceptor
import com.shop.application.gocart.data.preference.PreferenceRepository
import com.shop.application.gocart.data.preference.TokenRepositoryImpl
import com.shop.application.gocart.data.registration.RegistrationRepository
import com.shop.application.gocart.data.registration.RegistrationRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val PREFERENCE_NAME = "preferences"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

    @Provides
    @Singleton
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(networkInterceptor: NetworkInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(networkInterceptor).build()
    }

//    @Provides
//    @Singleton
//    fun providesGocartApi(retrofitBuilder: Retrofit.Builder): GocartApi {
//        return retrofitBuilder.build().create(GocartApi::class.java)
//    }

    @Provides
    @Singleton
    fun providesGocartApiToken(retrofitBuilder: Builder, okHttpClient: OkHttpClient): GocartApi {
        return retrofitBuilder.client(okHttpClient).build().create(GocartApi::class.java)
    }

    @Provides
    @Singleton
    fun providesRegistrationRepository(api: GocartApi): RegistrationRepository {
        return RegistrationRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesPreferencesRepository(@ApplicationContext context: Context): PreferenceRepository {
        return TokenRepositoryImpl(context.dataStore)
    }
}

