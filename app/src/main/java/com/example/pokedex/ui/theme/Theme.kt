package com.example.pokedex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White

private val DarkColorPalette = darkColors(
  primary = Color.Yellow,
  background = Color(0xFF101010),
  onBackground = White,
  surface = Color(0xFF303030),
  onSurface = White,
  secondary = Color.Black
)

private val LightColorPalette = lightColors(
  primary = Color.Blue,
  background = LightBlue,
  onBackground = Color.Black,
  surface = White,
  onSurface = Color.Black,
  secondary = Color(0xABA6C6FF)
)

@Composable
fun PokedexTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }
  
  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}