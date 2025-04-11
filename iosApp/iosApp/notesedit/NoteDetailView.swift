//
//  NoteDetailView.swift
//  iosApp
//
//  Created by Efekan YILMAZ on 5.04.2025.
//  Copyright © 2025 orgName. All rights reserved.
//


import SwiftUI
import shared
struct NoteDetailView: View {
    private let viewModel: NotesViewModel
    @Environment(\.dismiss) var dismiss
    @State private var title: String
    @State private var content: String
    private let note: Note?

    init(viewModel: NotesViewModel, note: Note?) {
        self.viewModel = viewModel
        self.note = note
        self._title = State(initialValue: note?.title ?? "")
        self._content = State(initialValue: note?.content ?? "")
    }

    var body: some View {
        NavigationView {
            Form {
                Section(header: Text("Title")) {
                    TextField("Enter title", text: $title)
                }
                Section(header: Text("Content")) {
                    TextEditor(text: $content)
                        .frame(height: 200)
                }
            }
            .navigationTitle(note == nil ? "Add Note" : "Edit Note")
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    Button("Cancel") {
                        dismiss()
                    }
                }
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button("Save") {
                        if let note = note {
                            let updatedNote = Note(id: note.id, title: title, content: content)
                            viewModel.updateNote(note: updatedNote)
                        } else {
                            let newNote = Note(id: Int64.random(in: 1...Int64.max), title: title, content: content)
                            viewModel.addNote(note: newNote)
                        }
                        dismiss()
                    }
                    .disabled(title.isEmpty || content.isEmpty)
                }
            }
        }
    }
}
