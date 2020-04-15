package com.kucharski.michal.weatheracc.viewModels

import androidx.lifecycle.ViewModel
import com.kucharski.michal.weatheracc.repository.Repository
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val repository: Repository): ViewModel() {

}
