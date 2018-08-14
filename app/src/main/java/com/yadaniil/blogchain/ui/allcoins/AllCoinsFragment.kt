package com.yadaniil.blogchain.ui.allcoins

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yadaniil.blogchain.MainActivity

import com.yadaniil.blogchain.R
import kotlinx.android.synthetic.main.all_coins_fragment.*

class AllCoinsFragment : Fragment() {

    private lateinit var viewModel: AllCoinsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.all_coins_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        app_bar.setNavigationIcon(R.drawable.ic_menu_black_24dp)
        app_bar.setNavigationOnClickListener { (activity as MainActivity).openDrawer() }
        viewModel = ViewModelProviders.of(this).get(AllCoinsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
