package com.hydra.core.util

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hydra.core.model.CloudCurrency
import com.hydra.core.model.CloudRate
import com.hydra.core.network.HttpClient
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SharePreferenceHelperTest {

    private lateinit var sharePreferenceHelper: SharePreferenceHelper

    @Before
    fun createClient() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        sharePreferenceHelper = SharePreferenceHelper(appContext)
    }

    @Test
    fun testSharePref() {
        sharePreferenceHelper.setLong(SharePreferenceHelper.KEY_LAST_CACHE, 1000L)
        assert(sharePreferenceHelper.getLong(SharePreferenceHelper.KEY_LAST_CACHE) == 1000L)
    }

}