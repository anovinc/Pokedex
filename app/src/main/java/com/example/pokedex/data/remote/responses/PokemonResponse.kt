package com.example.pokedex.data.remote.responses


data class PokemonResponse(
  val abilities: List<Ability>,
  val height: Int,
  val id: Int,
  val name: String,
  val types: List<Type>,
  val weight: Int)
