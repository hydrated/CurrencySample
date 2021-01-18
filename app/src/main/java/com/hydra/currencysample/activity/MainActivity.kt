package com.hydra.currencysample.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hydra.currencysample.R
import com.hydra.currencysample.adapter.ExchangeRateAdapter
import com.hydra.currencysample.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by inject()
    private lateinit var adapter: ExchangeRateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUi()
        setupObservers()
        setupData()
    }

    private fun setupUi()  {
        adapter = ExchangeRateAdapter()
        recyclerView.adapter = adapter
    }

    private fun setupData() {
        viewModel.getAvailableCurrency()
        viewModel.getExchangeRate()
    }

    private fun setupObservers() {
        viewModel.currencies.observe(this, Observer {
        })
        viewModel.rateList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

}