package com.kucharski.michal.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.adapters.SearchCityAdapter
import com.kucharski.michal.weatheracc.utils.hideKeyboard
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
            hideKeyboard()
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.search_city_fragment, container, false)
            .apply {
                ivBackArrow.setOnClickListener {
                    findNavController().popBackStack()
                }
                svSearchCity.let {
                    it.setOnClickListener {
                        svSearchCity.isIconified = false
                    }
                    it.setOnQueryTextListener((object : SearchView.OnQueryTextListener {

                        override fun onQueryTextChange(newText: String): Boolean {
                            if (svSearchCity.query.length <= 3)
                                tvEmptyList.text = "Type minimum 3 characters"
                            viewModel.searchCity(svSearchCity.query.toString())
                            return false
                        }

                        override fun onQueryTextSubmit(query: String): Boolean {
                            onQueryTextChange(query)
                            return false
                        }
                    }))
                }

                rvCitySearch.adapter = searchListAdapter

                with(viewModel) {
                    cityList.observe(viewLifecycleOwner, Observer {
                        if (it.isNotEmpty()) {
                            searchListAdapter.submitList(it)
                            handleVisibility(tvEmptyList, rvCitySearch, false)
                        } else {
                            tvEmptyList.text = "No found"
                            handleVisibility(tvEmptyList, rvCitySearch, true)
                        }
                    })
                    errorMessage.observe(viewLifecycleOwner, Observer {
                        if (svSearchCity.query.length >= 3) {
                            tvEmptyList.text = it
                            handleVisibility(tvEmptyList, rvCitySearch, true)
                        }
                    })
                }
            }
    }

    private fun handleVisibility(
        textView: View,
        recyclerView: RecyclerView,
        shouldShowError: Boolean
    ) {
        textView.visibility = if (shouldShowError) View.VISIBLE else View.GONE
        recyclerView.visibility = if (shouldShowError) View.GONE else View.VISIBLE
    }

}
