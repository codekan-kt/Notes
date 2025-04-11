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
            .onAppear {
                observeNotes()
                viewModel.loadNotes()
            }
            .onDisappear {
            }
        }
    }

    private func observeNotes() {
        cancellable = viewModel.notes.collect(collector: FlowCollector { value in
            if let notesList = value as? [Note] {
                DispatchQueue.main.async {
                    self.notes = notesList
                }
            }
        })
    }

    private func deleteNotes(at offsets: IndexSet) {
        offsets.forEach { index in
            let note = notes[index]
            viewModel.deleteNote(id: note.id)
        }
    }
}

// FlowCollector'ı uygulamak için bir yardımcı struct
private struct FlowCollector: Kotlinx_coroutines_coreFlowCollector {
    let emit: (Any?) -> Void

    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        emit(value)
        completionHandler(nil) // Hata yoksa nil döner
    }
}
