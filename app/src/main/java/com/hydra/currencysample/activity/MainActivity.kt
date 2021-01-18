package com.hydra.currencysample.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
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

    private fun setupUi() {
        adapter = ExchangeRateAdapter()
        recyclerView.adapter = adapter
        money_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val value = money_input.text.toString().toDoubleOrNull()
                value?.let { viewModel.setAmount(it) }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        spinner_currency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.onRateListItemSelected(position)
            }
        }
    }

    private fun setupData() {
        viewModel.getAvailableCurrency()
        viewModel.getExchangeRate()
    }

    private fun setupObservers() {
        viewModel.currencies.observe(this, Observer { list ->
            spinner_currency.adapter = ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                android.R.id.text1,
                list.map { it.title }
            )
            if (viewModel.getDefaultSpinnerIndex() > 0)
                spinner_currency.setSelection(viewModel.getDefaultSpinnerIndex())
        })
        viewModel.rateList.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.amount.observe(this, Observer {
            adapter.updateAmount(it)
        })
        viewModel.ratio.observe(this, Observer {
            adapter.updateRatio(it)
        })
    }

}