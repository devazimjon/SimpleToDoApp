package uz.devazimjon.demo.simpletodoapp.data

import uz.devazimjon.demo.simpletodoapp.data.model.Note

interface NoteRepository {
    fun deleteNote(note: Note)
    fun getNotes(): List<Note>
    fun saveNote(note: Note)
}
