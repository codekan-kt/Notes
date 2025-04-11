package com.codekan.notes.domain.usecase

data class NoteUseCases(
    val addNote: AddNoteUseCase,
    val deleteNote: DeleteNoteUseCase,
    val updateNote: UpdateNoteUseCase,
    val getNotes: GetNotesUseCase
)