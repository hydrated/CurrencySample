package com.hydra.core.network

import android.util.Log
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class HttpClient {

    companion object {
        private fun getOkHttpClient(): OkHttpClient {
            val callTimeoutInSeconds = 30.0

            val okHttpClient = OkHttpClient.Builder()
                .callTimeout(callTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
                .readTimeout(callTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
                .connectTimeout(callTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
                .build()

            return okHttpClient
        }

        public fun run() {
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
}