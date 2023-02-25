package com.example.myapplication.data.networking

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = "ourToken" // get from secure local storage
        Timber.i("Running network interceptor")
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}