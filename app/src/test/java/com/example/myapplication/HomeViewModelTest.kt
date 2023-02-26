package com.example.myapplication

import com.example.myapplication.domain.repositories.CountryRepo
import com.example.myapplication.ui.home.HomeViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import com.example.myapplication.data.types.Result
import com.example.myapplication.domain.models.Country
import com.example.myapplication.ui.home.HomeViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var countryRepo: CountryRepo

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun settingMainDispatcher() = runTest { // Uses Main’s scheduler
        Mockito.`when`(countryRepo.getCountries()).thenReturn(
            flow {
                emit(Result.Loading)
                delay(20)
                emit(Result.Success(data = listOf(Country("Sweden"))))
            }
        )

        val viewModel by lazy {
            HomeViewModel(
                countryRepo,
                UnconfinedTestDispatcher(testScheduler)
            )
        }

        val emitted = mutableListOf<HomeViewModelState>()
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.toList(emitted)
        }

        advanceTimeBy(50)

        assertEquals(
            listOf(
                HomeViewModelState(),
                HomeViewModelState(Result.Loading),
                HomeViewModelState(Result.Success(data = listOf(Country("Sweden"))))
            ), emitted
        )

        collectJob.cancel()
    }

}