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
import com.bumptech.glide.Glide
import com.yadaniil.blogchain.R
import com.yadaniil.blogchain.db.models.Cryptocurrency
import com.yadaniil.blogchain.util.AmountFormatter
import java.math.BigDecimal

class CryptocurrenciesAdapter(private val context: Context)
    : ListAdapter<Cryptocurrency, CryptocurrenciesAdapter.CryptocurrencyViewHolder>(CryptocurrencyDiffCallback()) {

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
                // TODO Enable ranks after remake cryptocurrency item to constraint layout
//                rank.text = cryptocurrency.rank.toString()
                symbol.text = cryptocurrency.symbol
                name.text = cryptocurrency.name
                usdRate.text = AmountFormatter.formatFiatPrice(BigDecimal(
                        cryptocurrency.usdQuote.price).toString()) + " USD"
                btcRate.text = AmountFormatter.formatCryptoPrice(BigDecimal(
                        cryptocurrency.btcQuote.price).toString()) + " BTC"
//            itemRootLayout.setOnClickListener { onClick.onClick(coinHolder, currencyRealm) }
//            itemRootLayout.setOnLongClickListener { onLongClick.onLongClick(coinHolder, currencyRealm); true }
                initRatesChange(this, cryptocurrency)
                Glide.with(context).load(cryptocurrency.imageLink).into(holder.icon)
            }
        }
    }

    private fun initRatesChange(holder: CryptocurrencyViewHolder,
                                item: Cryptocurrency) {
        with(holder) {
            hourChange.text = "${AmountFormatter.formatPercentage(BigDecimal(item.usdQuote.percentChange1h))} %"
            dayChange.text = "${AmountFormatter.formatPercentage(BigDecimal(item.usdQuote.percentChange24h))} %"
            weekChange.text = "${AmountFormatter.formatPercentage(BigDecimal(item.usdQuote.percentChange7d))} %"

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
        }
    }

}