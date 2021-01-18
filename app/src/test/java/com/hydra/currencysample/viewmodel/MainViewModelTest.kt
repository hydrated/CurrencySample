package com.hydra.currencysample.viewmodel

import android.content.Context
import com.hydra.core.repo.CurrencyRepo
import com.hydra.core.util.SharePreferenceHelper
import com.hydra.currencysample.util.CacheHelper
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class MainViewModelTest {
    @RelaxedMockK
    lateinit var sharePref: SharePreferenceHelper
    lateinit var viewModel: MainViewModel
    var repo: CurrencyRepo = Mockito.mock(CurrencyRepo::class.java)
    var cacheHelper: CacheHelper = Mockito.mock(CacheHelper::class.java)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockKAnnotations.init(this)
        val mContextMock = mockk<Context>(relaxed = true)
        sharePref = SharePreferenceHelper(mContextMock)
        viewModel = MainViewModel(repo, sharePref, cacheHelper)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `Data methods should use cache methods`() {
        runBlocking {
            launch(Dispatchers.IO) {
                viewModel.getExchangeRate()
                viewModel.getAvailableCurrency()
                Mockito.verify(cacheHelper).getExchangeRate()
                Mockito.verify(cacheHelper).getAvailableCurrency()
            }
        }
    }
}