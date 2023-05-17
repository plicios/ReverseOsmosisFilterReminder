package pl.piotrgorny.common

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.Date

fun DateTime.toStringFromPattern(pattern: String = "HH:mm dd-MM-yyyy") : String = this.toString(pattern)

@Suppress("SimpleDateFormat")
fun Date.toString(pattern: String = "dd-MM-yyyy") : String = SimpleDateFormat(pattern).format(this)

fun LocalDate.toStringFromPattern(pattern: String = "dd-MM-yyyy") : String = this.toString(pattern)

fun String.toLocalDate(pattern: String = "dd-MM-yyyy") : LocalDate = DateTimeFormat.forPattern(pattern).parseLocalDate(this)