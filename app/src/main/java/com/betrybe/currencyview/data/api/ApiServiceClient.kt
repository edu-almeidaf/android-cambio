package com.betrybe.currencyview.data.api

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient

object ApiServiceClient {
    private const val key = "0J91Vf7aimCZyBWnI7rrQgt6Tf7kXlVx"

    val instance: ApiService by lazy {
        val apiInterceptor = ApiInterceptor(key)
        val client = OkHttpClient.Builder()
            .addInterceptor(apiInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.apilayer.com/exchangerates_data/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(ApiService::class.java)
    }
}
