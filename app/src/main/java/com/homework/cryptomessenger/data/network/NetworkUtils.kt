package com.homework.cryptomessenger.data.network

import com.homework.cryptomessenger.BuildConfig
import com.homework.cryptomessenger.data.network.NetworkConstants.APPLICATION_JSON_TYPE
import com.homework.cryptomessenger.data.network.NetworkConstants.AUTHORIZATION
import com.homework.cryptomessenger.data.prefs.SharedPrefs
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

fun Retrofit.Builder.setClient() = apply {
    val okHttpClient = OkHttpClient.Builder()
        .addQueryInterceptor()
        .addHttpLoggingInterceptor()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()
    this.client(okHttpClient)
}

private fun OkHttpClient.Builder.addHttpLoggingInterceptor() = apply {
    if (BuildConfig.DEBUG) {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        this.addNetworkInterceptor(interceptor)
    }
}

private fun OkHttpClient.Builder.addQueryInterceptor() = apply {
    val interceptor = Interceptor { chain ->
        val token = SharedPrefs.getSharedPreferenceString(SharedPrefs.TOKEN_KEY)
        var request = chain.request()
        request = request.newBuilder().header(
            AUTHORIZATION,
            token
        ).build()
        chain.proceed(request)
    }
    this.addInterceptor(interceptor)
}

@Suppress("EXPERIMENTAL_API_USAGE")
fun Retrofit.Builder.addJsonConverter() = apply {
    val json = Json { ignoreUnknownKeys = true }
    val contentType = APPLICATION_JSON_TYPE.toMediaType()
    this.addConverterFactory(json.asConverterFactory(contentType))
}
