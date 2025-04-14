package com.codekan.notes.domain.usecase

import com.codekan.notes.domain.entity.Note
import com.codekan.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for retrieving notes.
 *
 * @property repository The note repository.
 */
class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getNotes()
    }
}