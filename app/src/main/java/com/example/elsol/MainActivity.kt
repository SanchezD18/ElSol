package com.example.elsol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDrawerState
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.elsol.ui.theme.ElSolTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElSolTheme {
                val navController = rememberNavController()
                val startDestination = "Build"
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val snackbarHostState = remember { SnackbarHostState() }
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet { Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                        ) {
                            Image(
                                painter = painterResource(R.drawable.elsol),
                                contentDescription = "cabecera menu lateral",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.Crop
                            )
                            Column(modifier = Modifier.padding(10.dp)) {
                                NavigationDrawerItem(
                                    label = { Text("Build") },
                                    selected = false,
                                    icon = { Icon(Icons.Default.Build, contentDescription = null) },
                                    onClick = { navController.navigate("Build") }
                                )
                                NavigationDrawerItem(
                                    label = { Text("Info") },
                                    selected = false,
                                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                                    onClick = { navController.navigate("Info") },
                                )
                                NavigationDrawerItem(
                                    label = { Text("Email") },
                                    selected = false,
                                    icon = { Icon(Icons.Default.Email, contentDescription = null) },
                                    onClick = { /* TODO */ },
                                )
                            }
                            Spacer(Modifier.height(12.dp))
                        }}
                    },
                ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomAppBarExample(drawerState) },
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) { innerPadding ->
                    NavHost(navController, startDestination, Modifier.padding(innerPadding)) {
                    composable("Build") {
                        PantallaPrincipal(
                            snackbarHostState = snackbarHostState
                        )
                    }
                        composable("Info") {
                            PantallaInfo(

                            )
                        }
            }
                }
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {
    var cardsElSol by remember { mutableStateOf(CardElSolRepository.getCardsElSol()) }
    val scope = rememberCoroutineScope()

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
                },
                onCardClick = { card ->
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = card.nombre,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInfo(){
        var loading by remember { mutableStateOf(false) }
        var showDatePicker by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val datePickerState = rememberDatePickerState()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.requiredHeight(30.dp))
            Button(
                onClick = {
                    loading = true
                    scope.launch {
                        delay(2000)
                        loading = false
                    }
                },
                enabled = !loading
            ) {
                Text("Download more info")
            }
            if (loading) {
                CircularProgressIndicator()
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {
                    showDatePicker = true
                }
            ) {
                Text("Visit Planetariums. Select date")
            }
        }
        
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
}



@Composable
fun BottomAppBarExample(drawerState: DrawerState) {
    var contador by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.onTertiary
    ) {
        IconButton(onClick = {
            scope.launch {
                if (drawerState.isClosed) {
                    drawerState.open()
                } else {
                    drawerState.close()
                } }
        }) {
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
        FloatingActionButton(onClick = {/*TODO*/},
            modifier = Modifier.padding(start = 245.dp)
        ){
            Icon(Icons.Filled.Add, contentDescription = "Favoritos")
        }
    }
}

@Composable
fun CardsElSol(
    cardElSol: CardElSol,
    onCardCopied: (CardElSol) -> Unit,
    onCardDeleted: (CardElSol) -> Unit,
    onCardClick: (CardElSol) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        onClick = { onCardClick(cardElSol) },
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



