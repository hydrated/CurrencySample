package com.hydra.currencysample.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hydra.currencysample.R
import com.hydra.currencysample.adapter.ExchangeRateAdapter
import com.hydra.currencysample.databinding.ActivityMainBinding
import com.hydra.currencysample.viewmodel.MainViewModel
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by inject()
    private lateinit var adapter: ExchangeRateAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setupUi()
        setupObservers()
        setupData()
    }

    private fun setupUi() {
        adapter = ExchangeRateAdapter()
        binding.recyclerView.adapter = adapter
        binding.moneyInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val value = binding.moneyInput.text.toString().toDoubleOrNull()
                value?.let { viewModel.setAmount(it) }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        binding.spinnerCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
            binding.spinnerCurrency.adapter = ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                android.R.id.text1,
                list.map { it.title }
            )
            if (viewModel.getDefaultSpinnerIndex() > 0)
                binding.spinnerCurrency.setSelection(viewModel.getDefaultSpinnerIndex())
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