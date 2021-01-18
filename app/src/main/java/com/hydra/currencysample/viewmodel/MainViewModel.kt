package com.hydra.currencysample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hydra.core.db.Currency
import com.hydra.core.model.ExchangeRate
import com.hydra.core.repo.CurrencyRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val repo: CurrencyRepo
) : ViewModel() {

    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies: LiveData<List<Currency>>
        get() = _currencies

    private var _rateList: List<ExchangeRate>? = null
        set(value) {
            field = value
            value?.let { rateList.postValue(it) }
        }
    val rateList: MutableLiveData<List<ExchangeRate>> = MutableLiveData()

    fun getExchangeRate() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val data = repo.getExchangeRateFromHttp()
                data?.let { repo.storeExchangeRatesToDb(it) }
                _rateList = repo.getExchangeRateFromDb().getRateList()
            }
        }
    }

    fun getAvailableCurrency() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val data = repo.getCurrenciesListFromHttp()
                repo.storeCurrenciesToDb(data!!.getCurrencies())
                _currencies.postValue(repo.getCurrenciesFromDb())
            }
        }
    }
}
