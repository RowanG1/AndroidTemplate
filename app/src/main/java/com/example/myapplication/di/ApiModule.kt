package com.example.myapplication.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.networking.ApiService
import com.example.myapplication.data.networking.NetworkInterceptor
import com.example.myapplication.data.repositories.CountryRepoImpl
import com.example.myapplication.domain.repositories.CountryRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

        @Singleton
        @Provides
        fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        @Singleton
        @Provides
        fun providesNetworkInterceptor() = NetworkInterceptor()

        @Singleton
        @Provides
        fun providesOnDeviceHttpLoggingInterceptor(@ApplicationContext appContext: Context): ChuckerInterceptor {
            val chuckerCollector = ChuckerCollector(
                context = appContext,
                // Toggles visibility of the notification
                showNotification = true,
                // Allows to customize the retention period of collected data
                retentionPeriod = RetentionManager.Period.ONE_HOUR
            )

            return ChuckerInterceptor.Builder(appContext)
                .collector(chuckerCollector)
                .build()
        }

        @Singleton
        @Provides
        fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, networkInterceptor: NetworkInterceptor, onDeviceHttpLoggingInterceptor: ChuckerInterceptor): OkHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(onDeviceHttpLoggingInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(networkInterceptor)
                .build()

        @Singleton
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.API_BASE_URL)
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