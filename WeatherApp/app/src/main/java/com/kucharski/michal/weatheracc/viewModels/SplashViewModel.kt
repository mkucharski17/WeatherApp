package com.kucharski.michal.weatheracc.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kucharski.michal.weatheracc.repository.Repository
import com.kucharski.michal.weatheracc.repository.local.getUnits
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    repository: Repository,
    sharedPreferences: SharedPreferences
) : ViewModel() {
    val proceed = MutableLiveData<Boolean>()
    init {
        viewModelScope.launch {
            val animationDone = async {
                delay(2500)
                true
            }
            val fetchDone = async {
                try {
                    val list = repository.getWeatherList().map { it.id }
                    if (list.isNotEmpty()) {
                        repository.fetchWeatherByCityIdList(list, sharedPreferences.getUnits())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                true
            }
            animationDone.await() && fetchDone.await()
            proceed.postValue(true)
        }
    }
}
