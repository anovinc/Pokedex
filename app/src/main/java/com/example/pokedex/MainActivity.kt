package com.example.pokedex

import PokemonListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex.pokemondetails.PokemonDetailsScreen
import com.example.pokedex.ui.theme.PokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      PokedexTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "pokemonListScreen") {
          composable("pokemonListScreen") {
            PokemonListScreen(navController = navController)
          }
          composable(
            "pokemonDetailsScreen/{pokemonName}",
            arguments = listOf(
              navArgument("pokemonName") {
                type = NavType.StringType
              }
            )
          ) {
            val pokemonName = remember {
              it.arguments?.getString("pokemonName")
            }
            if (pokemonName != null) {
              PokemonDetailsScreen(pokemonName = pokemonName, navController = navController )
            }
          }
        }
      }
    }
  }
}