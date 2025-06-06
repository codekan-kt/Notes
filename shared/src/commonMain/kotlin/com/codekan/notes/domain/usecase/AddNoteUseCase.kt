package com.codekan.notes.domain.usecase

import com.codekan.notes.domain.entity.Note
import com.codekan.notes.domain.repository.NoteRepository

/**
 * Use case for adding a note.
 *
 * @property repository The note repository.
 */
class AddNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.addNote(note)
    }
}