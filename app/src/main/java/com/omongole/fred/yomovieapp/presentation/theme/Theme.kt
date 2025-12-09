package com.omongole.fred.yomovieapp.presentation.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// optimised for Dark Mode only
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF66B3FF),
    secondary = Color(0xFF81C784),
    tertiary = Color(0xFFFF8A65),
    // We make background transparent here so we can draw a gradient behind it in MainActivity
    background = Color.Transparent,
    surface = Color(0x0DFFFFFF),
    surfaceVariant = Color(0xFF2D2D2D),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color(0xFFE0E0E0),
    onSurface = Color(0xFFE0E0E0),
    outline = Color(0xFF404040),
    outlineVariant = Color(0xFF5A5A5A),
)

// Kept for reference, but won't be used
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0066CC),
    secondary = Color(0xFF66BB6A),
    tertiary = Color(0xFFFF7043),
    background = Color(0xFFF8F9FA),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFF1A1A1A),
    onSurface = Color(0xFF1A1A1A),
)

@Composable
fun YoMovieAppTheme(
    // FORCE DARK MODE: We ignore the system setting and default to true
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    // Always use DarkColorScheme
    val colorScheme = DarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            // Set status bar to transparent so the gradient shows through
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Black.toArgb() // Solid black for nav bar looks cleaner

            val insetsController = WindowCompat.getInsetsController(window, view)
            // Force white icons on status bar
            insetsController.isAppearanceLightStatusBars = false
            insetsController.isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Ensure you have this defined
        content = content
    )
}