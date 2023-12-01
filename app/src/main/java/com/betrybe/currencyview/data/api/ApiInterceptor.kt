package com.betrybe.currencyview.data.api

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor(private val key: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val newReq = req.newBuilder().header("apiKey", key).build()

        return chain.proceed(newReq)
    }
}
