package com.dominicwrieden.lifemap.feature.main.model

sealed class StartScreen {
    object Login: StartScreen()
    object Map: StartScreen()
}