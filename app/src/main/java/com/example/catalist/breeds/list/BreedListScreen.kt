package com.example.catalist.breeds.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.catalist.core.composable.BreedCard
import com.example.catalist.breeds.domain.Breed
import com.example.catalist.breeds.repository.Data
import com.example.catalist.core.theme.CatalistTheme

@ExperimentalMaterial3Api
fun NavGraphBuilder.breedListScreen(
    route: String,
    navController: NavController,
) = composable(route = route) {
    val breedListViewModel = viewModel<BreedListViewModel>()
    val state by breedListViewModel.state.collectAsState()

    BreedListScreen(
        state = state,
        onItemClick = {
            navController.navigate(route = "breeds/${it.id}")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedListScreen(
    state: BreedListState,
    onItemClick: (Breed) -> Unit,
) {
    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(title = { Text(text = "BreedsList") })
                Divider()
            }
        },
        content = {

            BreedList(
                paddingValues = it,
                items = state.breeds,
                onItemClick = onItemClick,
            )

            if (state.breeds.isEmpty()) {
                when (state.fetching) {
                    true -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    false -> {
                        if (state.error != null) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                val errorMessage = when (state.error) {
                                    is BreedListState.ListError.ListUpdateFailed ->
                                        "Failed to load. Error message: ${state.error.cause?.message}."
                                }
                                Text(text = errorMessage)
                            }
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(text = "No breeds found.")
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun BreedList(
    items: List<Breed>,
    paddingValues: PaddingValues,
    onItemClick: (Breed) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        items.forEach {
            Column {
                key(it.name) {
                    BreedCard(
                        breed = it,
                        onClick = {
                            onItemClick(it)
                        },
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

    }
}

@Preview
@Composable
fun PreviewBreedListScreen() {
    CatalistTheme {
        BreedListScreen(
            state = BreedListState(breeds = Data),
            onItemClick = {},
        )
    }
}



