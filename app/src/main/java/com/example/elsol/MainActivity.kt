package com.example.elsol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elsol.ui.theme.ElSolTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElSolTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomAppBarExample() }) { innerPadding ->
                    PantallaPrincipal(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier) {
    var cardsElSol by remember { mutableStateOf(CardElSolRepository.getCardsElSol()) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize()
    ) {
        items(cardsElSol) { cardElSol ->
            CardsElSol(
                cardElSol = cardElSol,
                onCardCopied = { cardToCopy ->
                    val copiedCard = cardToCopy.copy()
                    cardsElSol = cardsElSol + copiedCard
                },
                onCardDeleted = { cardToDelete ->
                    cardsElSol = cardsElSol.filter { it != cardToDelete }
                }
            )
        }
    }
}

@Composable
fun BottomAppBarExample() {
    var contador by remember { mutableIntStateOf(0) }
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.onTertiary
    ) {
        IconButton(onClick = { /* TODO */ }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Menu", tint = Color.White)
        }
        IconButton(onClick = { contador++ }) {
            BadgedBox(
                badge = {
                    Badge {
                        Text(contador.toString())
                    }
                }
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favoritos",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun CardsElSol(
    cardElSol: CardElSol,
    onCardCopied: (CardElSol) -> Unit,
    onCardDeleted: (CardElSol) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(265.dp)
            .padding(6.dp)
    ) {
        Image(
            painter = painterResource(id = cardElSol.imageRes),
            contentDescription = "imagenes",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .width(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Text(
                text = cardElSol.nombre,
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color(0xFF000000)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "Opciones",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Copiar") },
                        onClick = {
                            onCardCopied(cardElSol)
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Add, contentDescription = null)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Eliminar") },
                        onClick = {
                            onCardDeleted(cardElSol)
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Delete, contentDescription = null)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgedBoxExample() {
    Box(Modifier.size(100.dp), contentAlignment = Alignment.Center) {
        BadgedBox(badge = { Badge { Text("8") } }) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Favorite"
            )
        }
    }
}


@Composable
fun MySnackbar(cardElSol: CardElSol) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Mostrar Snackbar") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Esto es un Snackbar",
                            actionLabel = "Acción",
                            duration = SnackbarDuration.Indefinite
                        )
                    }
                }
            )
        }
    ) { contentPadding -> Text(cardElSol.nombre, Modifier.padding(contentPadding))
    }
}