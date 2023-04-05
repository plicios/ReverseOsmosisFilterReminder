package pl.piotrgorny.filtersetup.extensions

import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.util.*

fun Date.print() : String = SimpleDateFormat("dd-MM-yyyy").format(this)

fun LocalDate.print() : String = this.toString("dd-MM-yyyy")