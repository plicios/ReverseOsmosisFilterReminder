package pl.piotrgorny.model

import org.joda.time.DateTime

data class FilterReminder(
    val alarmDate: DateTime,
    val type: Type
) {
    enum class Type {
        BuyNew,
        Replace
    }
}
