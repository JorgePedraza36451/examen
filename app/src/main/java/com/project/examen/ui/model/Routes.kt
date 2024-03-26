package com.project.examen.ui.model

sealed class Routes(val route: String) {
    object ExampleScreen : Routes("screen1")
    object Example2Screen : Routes("screen2")
}