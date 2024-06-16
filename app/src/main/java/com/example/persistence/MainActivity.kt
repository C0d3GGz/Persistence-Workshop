package com.example.persistence

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.persistence.Datenbank.ToDoListDatabase
import com.example.persistence.ui.theme.PersistenceTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ToDoListDatabase::class.java,
            "database.db"
        ).build()
    }
    private val viewModel by viewModels<ToDoViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ToDoViewModel(db.dao) as T
                }
            }
        }
    )

    private val dataStore by lazy { DataStore(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isAlwaysOnScreen by dataStore.alwaysOnScreenFlow.collectAsState(initial = false)
            val isWorkshopChecked by dataStore.workshopFlow.collectAsState(initial = false)
            val coroutineScope = rememberCoroutineScope()

            if (isAlwaysOnScreen) window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            else window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

            PersistenceTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "start") {
                    composable("start") {
                        StartScreen(
                            navController = navController,
                            modifier = Modifier
                        )
                    }
                    composable("settings") {
                        SettingsScreen(
                            navController,
                            modifier = Modifier,
                            isAlwaysOnScreen = isAlwaysOnScreen,
                            onAlwaysOnScreenChange = { enabled ->
                                coroutineScope.launch {
                                    dataStore.setAlwaysOnScreen(enabled)
                                }
                            },
                            isWorkshopChecked = isWorkshopChecked,
                            onWorkshopCheckedChange = { enabled ->
                                coroutineScope.launch {
                                    dataStore.setWorkshop(enabled)
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StartScreen(navController: NavHostController, modifier: Modifier = Modifier) {
        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("To-Do") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    actions = {
                        IconButton(onClick = { navController.navigate("settings") }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings Button"
                            )
                        }
                    }
                )
            },
            content = {
                val state by viewModel._state.collectAsState()
                ToDoScreen(
                    state = state,
                    viewModel = viewModel,
                    modifier = Modifier.padding(it)
                )
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SettingsScreen(
        navController: NavHostController,
        modifier: Modifier = Modifier,
        isAlwaysOnScreen: Boolean,
        onAlwaysOnScreenChange: (Boolean) -> Unit,
        isWorkshopChecked: Boolean,
        onWorkshopCheckedChange: (Boolean) -> Unit
    ) {

        Scaffold(
            modifier = modifier,
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Arrow Back Button"
                            )
                        }
                    },
                    title = { Text("Settings") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                )
            },
            content = {
                Column(
                    modifier = Modifier.padding(it)
                ) {
                    SettingsItem(
                        text = "Display Always On",
                        isChecked = isAlwaysOnScreen,
                        onCheckedChange = onAlwaysOnScreenChange
                    )
                    SettingsItem(
                        text = "Workshop",
                        isChecked = isWorkshopChecked,
                        onCheckedChange = onWorkshopCheckedChange
                    )
                }
            }
        )
    }
}

@Composable
fun SettingsItem(
    text: String,
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.padding(10.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
        )
    }
}
