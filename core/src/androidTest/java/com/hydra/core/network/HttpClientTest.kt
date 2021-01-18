package com.hydra.core.network

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hydra.core.db.Currency
import com.hydra.core.db.CurrencyDao
import com.hydra.core.db.CurrencyDb
import com.hydra.core.model.CloudCurrency
import com.hydra.core.model.CloudRate
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class HttpClientTest {

    private lateinit var client: HttpClient

    @Before
    fun createClient() {
        client = HttpClient()
    }

    @Test
    fun tryFetchApi() {
        runBlocking {
            val data = client.callApi<CloudRate>(HttpClient.url_rate_list)
            assert(data != null)
        }
    }

    @Test
    fun guaranteeCurrencyMapCoverToDefaultUSDRateList() {
        runBlocking {
            val rate = client.callApi<CloudRate>(HttpClient.url_rate_list)
            val currency = client.callApi<CloudCurrency>(HttpClient.url_currency_list)
            assert(rate != null)
            assert(currency != null)
            currency?.getCurrencies()?.forEach {
                assert(rate!!.quotes.containsKey(it.title))
            }
        }
    }
}