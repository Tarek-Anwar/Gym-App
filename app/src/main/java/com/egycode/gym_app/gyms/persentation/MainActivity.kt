package com.egycode.gym_app.gyms.persentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.egycode.gym_app.gyms.persentation.details.GymDetailsScreen
import com.egycode.gym_app.gyms.persentation.gymlist.GymScreen
import com.egycode.gym_app.gyms.persentation.gymlist.GymsViewModel

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
            val vm: GymsViewModel = viewModel()
            GymScreen(vm.state.value, { navController.navigate("gyms/$it") }) { id, isFavourite ->
                vm.toggleFavouriteState(id, isFavourite)
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