package com.codekan.notes.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codekan.notes.android.notes.NotesScreen
import com.codekan.notes.data.DatabaseDriverFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codekan.notes.android.notesedit.NoteEditScreen
import com.codekan.notes.di.KoinInitializer
import com.codekan.notes.presentation.NotesViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Koin with the database driver factory
        val driverFactory = DatabaseDriverFactory(this)
        // KoinInitializer hosted in commonModule is used to initialize Koin.
        KoinInitializer(driverFactory).initKoin()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotesApp()
                }
            }
        }
    }
}

/**
 * A composable function that hosted all the application and navigation logic.
 */
@Composable
fun NotesApp() {
    val navController = rememberNavController()
    // Init made in MainActivity is used to create viewmodel.
    // koinViewModel() is used to create viewmodel instance.
    // It is used to inject the viewmodel into the composable function.
    val viewModel: NotesViewModel = koinViewModel()
    NavHost(navController = navController, startDestination = "notes_list") {
        composable("notes_list") {
            NotesScreen(navController, viewModel)
        }
        composable("note_edit/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toLongOrNull()
            NoteEditScreen(noteId, navController,viewModel)
        }
        composable("note_edit") {
            NoteEditScreen(null, navController,viewModel)
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        NotesApp()
    }
}
