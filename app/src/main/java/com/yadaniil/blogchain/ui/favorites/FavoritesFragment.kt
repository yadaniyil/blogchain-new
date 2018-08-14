package com.yadaniil.blogchain.ui.favorites

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yadaniil.blogchain.MainActivity

import com.yadaniil.blogchain.R
import kotlinx.android.synthetic.main.favorites_fragment.*

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        app_bar.setNavigationIcon(R.drawable.ic_menu_black_24dp)
        app_bar.setNavigationOnClickListener { (activity as MainActivity).openDrawer() }

        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
