package com.example.pokedex.pokemondetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.data.remote.responses.PokemonResponse
import com.example.pokedex.util.getTypeColor


@Composable
fun PokemonDetailsScreen(
  pokemonName: String,
  navController: NavController,
  viewModel: PokemonDetailsViewModel = hiltViewModel()
) {
  viewModel.getPokemonDetails(pokemonName)
  val pokemon by remember {
    viewModel.pokemonResponse
  }
  val isLoading by remember {
    viewModel.isLoading
  }
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colors.background), horizontalAlignment = Alignment.CenterHorizontally
  ) {
    TopAppBar(
      backgroundColor = MaterialTheme.colors.background, elevation = 0.dp, modifier = Modifier.padding(
        top = 20
          .dp
      )
    ) {
      Row() {
        Icon(imageVector = Icons.Default.ArrowBack,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier
            .size(36.dp)
            .offset(16.dp, 16.dp)
            .clickable {
              navController.popBackStack("pokemonListScreen", false)
            })
      }
    }
    PokemonBoxDetails(pokemon = pokemon)
  }
}

@Composable
fun PokemonBoxDetails(pokemon: PokemonResponse) {
  Box(
    modifier = Modifier
      .padding(horizontal = 4.dp)
  ) {
    val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.id}.png"
    Surface(elevation = 4.dp,
      color= Color.White,
      modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 12.dp)
        .padding(top = 80.dp, bottom = 12.dp)
  
        .fillMaxSize()
        .clip(RoundedCornerShape(12.dp))
        
    ) {
    }
    Column(
      Modifier
        .padding(horizontal = 8.dp)
        .fillMaxWidth(),
      horizontalAlignment = CenterHorizontally
    ) {
      Image(
        painter = rememberAsyncImagePainter(url), contentDescription = pokemon.name, modifier = Modifier
          .size(150.dp)
          .clip(CircleShape)
          .align(CenterHorizontally)
      
      )
      Text(
        text = pokemon.name.capitalize(), textAlign = TextAlign.Center, fontFamily = FontFamily.SansSerif, fontSize = 42.sp,
        fontWeight = FontWeight.Bold
      )
      PokemonTypeDisplay(pokemon = pokemon)
        }
      }
    }

@Composable
fun PokemonTypeDisplay(pokemon: PokemonResponse) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 24.dp)
  ) {
    for (type in pokemon.types) {
      Box(
        modifier = Modifier
          .padding(vertical = 8.dp, horizontal = 24.dp)
          .clip(RoundedCornerShape(24.dp))
          .background
            (color = getTypeColor(type.type.name))
          .weight(1f)
      ) {
        Text(
          text = type.type.name.capitalize(),
          color = Color.White,
          textAlign = TextAlign.Center,
          fontSize = 24.sp,
          modifier = Modifier
            .padding
              (
              horizontal = 8.dp, vertical =
            4.dp
            )
            .fillMaxWidth()
        )
      }
    }
  }
}