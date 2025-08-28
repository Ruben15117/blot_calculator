package com.example.blotihashiv.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BleakDarkPrimary,
    onPrimary = BleakDarkOnPrimary,
    primaryContainer = BleakDarkPrimaryContainer,
    onPrimaryContainer = BleakDarkOnPrimaryContainer,
    secondary = BleakDarkSecondary,
    onSecondary = BleakDarkOnSecondary,
    secondaryContainer = BleakDarkSecondaryContainer,
    onSecondaryContainer = BleakDarkOnSecondaryContainer,
    tertiary = BleakDarkTertiary,
    onTertiary = BleakDarkOnTertiary,
    tertiaryContainer = BleakDarkTertiaryContainer,
    onTertiaryContainer = BleakDarkOnTertiaryContainer,
    error = BleakDarkError,
    onError = BleakDarkOnError,
    errorContainer = BleakDarkErrorContainer,
    onErrorContainer = BleakDarkOnErrorContainer,
    background = BleakDarkBackground,
    onBackground = BleakDarkOnBackground,
    surface = BleakDarkSurface,
    onSurface = BleakDarkOnSurface,
    surfaceVariant = BleakDarkSurfaceVariant,
    onSurfaceVariant = BleakDarkOnSurfaceVariant,
    outline = BleakDarkOutline
)

private val LightColorScheme = lightColorScheme(
    primary = BleakLightPrimary,
    onPrimary = BleakLightOnPrimary,
    primaryContainer = BleakLightPrimaryContainer,
    onPrimaryContainer = BleakLightOnPrimaryContainer,
    secondary = BleakLightSecondary,
    onSecondary = BleakLightOnSecondary,
    secondaryContainer = BleakLightSecondaryContainer,
    onSecondaryContainer = BleakLightOnSecondaryContainer,
    tertiary = BleakLightTertiary,
    onTertiary = BleakLightOnTertiary,
    tertiaryContainer = BleakLightTertiaryContainer,
    onTertiaryContainer = BleakLightOnTertiaryContainer,
    error = BleakLightError,
    onError = BleakLightOnError,
    errorContainer = BleakLightErrorContainer,
    onErrorContainer = BleakLightOnErrorContainer,
    background = BleakLightBackground,
    onBackground = BleakLightOnBackground,
    surface = BleakLightSurface,
    onSurface = BleakLightOnSurface,
    surfaceVariant = BleakLightSurfaceVariant,
    onSurfaceVariant = BleakLightOnSurfaceVariant,
    outline = BleakLightOutline
)

@Composable
fun BlotiHashivTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}