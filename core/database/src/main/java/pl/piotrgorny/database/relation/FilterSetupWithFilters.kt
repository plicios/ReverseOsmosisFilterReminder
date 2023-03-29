package pl.piotrgorny.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import pl.piotrgorny.database.entity.Filter
import pl.piotrgorny.database.entity.FilterSetup

data class FilterSetupWithFilters(
    @Embedded val filterSetup: FilterSetup,
    @Relation(
        parentColumn = "uid",
        entityColumn = "filterSetupId"
    )
    val filters: List<Filter>
)
