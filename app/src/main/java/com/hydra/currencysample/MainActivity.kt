package com.hydra.currencysample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hydra.core.network.HttpClient
import com.hydra.currencysample.viewmodel.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupObservers()
        viewModel.getAvailableCurrency()

    }

    private fun setupObservers() {
        viewModel.currencies.observe(this, Observer {
            Log.d("hydrated", "" + it)
        })
    }

}