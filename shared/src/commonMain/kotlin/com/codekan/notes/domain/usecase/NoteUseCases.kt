package com.codekan.notes.domain.usecase

// This data class groups all the use cases related to notes.
data class NoteUseCases(
    val addNote: AddNoteUseCase,
    val deleteNote: DeleteNoteUseCase,
    val updateNote: UpdateNoteUseCase,
    val getNotes: GetNotesUseCase
)