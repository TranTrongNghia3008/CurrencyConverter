package com.example.currencyconverter.model

import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("base")
    val base: String,

    @SerializedName("rates")
    val rates: Map<String, Double>,

    @SerializedName("date")
    val date: String
)
