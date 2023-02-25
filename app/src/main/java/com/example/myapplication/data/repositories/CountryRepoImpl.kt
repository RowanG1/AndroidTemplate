package com.example.myapplication.data.repositories

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.networking.ApiService
import com.example.myapplication.domain.repositories.CountryRepo
import kotlinx.coroutines.flow.flow
import com.example.myapplication.data.types.Result
import com.example.myapplication.domain.models.Country
import timber.log.Timber
import javax.inject.Inject

class CountryRepoImpl @Inject constructor(private val apiService: ApiService) : CountryRepo {
    override suspend fun getCountries() = flow {
        emit(Result.Loading)
        try {
            Timber.i("Base URL is: ${BuildConfig.API_BASE_URL}")
            val result = apiService.getCountries()
            emit(Result.Success(listOf(Country("Austria"), Country("Sweden"))))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getMessage(): String {
        return "Hi Rowan"
    }

}