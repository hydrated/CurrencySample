package com.hydra.core.network

import android.util.Log
import com.google.gson.Gson
import com.hydra.core.model.CloudCurrency
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException
import java.util.concurrent.TimeUnit


class HttpClient {

    companion object {

        private const val key_title = "access_key"
        private const val key = "22c7be4e826ee30d9320a60d154ff01e"


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

        }

        suspend fun getCurrencyList() {
            val url = " http://api.currencylayer.com/list".toHttpUrlOrNull()?.newBuilder()
                ?.addEncodedQueryParameter(key_title, key)
                ?.build()
            val request = Request.Builder()
                .get()
                .url(url!!)
                .build()
            getOkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    val dataString = response.body!!.string()
                    val gson = Gson()
                    val gsonData = gson.fromJson(dataString, CloudCurrency::class.java)
                    // Do some other stuffs with gsonData separately. This doesn't return anything to gsonData.
              // This will eventually be done via another coroutine.
                }
            })

        }
    }
}