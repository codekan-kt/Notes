import SwiftUI
import shared

struct NotesListView: View {
    private let viewModel: NotesViewModel
    @State private var notes: [Note] = []
    @State private var showingAddNoteSheet = false

    init() {
        self.viewModel = KoinHelper.shared.getNotesViewModel()
    }

    var body: some View {
        NavigationView {
            List {
                ForEach(notes, id: \.id) { note in
                    NavigationLink(destination: NoteDetailView(viewModel: viewModel, note: note)) {
                        VStack(alignment: .leading) {
                            Text(note.title)
                                .font(.headline)
                            Text(note.content)
                                .font(.subheadline)
                                .foregroundColor(.gray)
                        }
                    }
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
                do {
                    for try await notesList in viewModel.notes {
                        notes = notesList
                    }
                } catch {
                    print("Error observing notes: \(error)")
                }
                viewModel.loadNotes()
            }
        }
    }

    private func deleteNotes(at offsets: IndexSet) {
        offsets.forEach { index in
            let note = notes[index]
            viewModel.deleteNote(id: note.id)
        }
    }
}
