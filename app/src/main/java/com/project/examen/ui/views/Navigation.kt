package com.project.examen.ui.views

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.examen.ui.model.Routes.ExampleScreen
import com.project.examen.ui.model.Routes.Example2Screen

@Composable
fun NavigationScreenController() {
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = ExampleScreen.route
    ) {
        composable(ExampleScreen.route){ ExampleScreen(navigationController) }
        composable(Example2Screen.route){ }
    }
}