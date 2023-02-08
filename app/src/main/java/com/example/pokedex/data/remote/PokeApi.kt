package com.example.pokedex.data.remote

import com.example.pokedex.data.remote.responses.PokemonList
import com.example.pokedex.data.remote.responses.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
  
  @GET("pokemon")
  suspend fun getPokemonList(
    @Query("limit") limit: Int,
    @Query("offset") offset: Int
  ): PokemonList
  
  @GET("pokemon/{name}")
  suspend fun getPokemonDetails(
    @Path("name") name: String
  ): PokemonResponse
  
}