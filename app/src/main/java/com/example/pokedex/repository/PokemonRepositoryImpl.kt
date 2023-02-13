package com.example.pokedex.repository

import com.example.pokedex.data.remote.PokeApi
import com.example.pokedex.data.remote.responses.PokemonList
import com.example.pokedex.data.remote.responses.PokemonResponse
import com.example.pokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityScoped
class PokemonRepositoryImpl @Inject constructor(private val api: PokeApi): PokemonRepository {
  
  override suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList>  = withContext(Dispatchers.IO){
    val response = try {
      api.getPokemonList(limit, offset)
      
    } catch (e: Exception) {
      return@withContext Resource.Error("An unknown error occurred.")
    }
    return@withContext Resource.Success(response)
  }
  
  override suspend fun getPokemonDetails(name: String): Resource<PokemonResponse> = withContext(Dispatchers.IO){
    val response = try {
      api.getPokemonDetails(name)
    } catch (e: Exception) {
      return@withContext Resource.Error("An unknown error occurred.")
    }
    return@withContext Resource.Success(response)
  }
  
}