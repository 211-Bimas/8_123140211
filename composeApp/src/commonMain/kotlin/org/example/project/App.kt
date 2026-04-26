package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch

// --- Menggunakan ObservableSettings ---
import com.russhwolf.settings.ObservableSettings
import org.example.project.db.DatabaseDriverFactory
import org.example.project.db.NotesDatabase
import org.example.project.data.NotesRepository
import org.example.project.data.SettingsManager
import org.example.project.viewmodel.NotesUiState

import org.example.project.components.BottomNav
import org.example.project.navigation.BottomNavItem
import org.example.project.navigation.Screen
import org.example.project.ui.*
import org.example.project.viewmodel.ProfileViewModel
import org.example.project.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    driverFactory: DatabaseDriverFactory,
    settings: ObservableSettings
) {
    // 1. INISIALISASI DATABASE & REPOSITORY
    val database = remember { NotesDatabase(driverFactory.createDriver()) }
    val repository = remember { NotesRepository(database) }
    val settingsManager = remember { SettingsManager(settings) }

    val navController = rememberNavController()

    // 2. ViewModels
    val profileViewModel = remember { ProfileViewModel() }
    val profileUiState by profileViewModel.uiState.collectAsState()

    // NotesViewModel sekarang menggunakan Repository (Database)
    val notesViewModel = remember { NotesViewModel(repository, settingsManager) }
    val notesUiState by notesViewModel.uiState.collectAsState()

    // 3. Mengambil List Catatan dari State Database
    val notesList = when (val state = notesUiState) {
        is NotesUiState.Success -> state.notes
        else -> emptyList() // Jika sedang Loading atau Empty, list dikosongkan sementara
    }

    val colorScheme = if (profileUiState.isDarkMode) darkColorScheme() else lightColorScheme()

    // SETUP DRAWER & COROUTINE
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // SETUP CURRENT ROUTE (Untuk mengubah judul TopBar secara dinamis)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    MaterialTheme(colorScheme = colorScheme) {
        // BUNGKUS DENGAN NAVIGATION DRAWER
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = "Menu Navigasi",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider()
                    Spacer(Modifier.height(8.dp))

                    // Item di dalam Drawer
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Info, contentDescription = "Tentang") },
                        label = { Text("Tentang Aplikasi") },
                        selected = currentRoute == Screen.About.route,
                        onClick = {
                            navController.navigate(Screen.About.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            scope.launch { drawerState.close() } // Tutup drawer setelah diklik
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        ) {
            // SCAFFOLD UTAMA
            Scaffold(
                topBar = {
                    val isTopLevel = currentRoute in listOf(
                        BottomNavItem.Notes.route,
                        BottomNavItem.Favorites.route,
                        BottomNavItem.Profile.route,
                        Screen.About.route
                    )

                    if (isTopLevel) {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = when(currentRoute) {
                                        BottomNavItem.Notes.route -> "Catatan Ku"
                                        BottomNavItem.Favorites.route -> "Favorit"
                                        BottomNavItem.Profile.route -> "Profil Mahasiswa"
                                        Screen.About.route -> "Tentang"
                                        else -> "Aplikasi"
                                    },
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Buka Menu")
                                }
                            },
                            // <--- PERBAIKAN WARNING: Pakai topAppBarColors --->
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background
                            )
                        )
                    }
                },
                bottomBar = { BottomNav(navController = navController) }
            ) { paddingValues ->

                // NAVHOST DENGAN FITUR SEARCH, DELETE, DAN PENYESUAIAN ID
                NavHost(
                    navController = navController,
                    startDestination = BottomNavItem.Notes.route,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    // --- BOTTOM TABS ---
                    composable(BottomNavItem.Notes.route) {
                        val searchQuery by notesViewModel.searchQuery.collectAsState()
                        NoteListScreen(
                            notes = notesList,
                            searchQuery = searchQuery,
                            onSearchQueryChange = { query -> notesViewModel.updateSearchQuery(query) },
                            onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id.toInt())) },
                            onAddClick = { navController.navigate(Screen.AddNote.route) },
                            // <--- INI TAMBAHAN SAMBUNGAN BUAT TOMBOL FAVORIT --->
                            onFavoriteClick = { note -> notesViewModel.toggleFavorite(note) }
                        )
                    }
                    composable(BottomNavItem.Favorites.route) {
                        FavoritesScreen()
                    }
                    composable(BottomNavItem.Profile.route) {
                        ProfileScreen(
                            uiState = profileUiState,
                            onEditProfile = { name, bio -> profileViewModel.updateProfile(name, bio) },
                            onToggleDarkMode = { isDark -> profileViewModel.toggleDarkMode(isDark) }
                        )
                    }

                    // --- DRAWER SCREEN ---
                    composable(Screen.About.route) {
                        AboutScreen()
                    }

                    // --- DETAIL & EDIT SCREENS ---
                    composable(
                        route = Screen.NoteDetail.route,
                        arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
                        // Mengambil detail 1 catatan asli dari Database!
                        val note by notesViewModel.getNoteById(noteId.toLong()).collectAsState(initial = null)

                        NoteDetailScreen(
                            note = note,
                            onBack = { navController.popBackStack() },
                            onEdit = { id -> navController.navigate(Screen.EditNote.createRoute(id.toInt())) },
                            onDelete = { id -> notesViewModel.deleteNote(id) }
                        )
                    }

                    composable(Screen.AddNote.route) {
                        AddNoteScreen(
                            onSave = { title, content -> notesViewModel.addNote(title, content) },
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(
                        route = Screen.EditNote.route,
                        arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getInt("noteId") ?: -1
                        val note by notesViewModel.getNoteById(noteId.toLong()).collectAsState(initial = null)

                        EditNoteScreen(
                            note = note,
                            onSave = { id, title, content -> notesViewModel.updateNote(id, title, content) },
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}