package pl.piotrgorny.filtersetup.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.print() : String = SimpleDateFormat("dd-MM-yyyy").format(this)