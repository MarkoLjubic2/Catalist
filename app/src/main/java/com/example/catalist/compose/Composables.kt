package com.example.catalist.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catalist.model.Breed
import com.example.catalist.model.Data
import com.example.catalist.ui.theme.CatalistTheme

@Composable
fun BreedCard(
    breed: Breed,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
    ) {
        Text(
            modifier = Modifier.padding(all = 16.dp),
            text = breed.name
        )

        Text(
            modifier = Modifier.padding(all = 16.dp),
            text = breed.altName
        )

        Text(
            modifier = Modifier.padding(all = 16.dp),
            text = breed.description
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(all = 16.dp)
                 //   .padding(bottom = 16.dp)
                    .weight(weight = 1f),
                text = breed.temperament.joinToString(", ")
            )

            Icon(
                modifier = Modifier
                    .padding(all = 16.dp)
                  //  .padding(bottom = 0.dp),
                ,imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
fun BreedCardPreview() {
    CatalistTheme{
        BreedCard(
            breed = Data[0],
            onClick = {}
        )
    }
}