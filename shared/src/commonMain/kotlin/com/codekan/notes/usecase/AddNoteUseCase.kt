package com.codekan.notes.usecase

import com.codekan.notes.data.NoteRepository

class AddNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(title: String, content: String) {
        repository.addNote(title, content)
    }
}