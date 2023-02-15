package uz.devazimjon.demo.simpletodoapp.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import uz.devazimjon.demo.simpletodoapp.NoteApplication
import uz.devazimjon.demo.simpletodoapp.R
import uz.devazimjon.demo.simpletodoapp.data.model.Note
import uz.devazimjon.demo.simpletodoapp.databinding.ActivityNoteDetailBinding
import uz.devazimjon.demo.simpletodoapp.ui.detail.presenter.NoteDetailPresenter
import uz.devazimjon.demo.simpletodoapp.util.onTextChanged
import java.util.*
import kotlin.math.hypot
import kotlin.properties.Delegates

class NoteDetailActivity : AppCompatActivity() {
    private var presenter: NoteDetailPresenter by Delegates.notNull()
    private var _binding: ActivityNoteDetailBinding? = null
    private val binding get() = _binding!!

    val note: Note by lazy { intent.getParcelableExtra(EXTRA_NOTE) ?: Note(text = "") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = NoteDetailPresenter(this, NoteApplication.repository)
        _binding = ActivityNoteDetailBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        initUi()
    }

    private fun initUi() {
        binding.noteEt.onTextChanged {
            note.text = it
            note.lastModified = Date().time
        }

        binding.priorities.lowPriorityView.setOnClickListener { onPriorityChange(0, it) }
        binding.priorities.normalPriorityView.setOnClickListener { onPriorityChange(1, it) }
        binding.priorities.highPriorityView.setOnClickListener { onPriorityChange(2, it) }
        binding.priorities.urgentPriorityView.setOnClickListener { onPriorityChange(3, it) }

        showNote()
    }

    private fun onPriorityChange(priority: Int, view: View) {
        note.priority = priority
        showNote()

        val centerX = (view.x + view.width / 2).toInt()
        showRevealAnimation(centerX)
    }

    private fun showNote() {
        binding.noteEt.setText(note.text)
        binding.noteCv.setCardBackgroundColor(
            ContextCompat.getColor(binding.noteCv.context, note.priorityColor)
        )
    }

    private fun showRevealAnimation(centerX: Int) = with(binding) {
        val w = noteCv.width.toDouble()
        val h = noteCv.height.toDouble()
        val endRadius = hypot(w, h).toFloat()
        val revealAnimation = ViewAnimationUtils.createCircularReveal(
            noteCv,
            centerX,
            0,
            0f,
            endRadius
        )
        noteCv.visibility = View.VISIBLE
        revealAnimation.duration = 800
        revealAnimation.start()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (note.text.isEmpty()) {
            presenter.deleteNote(note)
            cancelTransitionAnimation()
        } else {
            presenter.saveNote(note)
        }
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            presenter.deleteNote(note)
            cancelTransitionAnimation()
            supportFinishAfterTransition()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun cancelTransitionAnimation() {
        binding.noteCv.transitionName = null
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        const val EXTRA_NOTE = "extra_note"

        fun newIntent(context: Context) = Intent(context, NoteDetailActivity::class.java)

        fun newIntent(context: Context, note: Note): Intent {
            val intent = Intent(context, NoteDetailActivity::class.java)
            intent.putExtra(EXTRA_NOTE, note)
            return intent
        }
    }
}