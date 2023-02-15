package com.example.myapplication.ui.utils

import Destinations
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.detail.DetailRoute
import com.example.myapplication.ui.detail.DetailViewModel
import com.example.myapplication.ui.home.HomeRoute
import com.example.myapplication.ui.home.HomeViewModel

@Composable
fun NavGraph(
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.HOME_ROUTE,
    goToDetail: () -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Destinations.HOME_ROUTE) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            homeViewModel.init()

            HomeRoute(
                homeViewModel = homeViewModel,
                isExpandedScreen = isExpandedScreen,
                goToDetail =  goToDetail
            )
        }

        composable(Destinations.DETAIL_ROUTE) {
            val detailViewModel: DetailViewModel = hiltViewModel()

            DetailRoute(
                detailViewModel = detailViewModel,
                isExpandedScreen = isExpandedScreen,
            )
        }
    }
}
