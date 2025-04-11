package com.codekan.notes.domain.repository

import com.codekan.notes.domain.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: Long)
    fun getNotes(): Flow<List<Note>>
}