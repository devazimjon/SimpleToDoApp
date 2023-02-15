package uz.devazimjon.demo.simpletodoapp.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.devazimjon.demo.simpletodoapp.data.NoteRepository
import uz.devazimjon.demo.simpletodoapp.data.model.Note

class NoteRepositoryImpl(private val preferences: SharedPreferences) : NoteRepository {

    private val gson = Gson()

    override fun deleteNote(note: Note) {
        val notes = getNotes().filter { it.id != note.id }
        save(notes)
    }

    override fun getNotes(): List<Note> {
        val notesString = preferences.getString(NOTES_KEY, null)
        val notes = notesString ?: return emptyList()
        return gson.fromJson(notes)
    }

    override fun saveNote(note: Note) {
        val index = getNotes().indexOfFirst { it.id == note.id }

        var notes: List<Note>
        if (index > -1) {
            notes = getNotes().toMutableList()
            notes[index] = note
        } else {
            notes = listOf(note) + getNotes()
        }
        save(notes)
    }

    private fun save(notes: List<Note>) {
        preferences.edit {
            putString(NOTES_KEY, gson.toJson(notes))
        }
    }

    companion object {
        const val NOTES_KEY = "notes"
    }
}

private inline fun <reified T> Gson.fromJson(notes: String): T =
    this.fromJson(notes, object : TypeToken<T>() {}.type)
