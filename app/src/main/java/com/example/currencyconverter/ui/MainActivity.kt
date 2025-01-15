package com.example.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.R
import com.example.currencyconverter.viewmodel.CurrencyViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[CurrencyViewModel::class.java]

        val amountInput: EditText = findViewById(R.id.amountInput)
        val fromCurrency: Spinner = findViewById(R.id.fromCurrency)
        val toCurrency: Spinner = findViewById(R.id.toCurrency)
        val convertButton: Button = findViewById(R.id.convertButton)
        val resultText: TextView = findViewById(R.id.resultText)

        val currencies = listOf(
            "EUR", "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD",
            "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD",
            "BND", "BOB", "BRL", "BSD", "BTC", "BTN", "BWP", "BYN", "BYR",
            "BZD", "CAD", "CDF", "CHF", "CLF", "CLP", "CNY", "CNH", "COP",
            "CRC", "CUC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD",
            "EGP", "ERN", "ETB", "FJD", "FKP", "GBP", "GEL", "GGP", "GHS",
            "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG",
            "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JEP",
            "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KMF", "KPW", "KRW",
            "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LTL",
            "LVL", "LYD", "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP",
            "MRU", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN",
            "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP",
            "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR",
            "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLE", "SLL", "SOS",
            "SRD", "STD", "SVC", "SYP", "SZL", "THB", "TJS", "TMT", "TND",
            "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU",
            "UZS", "VES", "VND", "VUV", "WST", "XAF", "XAG", "XAU", "XCD",
            "XDR", "XOF", "XPF", "YER", "ZAR", "ZMK", "ZMW", "ZWL"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        fromCurrency.adapter = adapter
        toCurrency.adapter = adapter

        convertButton.setOnClickListener {
            val amount = amountInput.text.toString().toDoubleOrNull()
            val from = fromCurrency.selectedItem.toString()
            val to = toCurrency.selectedItem.toString()

            if (amount != null && from.isNotEmpty() && to.isNotEmpty()) {
                viewModel.convertCurrency(amount, from, to)
            } else {
                resultText.text = "Please enter complete information"
            }
        }

        viewModel.conversionResult.observe(this) { result ->
            resultText.text = result
        }
    }
}
