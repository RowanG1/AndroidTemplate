package com.example.myapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.models.Country
import com.example.myapplication.domain.repositories.CountryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.myapplication.data.types.Result
import kotlinx.coroutines.flow.*

data class HomeViewModelState(
    val countriesFeed: Result<List<Country>>? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: CountryRepo) : ViewModel() {
    private val viewModelState = MutableStateFlow(HomeViewModelState())

    // UI state exposed to the UI
    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value
        )

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            repository.getCountries().flowOn(Dispatchers.IO).collect { result ->
                viewModelState.update { it.copy(countriesFeed = result) }
            }
        }
    }
}