package com.example.myapplication.di

import com.example.myapplication.data.networking.ApiService
import com.example.myapplication.data.networking.NetworkInterceptor
import com.example.myapplication.data.repositories.CountryRepoImpl
import com.example.myapplication.domain.repositories.CountryRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class  ApiModule {
    companion object {
        private const val BASE_URL = "https://restcountries.eu/rest/v2/"

        @Singleton
        @Provides
        fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        @Singleton
        @Provides
        fun providesHNetworkInterceptor() = NetworkInterceptor()

        @Singleton
        @Provides
        fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, networkInterceptor: NetworkInterceptor): OkHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(networkInterceptor)
                .build()

        @Singleton
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

        @Singleton
        @Provides
        fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

        @Singleton
        @Provides
        fun provideDispatchers(): CoroutineDispatcher = Dispatchers.IO
    }

    @Binds
    abstract fun providesRepository(apiService: CountryRepoImpl): CountryRepo
}