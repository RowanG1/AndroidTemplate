package com.example.myapplication.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.Greeting
import com.example.myapplication.data.types.Result

/**
  */
@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    isExpandedScreen: Boolean,
    goToDetail: () -> Unit = {},
) {
    // UiState of the HomeScreen
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    Column {
        Greeting(name = "Rowan- Home")
        Button(onClick = {
            //your onclick code here
            goToDetail.invoke()
        }) {
            Text(text = "Simple Button")
        }

        LazyColumn {
            // Add a single item
            item {
                Text(text = "Heading")
            }

            val feed = uiState.countriesFeed
            if (feed is Result.Success) {
                items(feed.data.count()) { index ->
                    Text(text = "Item: $index")
                }
            }
        }
    }
}