package com.hydra.currencysample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hydra.core.db.Currency
import com.hydra.core.repo.CurrencyRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val repo: CurrencyRepo
) : ViewModel() {

    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies : LiveData<List<Currency>>
        get() = _currencies

    fun getDataFromHttp() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val data = repo.getDataFromHttp()
            }
        }
    }

    fun getAvailableCurrency() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val data = repo.getDataFromHttp()
                repo.storeCurrenciesToDo(data!!.getCurrencies())
                _currencies.postValue(repo.getDataFromDb())
            }
        }
    }
}
