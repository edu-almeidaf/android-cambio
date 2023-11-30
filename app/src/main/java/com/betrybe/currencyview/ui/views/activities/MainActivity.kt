package com.betrybe.currencyview.ui.views.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.betrybe.currencyview.common.ApiIdlingResource
import com.betrybe.currencyview.data.api.ApiServiceClient
import com.betrybe.currencyview.data.models.CurrencySymbolResponse
import com.betrye.currencyview.R
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var autoComplete: AutoCompleteTextView
    private lateinit var selectCurrencyText: MaterialTextView
    private lateinit var loadingView: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        autoComplete = findViewById(R.id.currency_selection_input_layout)
        selectCurrencyText = findViewById(R.id.select_currency_state)
        loadingView = findViewById(R.id.waiting_response_state)
    }

    override fun onStart() {
        super.onStart()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                ApiIdlingResource.increment()

                startLoading()

                val data = getSymbols().symbols.keys.toList()
                setAutoComplete(data)

                stopLoading()
            } catch (error: HttpException) {
                Log.d("Error", "$error")
            } catch (error: IOException) {
                Log.d("Error", "$error")
            } finally {
                ApiIdlingResource.decrement()
            }
        }
    }

    private suspend fun getSymbols(): CurrencySymbolResponse {
        val apiService = ApiServiceClient.instance
        val res = withContext(Dispatchers.IO) {
            apiService.getSymbols()
        }
        return res
    }

    private fun setAutoComplete(data: List<String>) {
        val adapter = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_dropdown_item_1line,
            data
        )

        autoComplete.setAdapter(adapter)
        autoComplete.setOnClickListener {
            autoComplete.showDropDown()
        }
        selectCurrencyText.visibility = View.VISIBLE
        autoComplete.requestFocus()
    }

    private fun startLoading() {
        loadingView.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        loadingView.visibility = View.GONE
    }
}
