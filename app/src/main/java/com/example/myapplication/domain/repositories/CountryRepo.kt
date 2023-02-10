package com.example.myapplication.domain.repositories

import com.example.myapplication.domain.models.Country
import kotlinx.coroutines.flow.Flow
import com.example.myapplication.data.types.Result

interface CountryRepo {
    suspend fun getCountries(): Flow<Result<List<Country>>>
    fun getMessage(): String
}