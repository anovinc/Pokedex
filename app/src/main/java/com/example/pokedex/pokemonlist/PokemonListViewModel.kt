package com.example.pokedex.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.palette.graphics.Palette
import com.example.pokedex.data.remote.models.PokedexListEntry
import com.example.pokedex.repository.PokemonRepository
import com.example.pokedex.util.PAGE_SIZE
import com.example.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val repository: PokemonRepository) : ViewModel() {
  
  private var currentPage = 0
  
  var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())
  var loadError = mutableStateOf("")
  var isLoading = mutableStateOf(false)
  var endReached = mutableStateOf(false)
  
  
  init {
    loadPokemonPaginated()
  }
  
  fun loadPokemonPaginated() {
    viewModelScope.launch {
      isLoading.value = true
      val result = repository.getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)
      when (result) {
        is Resource.Success -> {
          endReached.value = currentPage * PAGE_SIZE >= result.data!!.count
          val pokedexEntries = result.data.pokemons.mapIndexed { index, entry ->
            val number = if (entry.url.endsWith("/")) {
              entry.url.dropLast(1).takeLastWhile { it.isDigit() }
            } else {
              entry.url.takeLastWhile { it.isDigit() }
            }
            val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
            PokedexListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
          }
          currentPage++
          
          loadError.value = ""
          isLoading.value = false
          pokemonList.value += pokedexEntries
        }
        is Resource.Error -> {
          loadError.value = result.message!!
          isLoading.value = false
        }
      }
    }
  }
}
