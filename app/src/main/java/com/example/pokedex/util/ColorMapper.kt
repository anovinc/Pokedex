package com.example.pokedex.util

import androidx.compose.ui.graphics.Color
import com.example.pokedex.ui.theme.*

fun getTypeColor(type: String): Color {
  when(type) {
    "normal" -> return TypeNormal
    "fire" -> return TypeFire
    "water" -> return TypeWater
    "electric" -> return TypeElectric
    "grass" -> return TypeGrass
    "ice" -> return TypeIce
    "fighting" -> return TypeFighting
    "poison" -> return TypePoison
    "ground" -> return TypeGround
    "flying" -> return TypeFlying
    "psychic" -> return TypePsychic
    "bug" -> return TypeBug
    "rock" -> return TypeRock
    "ghost" -> return TypeGhost
    "dragon" -> return TypeDragon
    "dark" -> return TypeDark
    "steel" -> return TypeSteel
    "fairy" -> return TypeFairy
    else -> return TypeNormal
  }
}

fun getStatColor(stat: String): Color {
  return when(stat) {
    "hp" -> HPColor
    "attack" -> AtkColor
    "defense" -> DefColor
    "special-attack" -> SpAtkColor
    "special-defense" -> SpDefColor
    "speed" -> SpdColor
    else -> TypeNormal
  }
}

