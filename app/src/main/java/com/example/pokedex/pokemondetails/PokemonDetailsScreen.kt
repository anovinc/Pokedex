package com.example.pokedex.pokemondetails

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.R
import com.example.pokedex.data.remote.responses.PokemonResponse
import com.example.pokedex.ui.theme.*
import com.example.pokedex.util.getStatColor
import com.example.pokedex.util.getTypeColor
import kotlinx.coroutines.launch


@Composable
fun PokemonDetailsScreen(
  pokemonName: String,
  navController: NavController,
  viewModel: PokemonDetailsViewModel = hiltViewModel()
) {
  viewModel.getPokemonDetails(pokemonName)
  val pokemon = viewModel.pokemonResponse
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
    Surface(
      elevation = 4.dp,
      color = Color.White,
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
      PokemonWeightAndHeight(pokemon = pokemon)
      PokemonDisplayStats(pokemon = pokemon)
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

@Composable
fun PokemonWeightAndHeight(pokemon: PokemonResponse) {
  Column() {
    val weightInKg = (pokemon.weight * 100f) / 1000f
    val heightInMeters = (pokemon.height * 100f) / 1000f
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(top = 8.dp)
    ) {
      Image(painter = painterResource(id = R.drawable.ic_weight), contentDescription = "Weight", Modifier.weight(1f))
      Spacer(
        modifier = Modifier
          .size(1.dp, 60.dp)
          .background(Color.LightGray)
      )
      Image(painter = painterResource(id = R.drawable.ic_height), contentDescription = "Height", Modifier.weight(1f))
    }
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
    ) {
      Text(
        text = "$weightInKg kg", textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier =
        Modifier.weight
          (1f)
      )
      Spacer(
        modifier = Modifier
          .size(1.dp, 20.dp)
          .background(Color.LightGray)
      )
      Text(
        text = "$heightInMeters m",
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.weight(1f)
      )
    }
  }
}

@Composable
fun PokemonDisplayStats(pokemon: PokemonResponse) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp)
  ) {
    Text(
      text = "Base stats:", fontSize = 16.sp, textDecoration = TextDecoration.Underline, fontWeight = FontWeight.SemiBold,
      fontFamily =
      FontFamily
        .SansSerif,
      modifier =
      Modifier.padding(12.dp)
    )
    for (stat in pokemon.stats) {
      Box(
        modifier = Modifier
          .padding(top = 8.dp)
          .clip(RoundedCornerShape(24.dp))
          .background(color = Color.Gray)
          .fillMaxWidth()
          .height(28.dp)
      ) {
        var fraction by remember { mutableStateOf(1f) }
        LaunchedEffect(Unit) {
          animate(initialValue = 0f, targetValue = stat.value/100.toFloat(), animationSpec = tween(1200)) { value, velocity ->
            fraction = value
          }
        }
        Row(
          modifier = Modifier
            .align(CenterStart)
            .fillMaxHeight()
            .fillMaxWidth(fraction)
            .background(color = getStatColor(stat.stat.name))
        ) {
          Text(
            text = mapStatName(stat.stat.name), modifier = Modifier
              .padding(start = 8.dp), fontWeight = FontWeight.Bold,
            fontFamily =
            FontFamily.SansSerif
          )
          Text(
            text = "${stat.value}", modifier = Modifier.padding(end = 8.dp).fillMaxWidth().padding(top = 4.dp), fontWeight =
            FontWeight.Bold,
            textAlign = TextAlign.End,
            fontFamily =
            FontFamily.SansSerif
          )
        }
      }
    }
  }
}

fun mapStatName(stat: String): String {
  return when (stat) {
    "hp" -> "HP"
    "attack" -> "Atk"
    "defense" -> "Def"
    "special-attack" -> "SpAtk"
    "special-defense" -> "SpDef"
    "speed" -> "Spd"
    else -> "empty"
  }
}