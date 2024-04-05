package com.example.catalist.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catalist.model.Breed
import com.example.catalist.model.Data
import com.example.catalist.ui.theme.CatalistTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedListScreen(
    items: List<Breed>,
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
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(it),
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
    )
}

@Preview
@Composable
fun PreviewBreedListScreen() {
    CatalistTheme {
        BreedListScreen(
            items = Data,
            onItemClick = {},
        )
    }
}



