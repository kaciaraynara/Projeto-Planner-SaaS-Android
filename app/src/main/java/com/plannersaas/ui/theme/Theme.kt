package com.plannersaas.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private fun getColorScheme(darkTheme: Boolean, colorName: String): ColorScheme {
    return when (colorName) {
        "Purple" -> if (darkTheme) {
            darkColorScheme(primary = PurplePrimary, secondary = PurpleSecondary)
        } else {
            lightColorScheme(primary = PurplePrimary, secondary = PurpleSecondary)
        }
        "Green" -> if (darkTheme) {
            darkColorScheme(primary = GreenPrimary, secondary = GreenSecondary)
        } else {
            lightColorScheme(primary = GreenPrimary, secondary = GreenSecondary)
        }
        "Orange" -> if (darkTheme) {
            darkColorScheme(primary = OrangePrimary, secondary = OrangeSecondary)
        } else {
            lightColorScheme(primary = OrangePrimary, secondary = OrangeSecondary)
        }
        "Red" -> if (darkTheme) {
            darkColorScheme(primary = RedPrimary, secondary = RedSecondary)
        } else {
            lightColorScheme(primary = RedPrimary, secondary = RedSecondary)
        }
        else -> if (darkTheme) { // Default Blue
            darkColorScheme(primary = BluePrimary, secondary = BlueSecondary)
        } else {
            lightColorScheme(primary = BluePrimary, secondary = BlueSecondary)
        }
    }
}

@Composable
fun PlannerSaaSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    themeColor: String = "Blue",
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> getColorScheme(darkTheme, themeColor)
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
