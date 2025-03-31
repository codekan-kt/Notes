package com.codekan.notes.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.codekan.notes.database.NotesDatabase
import kotlinx.coroutines.flow.Flow
import app.cash.sqldelight.db.SqlDriver
import com.codekan.notes.database.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

class NoteRepository(private val driverFactory: DatabaseDriverFactory) {
    private val db = NotesDatabase(driverFactory.createDriver())
    private val _notes = MutableStateFlow<List<Notes>>(emptyList())
    val notes: StateFlow<List<Notes>> = _notes.asStateFlow()

    init {
        updateNotes()
    }

    fun addNote(title: String, content: String) {
        db.notesQueries.insertNote(title, content)
        updateNotes()
    }

    private fun updateNotes() {
        _notes.value = db.notesQueries.selectAllNotes().executeAsList()
    }

    suspend fun getNotes(): Flow<List<Notes>> = db.notesQueries.selectAllNotes()
        .asFlow()
        .mapToList(Dispatchers.Default)

    fun deleteNote(noteId: Long) {
        db.notesQueries.deleteNote(noteId)
        updateNotes()
    }
}