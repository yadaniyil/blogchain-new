package com.yadaniil.blogchain.ui.allcoins

import androidx.recyclerview.widget.DiffUtil
import com.yadaniil.blogchain.db.models.Cryptocurrency

class CryptocurrencyDiffCallback : DiffUtil.ItemCallback<Cryptocurrency>() {
    override fun areItemsTheSame(oldItem: Cryptocurrency, newItem: Cryptocurrency): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cryptocurrency, newItem: Cryptocurrency): Boolean {
        return oldItem.name == newItem.name && oldItem.symbol == newItem.symbol
    }

}