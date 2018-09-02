package com.yadaniil.blogchain.ui.allcoins

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yadaniil.blogchain.Application
import com.yadaniil.blogchain.MainActivity

import com.yadaniil.blogchain.R
import kotlinx.android.synthetic.main.all_coins_fragment.*
import timber.log.Timber
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

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(AllCoinsViewModel::class.java)
        viewModel.observeCoins().observe(this, Observer { coins ->
            cryptocurrenciesAdapter.submitList(coins.data)
        })
    }
}
