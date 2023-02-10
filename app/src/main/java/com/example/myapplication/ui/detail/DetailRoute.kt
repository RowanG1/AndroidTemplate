package com.example.myapplication.ui.detail

import androidx.compose.runtime.Composable
import com.example.myapplication.Greeting

/**
  */
@Composable
fun DetailRoute(
    detailViewModel: DetailViewModel,
    isExpandedScreen: Boolean
) {
    // UiState of the HomeScreen
    //val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    Greeting(name = "Detail")
}