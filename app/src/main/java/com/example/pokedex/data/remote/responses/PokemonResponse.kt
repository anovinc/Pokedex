package com.example.pokedex.data.remote.responses


data class PokemonResponse(
  val stats: List<Stats> = listOf(),
  val height: Int = 0,
  val id: Int = 0,
  val name: String = "",
  val types: List<Type> = listOf(),
  val weight: Int = 0
  )
