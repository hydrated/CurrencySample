package com.hydra.currencysample.util

import android.content.Context
import com.hydra.core.repo.CurrencyRepo
import com.hydra.core.util.SharePreferenceHelper
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.never

class CacheHelperTest {
    @RelaxedMockK
    lateinit var sharePref: SharePreferenceHelper
    lateinit var cacheHelper: CacheHelper

    var repo: CurrencyRepo = mock(CurrencyRepo::class.java)

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        val mContextMock = mockk<Context>(relaxed = true)
        sharePref = SharePreferenceHelper(mContextMock)
        cacheHelper = CacheHelper(repo, sharePref)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should get data from http`() {
        runBlocking {
            cacheHelper.getExchangeRate()
            Mockito.verify(repo).getExchangeRateFromHttp()
        }
    }

    @Test
    fun `shouldn't get data from http`() {
        runBlocking {
            every { sharePref.lastCachedTimeStamp } answers { System.currentTimeMillis() }
            cacheHelper.getExchangeRate()
            Mockito.verify(repo, Mockito.times(0)).getExchangeRateFromHttp()
        }
    }
}