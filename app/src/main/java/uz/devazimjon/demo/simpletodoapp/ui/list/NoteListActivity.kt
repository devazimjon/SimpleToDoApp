package uz.devazimjon.demo.simpletodoapp.ui.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import uz.devazimjon.demo.simpletodoapp.NoteApplication
import uz.devazimjon.demo.simpletodoapp.data.model.Note
import uz.devazimjon.demo.simpletodoapp.databinding.ActivityNoteDetailBinding
import uz.devazimjon.demo.simpletodoapp.databinding.ActivityNoteListBinding
import uz.devazimjon.demo.simpletodoapp.ui.detail.NoteDetailActivity
import uz.devazimjon.demo.simpletodoapp.ui.list.adapter.NoteAdapter
import uz.devazimjon.demo.simpletodoapp.ui.list.presenter.NoteListPresenter
import kotlin.properties.Delegates

class NoteListActivity : AppCompatActivity() {
    private var presenter: NoteListPresenter by Delegates.notNull()
    private var adapter: NoteAdapter by Delegates.notNull()
    private var _binding: ActivityNoteListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = NoteListPresenter(this, NoteApplication.repository)
        _binding = ActivityNoteListBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        binding.noteRv.also {
            adapter = NoteAdapter()
            it.adapter = adapter
            adapter.setOnClickListener(::editNote)
        }

        initClickers()
    }

    fun submitNotes(items: List<Note>) {
        adapter.setItems(items)
    }

    private fun initClickers() {
        binding.addFab.setOnClickListener { openNote() }
    }

    private fun openNote() {
        NoteDetailActivity.newIntent(this)
            .also { startActivity(it) }
    }

    private fun editNote(view: View, note: Note) {
        val intent = NoteDetailActivity.newIntent(this, note)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            view,
            view.transitionName
        )
        ActivityCompat.startActivity(this, intent, options.toBundle())
    }

    override fun onStart() {
        super.onStart()
        presenter.getNotes()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
