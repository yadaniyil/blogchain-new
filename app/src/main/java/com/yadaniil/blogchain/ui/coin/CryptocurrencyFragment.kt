package com.yadaniil.blogchain.ui.coin

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.yadaniil.blogchain.MainActivity

import com.yadaniil.blogchain.R
import com.yadaniil.blogchain.db.models.Cryptocurrency
import com.yadaniil.blogchain.di.Injectable
import com.yadaniil.blogchain.util.AmountFormatter
import kotlinx.android.synthetic.main.cryptocurrency_fragment.*
import javax.inject.Inject

class CryptocurrencyFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CryptocurrencyViewModel
    private var cryptocurrencyId: Int = 0
    private var cryptocurrency: Cryptocurrency? = null
    private var favoriteMenuItem: MenuItem? = null
    private var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.cryptocurrency_fragment, container, false)
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cryptocurrencyId = CryptocurrencyFragmentArgs.fromBundle(arguments).cryptocurrencyId

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CryptocurrencyViewModel::class.java)

        viewModel.getCryptocurrency(cryptocurrencyId).observe(this, Observer {
            cryptocurrency = it
            (activity as MainActivity).setToolbarTitle(it.name)
            Glide.with(this).load(it.imageLink).into(icon)
            priceUsd.text = "$${AmountFormatter.formatFiatPrice(it.usdQuote.price.toString())}"
            btcPrice.text = "${AmountFormatter.formatCryptoPrice(it.btcQuote.price.toString())} BTC"
            eurPrice.text = "${AmountFormatter.formatFiatPrice(it.eurQuote.price.toString())} EUR"
            if (it.isFavorite)
                addedToFavoritesIcon()
            else
                removedFromFavoritesIcon()
        })

        viewModel.addOrRemoveEvent.observe(this, Observer {
            if (it)
                Snackbar.make(rootView!!, R.string.added_to_favorites, Snackbar.LENGTH_LONG).show()
            else
                Snackbar.make(rootView!!, R.string.removed_from_favorites, Snackbar.LENGTH_LONG).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_cryptocurrency, menu)
        favoriteMenuItem = menu?.findItem(R.id.action_favorite)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_favorite -> {
                if (isFavorite())
                    viewModel.removeFromFavorites()
                else
                    viewModel.addToFavorites()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun isFavorite(): Boolean {
        return cryptocurrency?.isFavorite ?: false
    }

    private fun removedFromFavoritesIcon() {
        if (favoriteMenuItem != null)
            favoriteMenuItem?.icon = resources.getDrawable(R.drawable.ic_heart_outline_red_24dp)
    }

    private fun addedToFavoritesIcon() {
        if (favoriteMenuItem != null)
            favoriteMenuItem?.icon = resources.getDrawable(R.drawable.ic_heart_red_24dp)
    }

}
