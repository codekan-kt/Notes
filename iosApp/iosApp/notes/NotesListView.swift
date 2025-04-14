import SwiftUI
import shared

//
//  NotesListView.swift
//  iosApp
//
//  Created by Efekan YILMAZ on 5.04.2025.
//  Copyright © 2025 Codekan Labs. All rights reserved.
//

struct NotesListView: View {
    private let viewModel: NotesViewModel
    @State private var notes: [Note] = []
    @State private var showingAddNoteSheet = false

    // Viewmodel accessor handled in init step.
    init() {
        self.viewModel = KoinHelper.shared.getNotesViewModel()
    }

    // Native SwiftUI for listing Notes
    var body: some View {
        NavigationView {
            if notes.isEmpty {
                // Empty state UI when no notes are available
                VStack(spacing: 8) {
                    Text("Add your first note")
                        .font(.title2) // titleMedium eşdeğeri
                        .foregroundColor(.primary.opacity(0.7))
                    Button(action: {
                        showingAddNoteSheet = true
                    }) {
                        Text("Press to add a new note")
                            .font(.subheadline) // bodyMedium eşdeğeri
                            .foregroundColor(.accentColor)
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .navigationTitle("Notes")
                .toolbar {
                    ToolbarItem(placement: .navigationBarTrailing) {
                        Button(action: {
                            showingAddNoteSheet = true
                        }) {
                            Image(systemName: "plus")
                        }
                    }
                }
                .sheet(isPresented: $showingAddNoteSheet) {
                    NoteDetailView(viewModel: viewModel, note: nil)
                }
                .task {
                    // Retrieving all the notes in the database.
                    do {
                        for try await notesList in viewModel.notes {
                            // State Flow aka "AsyncSequence" Reactive approach.
                            notes = notesList
                        }
                    } catch {
                        print("Error observing notes: \(error)")
                    }
                    // Loads all the notes from database.
                    viewModel.loadNotes()
                }
            } else {
                List {
                    ForEach(Array(notes.enumerated()), id: \.element.id) { (index, note) in
                        NavigationLink(destination: NoteDetailView(viewModel: viewModel, note: note)) {
                            VStack(alignment: .leading, spacing: 4) {
                                Text(note.title)
                                    .font(.headline)
                                Text(note.content)
                                    .font(.subheadline)
                                    .foregroundColor(.gray)
                                    .lineLimit(2)
                            }
                            .padding()
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .background(noteColor(for: index))
                            .clipShape(RoundedRectangle(cornerRadius: 12))
                        }
                        .listRowBackground(Color.clear)
                        .listRowInsets(EdgeInsets(top: 8, leading: 16, bottom: 8, trailing: 16))
                    }
                    .onDelete(perform: deleteNotes)
                }
                .navigationTitle("Notes")
                .toolbar {
                    ToolbarItem(placement: .navigationBarTrailing) {
                        Button(action: {
                            showingAddNoteSheet = true
                        }) {
                            Image(systemName: "plus")
                        }
                    }
                }
                .sheet(isPresented: $showingAddNoteSheet) {
                    NoteDetailView(viewModel: viewModel, note: nil)
                }
                .task {
                    // Retrieving all the notes in the database.
                    do {
                        for try await notesList in viewModel.notes {
                            // State Flow aka "AsyncSequence" Reactive approach.
                            notes = notesList
                        }
                    } catch {
                        print("Error observing notes: \(error)")
                    }
                    // Loads all the notes from database.
                    viewModel.loadNotes()
                }
            }
        }
    }

    // Swipe to delete action. Triggers viewModel use-cases which successfully running on coroutines.
    private func deleteNotes(at offsets: IndexSet) {
        offsets.forEach { index in
            let note = notes[index]
            viewModel.deleteNote(id: note.id)
        }
    }

    // Color picker for the note cells.
    private func noteColor(for index: Int) -> Color {
        switch index % 4 {
        case 0:
            return Color(red: 1.0, green: 0.976, blue: 0.769) // Yellow: #FFF9C4
        case 1:
            return Color(red: 0.702, green: 0.898, blue: 0.988) // Blue: #B3E5FC
        case 2:
            return Color(red: 1.0, green: 0.8, blue: 0.737) // Red: #FFCCBC
        default:
            return Color(red: 0.784, green: 0.902, blue: 0.788) // Green: #C8E6C9
        }
    }
}
