package com.codekan.notes.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.codekan.notes.database.NotesDatabase
import com.codekan.notes.domain.entity.Note
import com.codekan.notes.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    driverFactory: DatabaseDriverFactory
) : NoteRepository {
    private val db = NotesDatabase(driverFactory.createDriver())
    private val queries = db.notesQueries

    override suspend fun addNote(note: Note) {
        queries.insertNote(note.title, note.content)
        getNotes()
    }

    override suspend fun updateNote(note: Note) {
        queries.updateNote(note.title, note.content, note.id)
        getNotes()
    }

    override suspend fun deleteNote(id: Long) {
        queries.deleteNote(id)
        getNotes()
    }

    override fun getNotes(): Flow<List<Note>> {
        return queries.selectAllNotes()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { notes ->
                notes.map { note ->
                    Note(
                        id = note.id,
                        title = note.title,
                        content = note.content
                    )
                }
            }
    }
}