package com.example.pokedex.data.remote.responses


data class PokemonResponse(
  val abilities: List<Ability> = listOf(),
  val height: Int = 0,
  val id: Int = 0,
  val name: String = "",
  val types: List<Type> = listOf(),
  val weight: Int = 0
  )
