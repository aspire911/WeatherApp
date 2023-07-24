package com.mdmx.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.mdmx.weatherapp.presentation.view.DetailScreen
import com.mdmx.weatherapp.presentation.view.WeatherScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screens.Main.route) {

                composable(
                    route = Screens.Main.route
                ) {
                    WeatherScreen(
                        navController = navController, viewModel = viewModel,
                        imageLoader = imageLoader
                    )
                }

                composable(
                    route = Screens.Detail.route + "/{weatherId}"
                ) { backStackEntry ->
                    DetailScreen(
                        backStackEntry.arguments?.getString("weatherId"),
                        viewModel,
                        imageLoader
                    )
                }
            }
        }
    }
}
