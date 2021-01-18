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

    private val defaultCurrency = Currency("USD", "United States Dollar")

    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies: LiveData<List<Currency>>
        get() = _currencies

    private var _rateList: List<ExchangeRate>? = null
        set(value) {
            field = value
            value?.let { rateList.postValue(it) }
        }
    val rateList: MutableLiveData<List<ExchangeRate>> = MutableLiveData()

    private var _amount = MutableLiveData<Double>(0.0)
    val amount: LiveData<Double> get() = _amount
    private var _ratioToUSD = MutableLiveData<Double>(1.0)
    val ratio: LiveData<Double> get() = _ratioToUSD

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

    fun setAmount(it: Double) {
        _amount.postValue(it)
    }

    fun onRateListItemSelected(position: Int) {
        val item = _rateList?.firstOrNull { it.country == _currencies.value?.get(position)?.title }
        item?.let {
            _ratioToUSD.postValue(item.rate)
        }
    }

    fun getDefaultSpinnerIndex(): Int {
        return _currencies.value?.indexOf(defaultCurrency) ?: -1
    }

}
