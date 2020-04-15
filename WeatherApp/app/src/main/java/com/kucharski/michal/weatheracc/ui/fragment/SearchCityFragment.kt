package com.kucharski.michal.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.adapters.SearchCityAdapter
import com.kucharski.michal.weatheracc.viewModels.SearchCityViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.search_city_fragment.view.*
import javax.inject.Inject

class SearchCityFragment : DaggerFragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModels<SearchCityViewModel> { factory }

    private val searchListAdapter by lazy {
        SearchCityAdapter {
            viewModel.storeCity(it)
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.search_city_fragment, container, false)
            .apply {
                btnConfirm.setOnClickListener { viewModel.searchCity(etSearch.text.toString()) }
                rvCitySearch.adapter = searchListAdapter

                with(viewModel) {
                    cityList.observe(viewLifecycleOwner, Observer {
                        if (it.isNotEmpty()) {
                            searchListAdapter.submitList(it)
                            handleVisibility(textView, rvCitySearch, false)
                        } else {
                            textView.text = "List is empty"
                            handleVisibility(textView, rvCitySearch, true)
                        }
                    })
                    errorMessage.observe(viewLifecycleOwner, Observer {
                        textView.text = it
                        handleVisibility(textView, rvCitySearch, true)
                    })
                }
            }
    }

    private fun handleVisibility(textView: View, recyclerView: RecyclerView, shouldShowError: Boolean) {
        textView.visibility = if (shouldShowError) View.VISIBLE else View.GONE
        recyclerView.visibility = if (shouldShowError) View.GONE else View.VISIBLE
    }
}
