package com.kucharski.michal.weatheracc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.kucharski.michal.weatheracc.R
import com.kucharski.michal.weatheracc.adapters.SearchCityAdapter
import com.kucharski.michal.weatheracc.models.SearchCityModel
import com.kucharski.michal.weatheracc.viewModels.SearchCityViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.search_city_fragment.view.*


class SearchCityFragment : DaggerFragment() {

    private lateinit var cityViewModel: SearchCityViewModel
    private val citiesAdapter by lazy{
        SearchCityAdapter{
            Toast.makeText(context,it.name,Toast.LENGTH_SHORT).show()
        }
    }
    private val citiesList = mutableListOf(
        SearchCityModel(
            "1",
            "Warszawa",
            "Poland"
        ),
        SearchCityModel(
            "2",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "3",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "4",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "5",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "6",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "7",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "8",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "9",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "10",
            "Warszawa",
            "Poland"
        ),
        SearchCityModel(
            "11",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "12",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "13",
            "Warszawa",
            "Poland"
        ), SearchCityModel(
            "14",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "15",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "16",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "17",
            "Warszawa",
            "Poland"
        ),SearchCityModel(
            "18",
            "Warszawa",
            "Poland"
        )

    )


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.search_city_fragment, container, false)

        rootView.rvSearch.adapter = citiesAdapter.apply{
            submitList(citiesList)
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        // TODO: Use the ViewModel

    }


}
