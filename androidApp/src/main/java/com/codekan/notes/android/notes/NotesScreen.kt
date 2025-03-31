package com.codekan.notes.android.notes


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codekan.notes.database.Notes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@Composable
fun NotesScreen() {
    val viewModel: NotesViewModel = getViewModel()
    val notes by viewModel.notes.collectAsState()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var deletedNote by remember { mutableStateOf<Notes?>(null) }
    var showAddedAnimation by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                // Not Ekleme Alanı
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Yeni Not",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Başlık") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = content,
                            onValueChange = { content = it },
                            label = { Text("İçerik") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = {
                                if (title.isNotBlank() && content.isNotBlank()) {
                                    viewModel.addNote(title, content)
                                    title = ""
                                    content = ""
                                    showAddedAnimation = true
                                    coroutineScope.launch {
                                        delay(1000)
                                        showAddedAnimation = false
                                    }
                                }
                            },
                            modifier = Modifier.align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Ekle", fontSize = 16.sp)
                        }
                    }
                }

                // Not Eklendi Animasyonu
                AnimatedVisibility(
                    visible = showAddedAnimation,
                    enter = fadeIn(animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    Text(
                        text = "Not Eklendi!",
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Not Listesi
                LazyColumn {
                    items(notes) { note ->
                        var offsetX by remember { mutableStateOf(0f) }
                        NoteItem(
                            note = note,
                            onSwipe = {
                                deletedNote = note
                                coroutineScope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Not silindi",
                                        actionLabel = "Geri Al",
                                        duration = SnackbarDuration.Short
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        deletedNote?.let { viewModel.addNote(it.title, it.content) }
                                    }
                                    deletedNote = null
                                }
                            },
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .offset(x = offsetX.dp)
                                .pointerInput(Unit) {
                                    detectHorizontalDragGestures(
                                        onDragEnd = {
                                            if (offsetX < -100f) { // Sağdan sola swipe
                                                offsetX = 0f
                                                coroutineScope.launch { /*onSwipe()*/ }
                                            } else {
                                                offsetX = 0f
                                            }
                                        },
                                        onHorizontalDrag = { _, dragAmount ->
                                            offsetX += dragAmount
                                            if (offsetX > 0f) offsetX = 0f // Sadece sola kaydır
                                        }
                                    )
                                }
                        )
                    }
                }
            }
        }
    )
}



