package com.example.pokedex.pokemondetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.remote.responses.PokemonResponse
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(private val repository: PokemonRepository) : ViewModel() {
  
  var pokemonResponse by mutableStateOf(PokemonResponse())
    private set
  var fraction by mutableStateOf(0f)
    private set
  var loadError = mutableStateOf("")
  var isLoading = mutableStateOf(true)
  
  fun getPokemonDetails(name: String) {
    viewModelScope.launch {
      when (val response = repository.getPokemonDetails(name.lowercase())) {
        is Resource.Success -> {
          pokemonResponse = response.data!!
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