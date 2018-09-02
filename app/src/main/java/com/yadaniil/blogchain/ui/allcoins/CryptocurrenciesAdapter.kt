package com.yadaniil.blogchain.ui.allcoins

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yadaniil.blogchain.R
import com.yadaniil.blogchain.db.models.Cryptocurrency
import com.yadaniil.blogchain.util.AmountFormatter
import java.math.BigDecimal

class CryptocurrenciesAdapter(private val context: Context)
    : ListAdapter<Cryptocurrency, CryptocurrenciesAdapter.CryptocurrencyViewHolder>(CryptocurrencyDiffCallback()) {
//    : RecyclerView.Adapter<CryptocurrenciesAdapter.CryptocurrencyViewHolder>() {

    class CryptocurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemRootLayout: LinearLayout = view.findViewById(R.id.item_root_layout)
        var symbol: TextView = view.findViewById(R.id.item_currency_symbol)
        var name: TextView = view.findViewById(R.id.item_currency_name)
        var usdRate: TextView = view.findViewById(R.id.item_currency_price)
        var btcRate: TextView = view.findViewById(R.id.item_currency_btc_price)
        var hourChange: TextView = view.findViewById(R.id.item_currency_hour_change)
        var dayChange: TextView = view.findViewById(R.id.item_currency_day_change)
        var weekChange: TextView = view.findViewById(R.id.item_currency_week_change)
        var icon: ImageView = view.findViewById(R.id.item_currency_icon)
        var rank: TextView = view.findViewById(R.id.item_currency_rank)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptocurrenciesAdapter.CryptocurrencyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cryptocurrency, parent, false)
        return CryptocurrencyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CryptocurrenciesAdapter.CryptocurrencyViewHolder, position: Int) {
        getItem(position).let { cryptocurrency ->
            with(holder) {
                symbol.text = cryptocurrency.symbol
                name.text = cryptocurrency.name
                usdRate.text = cryptocurrency.priceUsd.toString()
                btcRate.text = "1 BTC"
                usdRate.text = AmountFormatter.formatFiatPrice(BigDecimal(cryptocurrency.priceUsd).toString()) + " USD"
                btcRate.text = AmountFormatter.formatCryptoPrice(BigDecimal(cryptocurrency.priceUsd).toString()) + " BTC"
//            itemRootLayout.setOnClickListener { onClick.onClick(coinHolder, currencyRealm) }
//            itemRootLayout.setOnLongClickListener { onLongClick.onLongClick(coinHolder, currencyRealm); true }
                initRatesChange(this, cryptocurrency)
//            downloadAndSetIcon(icon, currencyRealm, repo, context)
            }
        }
    }

    private fun initRatesChange(holder: CryptocurrencyViewHolder,
                                item: Cryptocurrency) {
        with(holder) {
            hourChange.text = "${item.percentChange1hUsd} %"
            dayChange.text = "${item.percentChange24hUsd} %"
            weekChange.text = "${item.percentChange7dUsd} %"

            if (hourChange.text.startsWith("-"))
                hourChange.setTextColor(context.resources.getColor(R.color.md_red_900))
            else
                hourChange.setTextColor(context.resources.getColor(R.color.md_green_900))

            if (dayChange.text.startsWith("-"))
                dayChange.setTextColor(context.resources.getColor(R.color.md_red_900))
            else
                dayChange.setTextColor(context.resources.getColor(R.color.md_green_900))

            if (weekChange.text.startsWith("-"))
                weekChange.setTextColor(context.resources.getColor(R.color.md_red_900))
            else
                weekChange.setTextColor(context.resources.getColor(R.color.md_green_900))

            arrayOf(hourChange, dayChange, weekChange)
                    .filterNot { it.text.startsWith("-") }
                    .forEach { it.text = "+" + it.text }

            if (item.percentChange1hUsd == null) {
                hourChange.text = "?"
                hourChange.setTextColor(context.resources.getColor(R.color.md_grey_900))
            }
            if (item.percentChange24hUsd == null) {
                dayChange.text = "?"
                dayChange.setTextColor(context.resources.getColor(R.color.md_grey_900))
            }
            if (item.percentChange7dUsd == null) {
                weekChange.text = "?"
                weekChange.setTextColor(context.resources.getColor(R.color.md_grey_900))
            }
        }
    }

}