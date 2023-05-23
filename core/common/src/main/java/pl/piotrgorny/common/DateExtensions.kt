package pl.piotrgorny.common

import org.joda.time.LocalDate

fun tomorrow(): LocalDate = LocalDate.now().plusDays(1)