package com.example.catalist.compose

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catalist.model.Repository

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "breeds",
    ) {
        breedsListScreen (
            route = "breeds",
            navController = navController,
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
private fun NavGraphBuilder.breedsListScreen(
    route: String,
    navController: NavController,
) {
    composable(route = route) {
        BreedListScreen(
            items = Repository.allBreeds(),
            onItemClick = {
                navController.navigate(route = "breeds/${it.id}")
            },
        )
    }
}