package com.egycode.gym_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymsAroundApp()
        }
    }
}

@Composable
private fun GymsAroundApp() {
    val navController = rememberNavController()
    val uri = "https://www.gymstarekapp.com"
    NavHost(navController = navController, startDestination = "gyms") {
        composable(route = "gyms") {
            GymScreen {
                navController.navigate("gyms/$it")
            }
        }
        composable(
            route = "gyms/{gym_id}",
            arguments = listOf(
                navArgument("gym_id") {
                    type = NavType.IntType
                },
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "$uri/{gym_id}"
                })

        ) {
            GymDetailsScreen()
        }

    }
}