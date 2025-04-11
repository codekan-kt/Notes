package com.codekan.notes.presentation

import com.codekan.notes.domain.entity.Note
import com.codekan.notes.domain.usecase.NoteUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

actual class NotesViewModel actual constructor(
    private val noteUseCases: NoteUseCases
) {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    actual val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    init {
        loadNotes()
    }

    actual fun loadNotes() {
        scope.launch {
            noteUseCases.getNotes().collect { notesList ->
                _notes.value = notesList
            }
        }
    }

    actual fun addNote(note: Note) {
        scope.launch {
            noteUseCases.addNote(note)
        }
    }

    actual fun updateNote(note: Note) {
        scope.launch {
            noteUseCases.updateNote(note)
        }
    }

    actual fun deleteNote(id: Long) {
        scope.launch {
            noteUseCases.deleteNote(id)
        }
    }
}