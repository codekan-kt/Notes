package com.codekan.notes.android.notesedit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codekan.notes.domain.entity.Note
import com.codekan.notes.presentation.NotesViewModel
import org.koin.androidx.compose.koinViewModel

/**
 *
 * A composable function that displays the note edit screen.
 * It allows the user to create a new note or edit an existing one.
 *
 * @param noteId The ID of the note to be edited. If null, a new note will be created.
 * @param navController The navigation controller for navigating between screens.
 * @param viewModel The view model for managing the notes data.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(noteId: Long?, navController: NavController, viewModel: NotesViewModel) {
    // Do not create viewmodel with koinViewModel() every time it creates a new instance everytime.
    // In main activity viewmodel is created with koinViewModel() and passed to this screen.
    // Create separate viewmodel for this screen if you want to use koinViewModel() here.
    // Collecting notes via collectAsState() to observe changes in the notes list.
    val notes by viewModel.notes.collectAsState()
    // Finding the note to be edited based on the noteId.
    val note = notes.find { it.id == noteId }

    var title by remember { mutableStateOf(note?.title ?: "") }
    var content by remember { mutableStateOf(note?.content ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null) "New Note" else title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    // Delete button shown only for existing notes
                    if (noteId != null) {
                        IconButton(onClick = {
                            // Delete the note and navigate back
                            viewModel.deleteNote(noteId)
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Note",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Headline") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Detail") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                maxLines = 10
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank()) {
                        if (noteId != null) {
                            // Update existing note
                            viewModel.updateNote(Note(noteId,title,content))
                        } else {
                            // Add new note
                            viewModel.addNote(Note(0L,title,content))
                        }
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.align(Alignment.End),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Save", fontSize = 16.sp)
            }
        }
    }
}