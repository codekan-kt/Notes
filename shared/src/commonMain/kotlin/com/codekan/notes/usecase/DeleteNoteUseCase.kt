package com.codekan.notes.usecase

import com.codekan.notes.data.NoteRepository

class DeleteNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(noteId: Long) {
        repository.deleteNote(noteId)
    }
}