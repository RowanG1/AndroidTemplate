package com.example.myapplication

import com.example.myapplication.domain.repositories.CountryRepo
import com.example.myapplication.ui.home.HomeViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import com.example.myapplication.data.types.Result
import com.example.myapplication.domain.models.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var countryRepo: CountryRepo

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun settingMainDispatcher() = runTest { // Uses Main’s scheduler
        Mockito.`when`(countryRepo.getCountries()).thenReturn(
            flow {
                emit(Result.Loading)
                emit(Result.Success(data = listOf(Country("Sweden"))))
            }
        )

        val viewModel = HomeViewModel(countryRepo, Dispatchers.Main)

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        assertEquals(Result.Success(data = listOf(Country("Sweden"))), viewModel.uiState.value.countriesFeed)

        collectJob.cancel()
    }

}