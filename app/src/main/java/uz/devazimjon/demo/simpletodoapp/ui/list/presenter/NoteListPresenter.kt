package uz.devazimjon.demo.simpletodoapp.ui.list.presenter

import uz.devazimjon.demo.simpletodoapp.data.NoteRepository
import uz.devazimjon.demo.simpletodoapp.ui.list.NoteListActivity
import java.lang.ref.WeakReference

class NoteListPresenter(
    view: NoteListActivity,
    private val repository: NoteRepository
) {
    private val view = WeakReference(view)

    fun getNotes() {
        val notes = repository.getNotes()
        view.get()?.let { it.submitNotes(notes) }
    }
}