package com.example.catalist.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.catalist.breeds.details.breedDetails
import com.example.catalist.breeds.list.breedListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "breeds",
    ) {
        breedListScreen(
            route = "breeds",
            navController = navController,
        )

        breedDetails(
            route = "breeds/{id}",
            navController = navController,
        )
    }

}
