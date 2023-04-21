package pl.piotrgorny.filtersetup

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pl.piotrgorny.model.Filter
import java.util.UUID

@Parcelize
data class FilterChange(
    val index: Int? = null,
    val newValue: Filter? = null,
    private val uid: UUID = UUID.randomUUID()
) : Parcelable {
    override fun toString(): String {
        return uid.toString()
    }
}