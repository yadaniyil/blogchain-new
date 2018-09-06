package com.yadaniil.blogchain.ui.addportfolio

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.yadaniil.blogchain.R
import com.yadaniil.blogchain.di.Injectable
import com.yadaniil.blogchain.util.hideKeyboard
import com.yadaniil.blogchain.util.showKeyboard
import kotlinx.android.synthetic.main.add_portfolio_fragment.*
import javax.inject.Inject

class AddPortfolioFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: AddPortfolioViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(AddPortfolioViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.add_portfolio_fragment, container, false)
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        portfolioName.requestFocus()
        portfolioName.showKeyboard(activity as Context)


    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_add_portfolio, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.addPortfolioAction) {
            viewModel.addPortfolio(portfolioName.text.toString(), portfolioDescription.text.toString())
            portfolioName.hideKeyboard(activity as Context)
            portfolioName.findNavController().navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
