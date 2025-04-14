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
// This is a Koin initializer for the shared module. It initializes Koin and provides the necessary dependencies.
// Koin is a lightweight dependency injection framework for Kotlin.
// It allows you to manage dependencies in a clean and efficient way.
// This file is part of the shared module, which contains use-case injections and view-model injections.

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

    // This is the Koin module that provides the use-cases and view-models for the app.
    // It defines the dependencies and how they are created.
    // This blocks works both on androidApp and iosApp targets.
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