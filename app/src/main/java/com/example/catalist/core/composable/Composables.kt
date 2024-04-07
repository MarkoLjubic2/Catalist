package com.example.catalist.core.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catalist.breeds.domain.Breed
import com.example.catalist.breeds.repository.Data
import com.example.catalist.core.theme.CatalistTheme

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
            modifier = Modifier.padding(all = 10.dp),
            text = breed.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Text(
            modifier = Modifier.padding(all = 10.dp),
            text = "Alternative name" + breed.altName
        )

        Text(
            modifier = Modifier.padding(all = 10.dp),
            text = breed.description.take(250) + if (breed.description.length > 250) "..." else "",
            letterSpacing = 0.5.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            breed.temperament.take(3).forEach { temperament ->
                Box(
                    modifier = Modifier
                        .padding(all = 6.dp)
                ) {
                    SuggestionChip(
                        onClick = {},
                        label = {
                            Text(
                                modifier = Modifier.width(IntrinsicSize.Min),
                                text = temperament
                            )
                        }
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "More",
                modifier = Modifier.padding(bottom = 4.dp) // Add some space between the text and the icon
            )
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
            )
        }
    }
}



@Composable
fun AppIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
        )
    }
}

@Composable
fun NoDataContent(
    id: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "There is no data for id '$id'.",
            fontSize = 18.sp,
        )
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