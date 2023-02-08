package com.example.pokedex.data.remote.responses


data class Ability(
  val ability: AbilityX,
)
data class AbilityX(
  val name: String,
  val url: String
)
