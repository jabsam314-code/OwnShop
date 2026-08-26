package com.example.ui.theme

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

private val DarkColorScheme =
  darkColorScheme(
    primary = OwnEmeraldPrimary,
    onPrimary = Color.White,
    primaryContainer = OwnEmeraldDark,
    onPrimaryContainer = Color.White,
    secondary = OwnNavyDark,
    onSecondary = Color.White,
    secondaryContainer = SlateCardDark,
    onSecondaryContainer = OwnNavyLight,
    tertiary = SaffronAccent,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF7C2D12),
    onTertiaryContainer = Color.White,
    background = SlateDark,
    onBackground = Color(0xFFF1F5F9),
    surface = SlateCardDark,
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF1E2E44),
    onSurfaceVariant = Color(0xFFCBD5E1),
  )

private val LightColorScheme =
  lightColorScheme(
    primary = OwnNavyPrimary,
    onPrimary = Color.White,
    primaryContainer = OwnNavyLight,
    onPrimaryContainer = OwnNavyPrimary,
    secondary = OwnEmeraldPrimary,
    onSecondary = Color.White,
    secondaryContainer = OwnEmeraldLight,
    onSecondaryContainer = OwnEmeraldAccent,
    tertiary = SaffronAccent,
    onTertiary = Color.White,
    tertiaryContainer = SaffronLight,
    onTertiaryContainer = Color(0xFF9A3412),
    background = SlateLight,
    onBackground = SlateTextPrimary,
    surface = Color.White,
    onSurface = SlateTextPrimary,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = SlateTextSecondary,
  )

@Composable
fun OwnShopTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) = OwnShopTheme(darkTheme, dynamicColor, content)

