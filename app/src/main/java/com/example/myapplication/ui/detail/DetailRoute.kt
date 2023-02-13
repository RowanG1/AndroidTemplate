package com.example.myapplication.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.Greeting
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 */
@Composable
fun DetailRoute(
    detailViewModel: DetailViewModel,
    isExpandedScreen: Boolean
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //Important part here
                    scope.launch {
                        snackbarHostState.showSnackbar("Hello there")
                    }
                    //
                },
                content = { Icon(imageVector = Icons.Default.Add, contentDescription = "") },
            )
        },
        content = { padding ->
            Column(modifier = Modifier
                .padding(padding)
                .padding(all = 20.dp)) {
                Greeting(name = "Detail")
            }

            SnackbarHost(hostState = snackbarHostState)
        }
    )
}