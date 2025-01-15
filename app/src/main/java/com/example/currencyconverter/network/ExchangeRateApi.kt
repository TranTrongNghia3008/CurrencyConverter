package com.example.currencyconverter.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("latest")
    suspend fun getExchangeRates(
        @Query("base") base: String,
        @Query("access_key") accessKey: String
    ): Response<ExchangeRateResponse>
}

data class ExchangeRateResponse(
    val rates: Map<String, Double>
)
