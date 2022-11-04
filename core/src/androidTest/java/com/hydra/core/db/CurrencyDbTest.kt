package com.hydra.core.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hydra.core.model.CloudRate
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CurrencyDbTest {
    private lateinit var dao: CurrencyDao
    private lateinit var rateDao : RateDao
    private lateinit var db: CurrencyDb

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CurrencyDb::class.java
        ).build()
        dao = db.currencyDao()
        rateDao = db.rateDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndQueryCurrency() {
        val currency = Currency("title", "value")
        runBlocking {
            dao.insert(currency)
            val value = dao.queryByTitle("title")
            assert(value.description == "value")
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeRate() {
        val rate = CloudRate(true,"123","456",1610866146,"source", mapOf(
            "USDAED" to 3.673042,
            "USDAFN" to 77.395588,
            "USDALL" to 102.098597
        ))
        runBlocking {
            rateDao.insert(rate)
            val data = rateDao.getAll()
            assert(data != null)
        }
    }

}