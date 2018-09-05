package com.yadaniil.blogchain.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yadaniil.blogchain.R
import com.yadaniil.blogchain.db.models.Cryptocurrency
import com.yadaniil.blogchain.di.Injectable
import com.yadaniil.blogchain.ui.CryptocurrencyAdapterDirection
import com.yadaniil.blogchain.ui.allcoins.CryptocurrenciesAdapter
import com.yadaniil.blogchain.util.gone
import com.yadaniil.blogchain.util.startRefreshing
import com.yadaniil.blogchain.util.stopRefreshing
import com.yadaniil.blogchain.util.visible
import com.yadaniil.blogchain.vo.Resource
import com.yadaniil.blogchain.vo.Status
import kotlinx.android.synthetic.main.favorites_fragment.*
import javax.inject.Inject

class FavoritesFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var cryptocurrenciesAdapter: CryptocurrenciesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.favorites_fragment, container, false)
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(FavoritesViewModel::class.java)

        viewManager = LinearLayoutManager(activity)
        cryptocurrenciesAdapter = CryptocurrenciesAdapter(activity as Context,
                CryptocurrencyAdapterDirection.FAVORITE)
        cryptocurrenciesList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = cryptocurrenciesAdapter
        }

        swipeRefreshList.setOnRefreshListener { viewModel.refresh() }

        viewModel.coins.observe(this, Observer { coins ->
            when (coins.status) {
                Status.LOADING -> renderLoading()
                Status.ERROR -> renderError()
                Status.SUCCESS -> renderSuccess(coins)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateFavoritesFromDb()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_cryptocurrencies, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun renderLoading() {
        if (cryptocurrenciesAdapter.itemCount > 0) {
            swipeRefreshList.visible()
            progressBar.gone()
            swipeRefreshList.startRefreshing()
        } else {
            swipeRefreshList.gone()
            progressBar.visible()
            swipeRefreshList.stopRefreshing()
        }
    }

    private fun renderError() {}

    private fun renderSuccess(coins: Resource<List<Cryptocurrency>>) {
        progressBar.gone()
        swipeRefreshList.visible()
        swipeRefreshList.stopRefreshing()
        cryptocurrenciesAdapter.submitList(coins.data)
    }

}
