package com.codekan.notes.di

import com.codekan.notes.data.DatabaseDriverFactory
import com.codekan.notes.data.NoteRepository
import com.codekan.notes.usecase.AddNoteUseCase
import com.codekan.notes.usecase.DeleteNoteUseCase
import com.codekan.notes.usecase.GetNotesUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

fun appModule(driverFactory: DatabaseDriverFactory): Module = module {
    single { NoteRepository(driverFactory) }
    single { AddNoteUseCase(get()) }
    single { GetNotesUseCase(get()) }
    single { DeleteNoteUseCase(get()) }

}