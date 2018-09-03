package com.yadaniil.blogchain.ui.allcoins

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yadaniil.blogchain.Application
import com.yadaniil.blogchain.MainActivity
import com.yadaniil.blogchain.R
import com.yadaniil.blogchain.db.models.Cryptocurrency
import com.yadaniil.blogchain.util.gone
import com.yadaniil.blogchain.util.startRefreshing
import com.yadaniil.blogchain.util.stopRefreshing
import com.yadaniil.blogchain.util.visible
import com.yadaniil.blogchain.vo.Resource
import com.yadaniil.blogchain.vo.Status
import kotlinx.android.synthetic.main.all_coins_fragment.*
import javax.inject.Inject

class AllCoinsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AllCoinsViewModel
    private lateinit var cryptocurrenciesAdapter: CryptocurrenciesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.all_coins_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Application.component?.inject(this)

        app_bar.setNavigationIcon(R.drawable.ic_menu_black_24dp)
        app_bar.setNavigationOnClickListener { (activity as MainActivity).openDrawer() }

        viewManager = LinearLayoutManager(activity)
        cryptocurrenciesAdapter = CryptocurrenciesAdapter(activity as Context)
        cryptocurrenciesList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = cryptocurrenciesAdapter
        }

        swipeRefreshList.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(AllCoinsViewModel::class.java)

        viewModel.coins.observe(this, Observer { coins ->
            when (coins.status) {
                Status.LOADING -> renderLoading()
                Status.ERROR -> renderError()
                Status.SUCCESS -> renderSuccess(coins)
            }
        })
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
