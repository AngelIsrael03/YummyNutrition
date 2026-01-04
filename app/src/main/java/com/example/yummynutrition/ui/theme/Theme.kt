package com.example.yummynutrition.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = Color.Black,
    background = md_theme_dark_background,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    secondary = md_theme_dark_secondary,
    tertiary = md_theme_dark_tertiary
)

@Composable
fun YummyNutritionTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography = Typography,
        content = content
    )
}
