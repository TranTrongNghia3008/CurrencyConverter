package com.example.currencyconverter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.network.ApiClient
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {
    private val _conversionResult = MutableLiveData<String>()
    val conversionResult: LiveData<String> get() = _conversionResult

    private val accessKey = "2606aa19b037e89fd333175a841fe1be"

    fun convertCurrency(amount: Double, from: String, to: String) {
        viewModelScope.launch {
            try {
                val response = ApiClient.instance.getExchangeRates(from, accessKey)
                if (response.isSuccessful) {
                    val rate = response.body()?.rates?.get(to) ?: 0.0
                    val result = amount * rate

                    if (amount != 0.0 && rate == 0.0) {
                        _conversionResult.postValue("No conversion from $from supported")
                    } else {
                        _conversionResult.postValue("$amount $from = $result $to")
                    }

                } else {
                    _conversionResult.postValue("Unable to get exchange rate")
                }
            } catch (e: Exception) {
                _conversionResult.postValue("Network connection error")
            }
        }
    }
}
