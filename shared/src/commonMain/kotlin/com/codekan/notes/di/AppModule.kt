package com.codekan.notes.di

import com.codekan.notes.data.DatabaseDriverFactory
import com.codekan.notes.data.NoteRepositoryImpl
import com.codekan.notes.domain.usecase.AddNoteUseCase
import com.codekan.notes.domain.usecase.DeleteNoteUseCase
import com.codekan.notes.domain.usecase.GetNotesUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

fun appModule(driverFactory: DatabaseDriverFactory): Module = module {
    single { NoteRepositoryImpl(driverFactory) }
    single { AddNoteUseCase(get()) }
    single { GetNotesUseCase(get()) }
    single { DeleteNoteUseCase(get()) }

}