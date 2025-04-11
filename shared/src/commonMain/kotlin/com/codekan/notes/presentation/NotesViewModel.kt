package com.codekan.notes.presentation

import com.codekan.notes.domain.entity.Note
import com.codekan.notes.domain.usecase.NoteUseCases
import kotlinx.coroutines.flow.StateFlow


expect class NotesViewModel(noteUseCases: NoteUseCases) {
    val notes: StateFlow<List<Note>>
    fun loadNotes()
    fun addNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote(id: Long)
}