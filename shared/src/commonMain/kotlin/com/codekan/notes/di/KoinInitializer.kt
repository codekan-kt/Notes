package com.codekan.notes.di

import com.codekan.notes.data.DatabaseDriverFactory
import com.codekan.notes.data.NoteRepositoryImpl
import com.codekan.notes.domain.repository.NoteRepository
import com.codekan.notes.domain.usecase.AddNoteUseCase
import com.codekan.notes.domain.usecase.DeleteNoteUseCase
import com.codekan.notes.domain.usecase.GetNotesUseCase
import com.codekan.notes.domain.usecase.NoteUseCases
import com.codekan.notes.domain.usecase.UpdateNoteUseCase
import com.codekan.notes.presentation.NotesViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class KoinInitializer(
    private val driverFactory: DatabaseDriverFactory
) {
    private var koinApp: KoinApplication? = null

    fun initKoin() {
        if (koinApp != null) return

        koinApp = startKoin {
            modules(appModule())
        }
    }

    fun stopKoin() {
        stopKoin()
        koinApp = null
    }

    val koin: KoinApplication
        get() = koinApp ?: throw IllegalStateException("Koin has not been initialized. Call initKoin() first.")

    private fun appModule() = module {
        single<NoteRepository> { NoteRepositoryImpl(driverFactory) }
        single { AddNoteUseCase(get()) }
        single { DeleteNoteUseCase(get()) }
        single { UpdateNoteUseCase(get()) }
        single { GetNotesUseCase(get()) }
        single { NoteUseCases(get(), get(), get(), get()) }
        single { NotesViewModel(get()) }
    }

    fun getNotesViewModel(): NotesViewModel {
        return koin.koin.get<NotesViewModel>()
    }
}