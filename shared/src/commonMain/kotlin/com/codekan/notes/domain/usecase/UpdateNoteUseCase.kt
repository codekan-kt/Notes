package com.codekan.notes.domain.usecase

import com.codekan.notes.domain.entity.Note
import com.codekan.notes.domain.repository.NoteRepository

class UpdateNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.updateNote(note)
    }
}