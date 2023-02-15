package uz.devazimjon.demo.simpletodoapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import uz.devazimjon.demo.simpletodoapp.R
import java.util.*

@Parcelize
data class Note(
    val id: String = UUID.randomUUID().toString(),
    var text: String,
    var priority: Int = 0,
    var lastModified: Long = Date().time,
) : Parcelable{

    val priorityColor: Int
        get() {
            return when (priority) {
                0 -> R.color.colorLowPriority
                1 -> R.color.colorNormalPriority
                2 -> R.color.colorHighPriority
                else -> R.color.colorUrgentPriority
            }
        }
}
