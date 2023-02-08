package com.example.pokedex.data.remote.responses

import com.google.gson.annotations.SerializedName

data class PokemonList(
  val count: Int,
  val next: String,
  val previous: Any,
  @SerializedName("results")
  val pokemons: List<Pokemon>
)
