import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.R
import com.example.pokedex.data.remote.models.PokedexListEntry
import com.example.pokedex.pokemonlist.PokemonListViewModel
import com.example.pokedex.ui.theme.RobotoCondensed

@Composable
fun PokemonListScreen(
  navController: NavController
) {
  Surface(
    color = MaterialTheme.colors.background,
    modifier = Modifier.fillMaxSize()
  ) {
    Column {
      Spacer(modifier = Modifier.height(20.dp))
      Image(
        painter = painterResource(id = R.drawable.ic_pokemon_logo),
        contentDescription = "Pokemon",
        modifier = Modifier
          .fillMaxWidth()
          .align(CenterHorizontally)
      )
      Spacer(modifier = Modifier.height(16.dp))
      PokemonList(navController = navController)
    }
  }
}

@Composable
fun PokemonList(
  navController: NavController,
  viewModel: PokemonListViewModel = hiltViewModel()
) {
  val pokemonList by remember { viewModel.pokemonList }
  val endReached by remember { viewModel.endReached }
  val loadError by remember { viewModel.loadError }
  val isLoading by remember { viewModel.isLoading }
  
  LazyColumn(contentPadding = PaddingValues(8.dp)) {
    val itemCount = if (pokemonList.size % 2 == 0) {
      pokemonList.size / 2
    } else {
      pokemonList.size / 2 + 1
    }
    items(itemCount) {
      if (it >= itemCount - 1 && !endReached && !isLoading) {
        viewModel.loadPokemonPaginated()
      }
      PokedexRow(rowIndex = it, entries = pokemonList, navController = navController)
    }
  }
  
  Box(
    contentAlignment = Center,
    modifier = Modifier.fillMaxSize()
  ) {
    if (isLoading) {
      CircularProgressIndicator(color = MaterialTheme.colors.primary)
    }
    if (loadError.isNotEmpty()) {
      RetrySection(error = loadError) {
        viewModel.loadPokemonPaginated()
      }
    }
  }
  
}

@Composable
fun PokedexEntry(
  entry: PokedexListEntry,
  navController: NavController,
  modifier: Modifier = Modifier,
  viewModel: PokemonListViewModel = hiltViewModel()
) {
  val colors = listOf(Color.White, MaterialTheme.colors.secondary)
  Card(elevation = 4.dp,
    modifier = modifier.padding(horizontal = 12.dp)
      .clickable {
        navController.navigate(
          "pokemonDetailsScreen/${entry.pokemonName}"
        )
      }
  ) {
    Box(
      modifier = modifier
        .background(brush = Brush.radialGradient(colors = colors))
    ) {
      Column {
        Image(
          painter = rememberAsyncImagePainter(entry.imageUrl), contentDescription = entry.pokemonName, modifier = Modifier
            .size(120.dp)
            .align(CenterHorizontally)
        )
        Text(
          text = entry.pokemonName,
          fontFamily = RobotoCondensed,
          fontSize = 20.sp,
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )
      }
    }
  }
}

@Composable
fun PokedexRow(
  rowIndex: Int,
  entries: List<PokedexListEntry>,
  navController: NavController
) {
  Column {
    Row {
      PokedexEntry(
        entry = entries[rowIndex * 2],
        navController = navController,
        modifier = Modifier.weight(1f)
      )
      Spacer(modifier = Modifier.width(16.dp))
      if (entries.size >= rowIndex * 2 + 2) {
        PokedexEntry(
          entry = entries[rowIndex * 2 + 1],
          navController = navController,
          modifier = Modifier.weight(1f)
        )
      } else {
        Spacer(modifier = Modifier.weight(1f))
      }
    }
    Spacer(modifier = Modifier.height(16.dp))
  }
}

@Composable
fun RetrySection(
  error: String,
  onRetry: () -> Unit
) {
  Column {
    Text(error, color = Color.Red, fontSize = 18.sp)
    Spacer(modifier = Modifier.height(8.dp))
    Button(
      onClick = { onRetry() },
      modifier = Modifier.align(CenterHorizontally)
    ) {
      Text(text = "Retry")
    }
  }
}