package com.example.myapplication.ui.utils

import Destinations
import androidx.navigation.NavHostController

class NavigationActions(navController: NavHostController) {
    val navigateToDetail: () -> Unit = {
        navController.navigate(Destinations.DETAIL_ROUTE) {
        }
    }
}