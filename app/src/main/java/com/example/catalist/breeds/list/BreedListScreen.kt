package com.example.catalist.breeds.list

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
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
import com.example.catalist.breeds.list.BreedListContract.BreedListState
import com.example.catalist.breeds.list.BreedListContract.BreedListUiEvent

@ExperimentalMaterial3Api
fun NavGraphBuilder.breedListScreen(
    route: String,
    navController: NavController,
) = composable(route = route) {
    val breedListViewModel = viewModel<BreedListViewModel>()
    val state by breedListViewModel.state.collectAsState()

    BreedListScreen(
        state = state,
        eventPublisher = {
            breedListViewModel.setEvent(it)
        },
        onItemClick = { breed ->
            if (state.fetching) {
                println("Fetching in progress, ignoring click event.")
            } else {
                println("Navigating to breed details screen for breed: $breed")
                navController.navigate(route = "breeds/${breed.id}")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedListScreen(
    state: BreedListState,
    eventPublisher: (BreedListUiEvent) -> Unit,
    onItemClick: (Breed) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val isTextFieldFocused = interactionSource.collectIsFocusedAsState().value
    val focusManager = LocalFocusManager.current

    val searchText = remember { mutableStateOf(state.searchQuery) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        })
                    }
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Catalist")
                    }
                )
                TextField(
                    value = searchText.value,
                    onValueChange = { newValue ->
                        searchText.value = newValue
                        eventPublisher(BreedListUiEvent.FindBreed(newValue))
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                    label = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp),
                    leadingIcon = {
                        if (isTextFieldFocused) {
                            IconButton(onClick = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Hide keyboard")
                            }
                        }
                    },
                    trailingIcon = {
                        if (state.searchQuery.isNotEmpty()) {
                            IconButton(onClick = {
                                searchText.value = ""
                                eventPublisher(BreedListUiEvent.FindBreed(""))
                            }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                    },
                    interactionSource = interactionSource
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        })
                    }
            ) {

                BreedList(
                    paddingValues = paddingValues,
                    items = state.filteredBreeds,
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
        }
    )
}

@Composable
private fun BreedList(
    items: List<Breed>,
    paddingValues: PaddingValues,
    onItemClick: (Breed) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(items) { breed ->
            BreedCard(
                breed = breed,
                onClick = {
                    onItemClick(breed)
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
fun PreviewBreedListScreen() {
    CatalistTheme {
        BreedListScreen(
            state = BreedListState(breeds = Data),
            eventPublisher = {},
            onItemClick = {},
        )
    }
}



