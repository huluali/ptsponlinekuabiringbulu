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
    primary = KuaGreenPrimaryDark,
    onPrimary = Color(0xFF003823),
    primaryContainer = KuaGreenPrimaryContainerDark,
    onPrimaryContainer = Color(0xFFE8F5EE),
    secondary = KuaGoldSecondaryDark,
    onSecondary = Color(0xFF3B2F00),
    secondaryContainer = KuaGoldSecondaryContainerDark,
    onSecondaryContainer = Color(0xFFFEF9E7),
    tertiary = KuaTealTertiary,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF075985),
    onTertiaryContainer = Color(0xFFE0F2FE),
    background = KuaBackgroundDark,
    surface = KuaSurfaceDark,
    surfaceVariant = KuaSurfaceVariantDark,
    onBackground = KuaOnSurfaceDark,
    onSurface = KuaOnSurfaceDark,
    onSurfaceVariant = KuaOnSurfaceVariantDark,
    outline = KuaCardBorderDark
  )

private val LightColorScheme =
  lightColorScheme(
    primary = PangadakkangRed,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFEBEE),
    onPrimaryContainer = Color(0xFF7F1D1D),
    secondary = PangadakkangYellow,
    onSecondary = Color(0xFF78350F),
    secondaryContainer = Color(0xFFFFFBEB),
    onSecondaryContainer = Color(0xFF92400E),
    tertiary = KuaTealTertiary,
    onTertiary = Color.White,
    tertiaryContainer = KuaTealTertiaryContainer,
    onTertiaryContainer = KuaTealOnTertiaryContainer,
    background = KuaBackgroundLight,
    surface = KuaSurfaceLight,
    surfaceVariant = KuaSurfaceVariantLight,
    onBackground = KuaOnSurfaceLight,
    onSurface = KuaOnSurfaceLight,
    onSurfaceVariant = KuaOnSurfaceVariantLight,
    outline = KuaOutlineLight
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Use our branded Kemenag Emerald & Gold theme by default
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

