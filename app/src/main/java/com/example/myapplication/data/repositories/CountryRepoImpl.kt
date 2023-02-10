package com.example.myapplication.data.repositories

import com.example.myapplication.data.networking.ApiService
import com.example.myapplication.domain.repositories.CountryRepo
import kotlinx.coroutines.flow.flow
import com.example.myapplication.data.types.Result
import com.example.myapplication.domain.models.Country

class CountryRepoImpl (private val apiService: ApiService): CountryRepo {
    override suspend fun getCountries() = flow {
        emit(Result.Loading)
        try {
          //  val result = apiService.getCountries()
            emit(Result.Success(listOf(Country("Austria"), Country("Sweden"))))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getMessage(): String {
        return "Hi Rowan"
    }

}