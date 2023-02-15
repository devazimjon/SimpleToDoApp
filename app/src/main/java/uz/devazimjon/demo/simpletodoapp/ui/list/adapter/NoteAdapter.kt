package uz.devazimjon.demo.simpletodoapp.ui.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import uz.devazimjon.demo.simpletodoapp.databinding.NoteItemBinding
import uz.devazimjon.demo.simpletodoapp.data.model.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    private val items: MutableList<Note> = mutableListOf()
    private var onClickListener: ((View, Note) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(NoteItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(note = items[position])
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Note>) = with(this.items) {
        clear()
        addAll(items)
        notifyDataSetChanged()
    }

    fun setOnClickListener(block: (View, Note) -> Unit) {
        this.onClickListener = block
    }

    inner class ViewHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        fun onBind(note: Note) {
            binding.noteTv.text = note.text
            binding.noteCv.setCardBackgroundColor(
                ContextCompat.getColor(itemView.context, note.priorityColor)
            )
            binding.noteCv.setOnClickListener { onClickListener?.invoke(it, note) }
            binding.noteDateTv.text = sdf.format(Date(note.lastModified))
        }
    }
}