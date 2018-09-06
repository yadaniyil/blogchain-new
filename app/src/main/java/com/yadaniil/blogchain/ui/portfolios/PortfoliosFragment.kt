package com.yadaniil.blogchain.ui.portfolios

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController

import com.yadaniil.blogchain.R
import com.yadaniil.blogchain.di.Injectable
import kotlinx.android.synthetic.main.portfolios_fragment.*
import javax.inject.Inject

class PortfoliosFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PortfoliosViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
                .get(PortfoliosViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.portfolios_fragment, container, false)
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toAddPortfolioFab.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_portfolioAction_to_addPortfolioFragment)
        )
    }

}
