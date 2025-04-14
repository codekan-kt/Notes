package com.codekan.notes.domain.usecase

import com.codekan.notes.domain.repository.NoteRepository

/**
 * Use case for deleting a note.
 *
 * @property repository The note repository.
 */
class DeleteNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteNote(id)
    }
}