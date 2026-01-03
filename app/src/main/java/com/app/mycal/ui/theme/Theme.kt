package com.app.mycal.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6366F1), // Original color
    onPrimary = Color.White,
    primaryContainer = Color(0xFF4338CA),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF94A3B8),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF64748B),
    onSecondaryContainer = Color.White,
    tertiary = Color(0xFFD6BCFA),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF4A148C),
    onTertiaryContainer = Color.White,
    error = Color(0xFFCF6679),
    errorContainer = Color(0xFFB00020),
    onError = Color.Black,
    onErrorContainer = Color.White,
    background = Color(0xFF1C1B1F),
    onBackground = Color.White,
    surface = Color(0xFF1C1B1F),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    outline = Color(0xFF909090),
    inverseOnSurface = Color(0xFF1C1B1F),
    inverseSurface = Color(0xFFE6E1E5),
    inversePrimary = Color(0xFF7E84EC),
    surfaceTint = Color(0xFF6366F1),
    outlineVariant = Color(0xFF49454F),
    scrim = Color(0xFF000000),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6366F1), // Original color
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0E7FF),
    onPrimaryContainer = Color(0xFF1E293B),
    secondary = Color(0xFF94A3B8),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFCFD8DC),
    onSecondaryContainer = Color(0xFF263238),
    tertiary = Color(0xFFBBDEFB),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF90CAF9),
    onTertiaryContainer = Color(0xFF212121),
    error = Color(0xFFB00020),
    errorContainer = Color(0xFFFDD8DF),
    onError = Color.White,
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EE),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E),
    inverseOnSurface = Color(0xFFF4EFF4),
    inverseSurface = Color(0xFF313033),
    inversePrimary = Color(0xFFBFB3FF),
    surfaceTint = Color(0xFF6366F1),
    outlineVariant = Color(0xFFCAC4D0),
    scrim = Color(0xFF000000),
)

@Composable
fun MycalTheme(darkTheme: Boolean = isSystemInDarkTheme(), dynamicColor: Boolean = true, content: @Composable () -> Unit) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
