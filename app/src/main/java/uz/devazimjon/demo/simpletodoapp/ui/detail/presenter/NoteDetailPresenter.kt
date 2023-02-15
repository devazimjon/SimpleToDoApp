package uz.devazimjon.demo.simpletodoapp.ui.detail.presenter

import uz.devazimjon.demo.simpletodoapp.data.NoteRepository
import uz.devazimjon.demo.simpletodoapp.data.model.Note
import uz.devazimjon.demo.simpletodoapp.ui.detail.NoteDetailActivity
import java.lang.ref.WeakReference

class NoteDetailPresenter(
    view: NoteDetailActivity,
    private val repository: NoteRepository
) {
    private val view = WeakReference(view)

    fun saveNote(note: Note) {
        repository.saveNote(note)
    }

    fun deleteNote(note: Note) {
        repository.deleteNote(note)
    }
}