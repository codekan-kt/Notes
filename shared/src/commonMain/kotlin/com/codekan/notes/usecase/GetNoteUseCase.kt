package com.codekan.notes.usecase

import com.codekan.notes.data.NoteRepository
import com.codekan.notes.database.Notes
import kotlinx.coroutines.flow.StateFlow

class GetNotesUseCase(private val repository: NoteRepository) {
    operator fun invoke(): StateFlow<List<Notes>> = repository.notes
}