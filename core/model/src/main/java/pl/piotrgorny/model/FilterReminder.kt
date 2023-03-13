package pl.piotrgorny.model

import java.util.*

data class FilterReminder(
    val alarmDate: Date,
    val type: Type
) {
    enum class Type {
        BuyNew,
        Replace
    }
}
