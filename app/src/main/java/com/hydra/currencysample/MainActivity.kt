package com.hydra.currencysample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    val url = "http://api.currencylayer.com/live?access_key=22c7be4e826ee30d9320a60d154ff01e&format=1"
    val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        GlobalScope.launch {
            run()
        }

    }

    private fun getOkHttpClient(): OkHttpClient {
        val callTimeoutInSeconds = 30.0

        val okHttpClient = OkHttpClient.Builder()
                .callTimeout(callTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
                .readTimeout(callTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
                .connectTimeout(callTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
                .build()

        return okHttpClient
    }

    private fun run() {
        val url = "http://api.currencylayer.com/live".toHttpUrlOrNull()?.newBuilder()
                ?.addEncodedQueryParameter("access_key", "22c7be4e826ee30d9320a60d154ff01e")
                ?.build()
        val request = Request.Builder()
                .get()
                .url(url!!)
                .build()

        val response = getOkHttpClient().newCall(request).execute()
        Log.d("hydrated", "" + response)

    }
}