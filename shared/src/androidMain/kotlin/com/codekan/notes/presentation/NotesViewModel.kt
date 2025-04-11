package com.codekan.notes.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codekan.notes.domain.entity.Note
import com.codekan.notes.domain.usecase.NoteUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

actual class NotesViewModel actual constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    actual val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    init {
        loadNotes()
    }

    actual fun loadNotes() {
        viewModelScope.launch {
            noteUseCases.getNotes().collect { notesList ->
                _notes.value = notesList
            }
        }
    }

    actual fun addNote(note: Note) {
        viewModelScope.launch {
            Log.d("NotesViewModel", "Adding note: $note")
            noteUseCases.addNote(note)
        }
    }

    actual fun updateNote(note: Note) {
        viewModelScope.launch {
            Log.d("NotesViewModel", "Updating note: $note")
            noteUseCases.updateNote(note)
        }
    }

    actual fun deleteNote(id: Long) {
        viewModelScope.launch {
            noteUseCases.deleteNote(id)
        }
    }
}