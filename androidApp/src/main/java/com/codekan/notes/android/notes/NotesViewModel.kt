package com.codekan.notes.android.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codekan.notes.usecase.AddNoteUseCase
import com.codekan.notes.usecase.DeleteNoteUseCase
import com.codekan.notes.usecase.GetNotesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(
    private val addNoteUseCase: AddNoteUseCase,
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {
    val notes = getNotesUseCase()
    init {

    }


    fun addNote(title: String, content: String) {
        CoroutineScope(Dispatchers.Main).launch {
            addNoteUseCase(title, content)
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(noteId)
        }
    }
}