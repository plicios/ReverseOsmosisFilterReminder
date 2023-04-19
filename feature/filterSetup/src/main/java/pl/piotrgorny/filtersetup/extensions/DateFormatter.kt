package pl.piotrgorny.filtersetup.extensions

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.Date

fun Date.print() : String = SimpleDateFormat("dd-MM-yyyy").format(this)

fun LocalDate.print() : String = this.toString("dd-MM-yyyy")

fun String.toDate() : LocalDate = DateTimeFormat.forPattern("dd-MM-yyyy").parseLocalDate(this)