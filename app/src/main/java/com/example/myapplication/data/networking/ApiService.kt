package com.example.myapplication.data.networking

import com.example.myapplication.domain.models.Country
import retrofit2.http.GET

interface ApiService {
    @GET("region/europe")
    suspend fun getCountries(): List<Country>
}