package com.egycode.gym_app.gyms.persentation.gymlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.egycode.gym_app.gyms.domain.Gym

@Composable
fun GymScreen(
    state: GymsScreenState,
    onItemClick : (Int) -> Unit,
    toggleFavouriteState : (Int , Boolean) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn {
            items(state.gyms) { gym ->
                GymItem(gym = gym,
                    onItemClick = { onItemClick(it) }){ id , isFavourite ->
                    toggleFavouriteState(id , isFavourite)
                }
            }
        }
        if (state.isLoading) CircularProgressIndicator()
        state.error?.let { 
            Text(text = state.error)
        }
    }
}


@Composable
fun GymItem(
    gym: Gym,
    onItemClick : (Int) -> Unit,
    onFavoriteItemClick: (Int , Boolean) -> Unit
    ) {
    val icon = if (gym.isFavourite) Icons.Filled.Favorite
    else Icons.Filled.FavoriteBorder
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(gym.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            DefaultIcon(
                Icons.Filled.LocationOn,
                Modifier.weight(.15f),
                "Location Gym Icon"
            )
            GymDetails(
                gym = gym,
                modifier = Modifier.weight(.70f)
            )
            DefaultIcon(icon, Modifier.weight(.15f), "Favourite Gym Icon") {
                onFavoriteItemClick(gym.id , gym.isFavourite)
            }
        }
    }
}

@Composable
fun DefaultIcon(
    icon: ImageVector,
    modifier: Modifier,
    contentDescription: String,
    onclick: () -> Unit = {}
) {
    Image(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onclick()
            }, colorFilter = ColorFilter.tint(
            Color.DarkGray
        )
    )
}

@Composable
fun GymDetails(
    gym: Gym,
    modifier: Modifier,
    horizontal: Alignment.Horizontal = Alignment.Start
) {
    Column(
        modifier.padding(8.dp),
        horizontalAlignment = horizontal
    ) {
        Text(
            text = gym.name,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Magenta
        )
        Text(
            text = gym.place,
            style = MaterialTheme.typography.bodyMedium
        )

    }
}

