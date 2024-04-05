package com.example.catalist.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catalist.model.Breed
import com.example.catalist.model.Data
import com.example.catalist.model.Repository
import com.example.catalist.ui.theme.CatalistTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDetailsScreen(
    breed: Breed,
) {
    Surface {
        Column {
            TopAppBar(
                title = { Text(text = breed.name) }
            )
            Column(modifier = Modifier.padding(16.dp)) {
                // Image(
                //     painter = painterResource(id = breed.imageResId),
                //     contentDescription = "Image of ${breed.name}"
                // )
                Text(text = "Description: ${breed.description}")
                Text(text = "Origin: ${breed.origin}")
                Text(text = "Temperament: ${breed.temperament.joinToString()}")
                Text(text = "Life Span: ${breed.lifeSpan}")
                Text(text = "Weight: ${breed.weight}")
                // Text(text = "Height: ${breed.height}")
            }
        }
    }
}

@Preview
@Composable
fun PreviewBreedDetailsScreen() {
    CatalistTheme {
        Repository.getById(1)?.let {
            BreedDetailsScreen(
                breed = it
            )
        }
    }
}