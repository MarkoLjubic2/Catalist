package com.example.catalist.breeds.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.catalist.breeds.domain.Breed
import com.example.catalist.core.composable.AppIconButton
import com.example.catalist.core.composable.NoDataContent
import com.example.catalist.core.theme.CatalistTheme
import coil.compose.rememberAsyncImagePainter


fun NavGraphBuilder.breedDetails(
    route: String,
    navController: NavController,
) = composable(
    route = route,
) { navBackStackEntry ->
    val dataId = navBackStackEntry.arguments?.getString("id")
        ?: throw IllegalArgumentException("id is required.")

    val breedDetailsViewModel = viewModel<BreedDetailsViewModel>(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return BreedDetailsViewModel(breedId = dataId) as T
            }
        },
    )
    val state = breedDetailsViewModel.state.collectAsState()

    BreedDetailsScreen(
        state = state.value,
        onClose = {
            navController.navigateUp()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDetailsScreen(
    state: BreedDetailsState,
    onClose: () -> Unit,
) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.data?.name ?: "Loading")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                navigationIcon = {
                    AppIconButton(
                        imageVector = Icons.Default.ArrowBack,
                        onClick = onClose,
                    )
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when {
                    state.fetching -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    state.error != null -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Error: ${state.error}"
                                )
                            }
                        }
                    }
                    state.data != null -> {
                        item {
                            BreedColumn(
                                breed = state.data,
                            )
                        }
                    }
                    else -> {
                        item {
                            NoDataContent(id = state.breedId)
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun BreedColumn(
    breed: Breed,
) {
    Column(
        modifier = Modifier.padding(all = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Image(
            painter = rememberAsyncImagePainter(model = breed.imageUrl),
            contentDescription = "Image of ${breed.name}",
            modifier = Modifier.size(200.dp)
        )

        if (breed.altName.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append("Commonly called")
                    }
                    append(": " + breed.altName)
                }
            )
        }

        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("Description")
                }
                append(": ${breed.description}")
            }
        )

        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("Origin")
                }
                append(": ${breed.origin}")
            }
        )

        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("Temperament")
                }
                append(": ${breed.temperament.joinToString()}")
            }
        )

        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("Life Span")
                }
                append(": ${breed.lifeSpan} years")
            }
        )

        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("Weight")
                }
                append(": ${breed.weight} kg")
            }
        )

        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("Rarity")
                }
                append(": " + when (breed.rare) {
                    1 -> "Rare"
                    0 -> "Not rare"
                    else -> ""
                })
            }
        )

        CustomIndicator(value = breed.intelligence, "Intelligence")
        CustomIndicator(value = breed.adaptability, "Adaptability")
        CustomIndicator(value = breed.socialNeeds, "Social Needs")
        CustomIndicator(value = breed.energyLevel, "Energy Level")
        CustomIndicator(value = breed.strangerFriendly, "Stranger Friendly")

        val context = LocalContext.current
        val annotatedString = buildAnnotatedString {
            append("Discover more about ")
            withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                append(breed.name)
            }
            addStringAnnotation(
                tag = "URL",
                annotation = breed.wikipediaUrl,
                start = "Discover more about ".length,
                end = "Discover more about ".length + breed.name.length
            )
        }

        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                val url = annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                    .firstOrNull()?.item
                if (url != null) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            },
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

@Composable
fun CustomIndicator(value: Int, behavior: String) {
    Box(
        modifier = Modifier.width(300.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = behavior, modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(bottom = 4.dp),
                fontSize = 14.sp
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 1..5) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(4.dp)
                            .background(
                                if (i <= value) Color.Green else Color.Gray,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBreedDetailsScreen() {
    CatalistTheme {
        BreedDetailsScreen(
            state = BreedDetailsState(breedId = "abys"),
            onClose = {},
        )

    }
}