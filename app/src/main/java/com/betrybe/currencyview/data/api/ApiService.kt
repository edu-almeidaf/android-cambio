package com.betrybe.currencyview.data.api

import com.betrybe.currencyview.data.models.CurrencySymbolResponse
import retrofit2.http.GET

interface ApiService {
    @GET("symbols")
    suspend fun getSymbols(): CurrencySymbolResponse
}
