package com.example.pokedex.repository

import com.example.pokedex.data.remote.responses.PokemonList
import com.example.pokedex.data.remote.responses.PokemonResponse
import com.example.pokedex.util.Resource

interface PokemonRepository {

  suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList>
  
  suspend fun getPokemonDetails(name: String): Resource<PokemonResponse>
  
}