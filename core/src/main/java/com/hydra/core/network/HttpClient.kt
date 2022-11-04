package com.hydra.core.network

import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException
import java.util.concurrent.TimeUnit


class HttpClient {

    companion object {
        const val key_title = "access_key"
        const val key = "Abt3YhdzpcaTkvBj8VR2dgTAOuvk2rBh"

        const val url_currency_list = "http://api.currencylayer.com/list"
        const val url_rate_list = "http://api.currencylayer.com/live"
    }

    fun getOkHttpClient(): OkHttpClient {
        val callTimeoutInSeconds = 30.0

        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(callTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
            .readTimeout(callTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
            .connectTimeout(callTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
            .build()

        return okHttpClient
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend inline fun <reified T> callApi(url : String) : T? {
        val localUrl = url.toHttpUrlOrNull()?.newBuilder()
            ?.addEncodedQueryParameter(key_title, key)
            ?.build()
            ?: return null
        val request = Request.Builder()
            .get()
            .url(localUrl)
            .build()
        return suspendCancellableCoroutine { continuation ->
            getOkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    val dataString = response.body!!.string()
                    val gson = Gson()
                    val gsonData = gson.fromJson(dataString, T::class.java)
                    return continuation.resume(gsonData, onCancellation = {
                        //TODO: onCancel
                    })
                }
            })

        }

    }

}