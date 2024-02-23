package com.egycode.exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GymDetailsScreen(){
    val viewModel : GymDetailsViewModel = viewModel()

    val gymModel = viewModel.state

    gymModel?.let {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .padding(16.dp)
        ) {
            DefaultIcon(
                icon = Icons.Filled.Place ,
                modifier = Modifier.padding(horizontal = 32.dp) ,
                contentDescription = "Location Gym Icon"
            )
            GymDetails(
                gym = it,
                modifier =Modifier.padding(32.dp),
                horizontal = Alignment.CenterHorizontally
            )
            Text(
                text = if(it.isOpen) "Gym is Open" else "Gym is Closed",
                color = if (it.isOpen) Color.Green else Color.Red
            )
        }


    }
}