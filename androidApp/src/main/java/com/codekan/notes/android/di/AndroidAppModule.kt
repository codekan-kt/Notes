package com.codekan.notes.android.di

import com.codekan.notes.android.notes.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun androidModule() = module {
    viewModel { NotesViewModel(get(), get(), get()) } // Use case'leri shared'dan alıyor
}