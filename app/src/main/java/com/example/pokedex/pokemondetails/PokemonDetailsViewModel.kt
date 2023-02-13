package com.example.pokedex.pokemondetails

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.remote.responses.PokemonResponse
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(private val repository: PokemonRepository) : ViewModel() {
  
  var pokemonResponse = mutableStateOf(PokemonResponse())
  var loadError = mutableStateOf("")
  var isLoading = mutableStateOf(true)
  
  fun getPokemonDetails(name: String) {
    viewModelScope.launch {
      when (val response = repository.getPokemonDetails(name.lowercase())) {
        is Resource.Success -> {
          pokemonResponse.value = response.data!!
          isLoading.value = false
        }
        else -> {
          loadError.value = response.message.toString()
          isLoading.value = false
        }
      }
    }
  }
}