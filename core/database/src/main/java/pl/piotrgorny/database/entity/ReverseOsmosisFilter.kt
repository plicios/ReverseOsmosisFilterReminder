package pl.piotrgorny.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = ReverseOsmosisFilter.TABLE_NAME)
data class ReverseOsmosisFilter(
    @PrimaryKey val uid: Int,
    val filterSetupId: Int, //ForeignKey
    val type: FilterType,
    val name: String,
    val lifeSpan: Int, //TODO Maybe change to some timeSpan
    val installationDate: Date
) {
    companion object {
        const val TABLE_NAME = "filter"
    }
}

enum class FilterType
