package com.dominicwrieden.lifemap.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.dominicwrieden.lifemap.ui.theme.*


private val LightThemeColors = lightColors(
    primary = IndigoIntermediate,
    primaryVariant = IndigoLight,
    onPrimary = Color.White,
    secondary = YellowIntermediate,
    secondaryVariant = YellowLight,
    onSecondary = Color.Black,
    onBackground = Color.Gray
)

@Composable
fun LifeMapTheme(
    content: @Composable () -> Unit
){
    MaterialTheme(
        colors = LightThemeColors,
        typography = typography,
        content = content
    )
}


