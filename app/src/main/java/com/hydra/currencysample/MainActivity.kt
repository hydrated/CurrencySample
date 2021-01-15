package com.hydra.currencysample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hydra.core.network.HttpClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        GlobalScope.launch {
            HttpClient.getCurrencyList();
        }

    }


}