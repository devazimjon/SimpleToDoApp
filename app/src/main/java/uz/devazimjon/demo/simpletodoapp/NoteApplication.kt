package uz.devazimjon.demo.simpletodoapp

import android.app.Application
import android.content.SharedPreferences
import uz.devazimjon.demo.simpletodoapp.data.NoteRepository
import uz.devazimjon.demo.simpletodoapp.data.repository.NoteRepositoryImpl
import kotlin.properties.Delegates

class NoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        repository = NoteRepositoryImpl(getSharedPreferences(NOTE_PREFERENCES, MODE_PRIVATE))
    }

    companion object {
        const val NOTE_PREFERENCES = "note_preferences"

        var repository: NoteRepository by Delegates.notNull()
    }
}