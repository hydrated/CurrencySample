package com.hydra.currencysample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hydra.core.model.ExchangeRate
import com.hydra.currencysample.R
import com.hydra.currencysample.databinding.ItemExchangeRateBinding
import java.text.NumberFormat

class ExchangeRateAdapter :
    ListAdapter<ExchangeRate, ExchangeRateAdapter.RateHolder>(ExchangeRateDiff()) {
    private var amount: Double = 0.0
    private var ratioToUsb: Double = 1.0
    private lateinit var binding: ItemExchangeRateBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateHolder {
        return RateHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_exchange_rate,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RateHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun updateAmountAndRatio(amount: Double, ratio: Double) {
        this.amount = amount
        this.ratioToUsb = ratio
        notifyDataSetChanged()
    }

    fun updateAmount(amount: Double) {
        updateAmountAndRatio(amount, ratioToUsb)
    }

    fun updateRatio(ratio: Double) {
        updateAmountAndRatio(amount, ratio)
    }

    inner class RateHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(rate: ExchangeRate) {
            with(view) {
                binding.title.text = rate.country
                binding.exchangeRate.text =
                    NumberFormat.getNumberInstance().format(rate.rate / ratioToUsb)
                binding.value.text =
                    NumberFormat.getNumberInstance().format(rate.rate * amount / ratioToUsb)
            }
        }
    }
}


class ExchangeRateDiff : DiffUtil.ItemCallback<ExchangeRate>() {
    override fun areItemsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate) =
        oldItem.country == newItem.country

    override fun areContentsTheSame(oldItem: ExchangeRate, newItem: ExchangeRate) =
        oldItem == newItem
}