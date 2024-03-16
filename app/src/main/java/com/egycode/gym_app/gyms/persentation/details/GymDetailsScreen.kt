package com.egycode.gym_app.gyms.persentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.egycode.gym_app.gyms.persentation.gymlist.DefaultIcon
import com.egycode.gym_app.gyms.persentation.gymlist.GymDetails

@Composable
fun GymDetailsScreen() {

    val viewModel: GymDetailsViewModel = viewModel()
    val state = viewModel.state.value

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        state.gym?.let {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                DefaultIcon(
                    icon = Icons.Filled.Place,
                    modifier = Modifier.padding(horizontal = 32.dp),
                    contentDescription = "Location Gym Icon"
                )
                GymDetails(
                    gym = it,
                    modifier = Modifier.padding(32.dp),
                    horizontal = Alignment.CenterHorizontally
                )
                Text(
                    text = if (it.isOpen) "Gym is Open" else "Gym is Closed",
                    color = if (it.isOpen) Color.Green else Color.Red
                )
            }

        }

        if (state.isLoading) CircularProgressIndicator()
        if(state.gym == null)
        {
            Text(text = state.error.toString())
        }
    }
}