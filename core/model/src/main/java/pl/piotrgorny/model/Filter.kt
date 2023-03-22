package pl.piotrgorny.model

import org.joda.time.Days
import org.joda.time.LocalDate
import org.joda.time.Months
import org.joda.time.ReadablePeriod
import org.joda.time.Weeks
import org.joda.time.Years
import java.util.*

data class Filter(
    val name: String,
    val lifeSpan: LifeSpan,
    val installationDate: Date,
    val type: Type
) {
    fun getExpirationDate() = (LocalDate(installationDate) + lifeSpan.period).toDate()

    enum class Type {
        SedimentPS_20,
        SedimentPS_10,
        SedimentPS_5,
        SedimentPS_1,
        Carbon,
        SemiPermeableMembrane,
        InlineCarbon,
        Mineralizing,
        BioCeramic
    }

    enum class LifeSpan(val period: ReadablePeriod) {
        One_Day(Days.ONE),
        Two_Days(Days.TWO),
        One_Week(Weeks.ONE),
        Two_Weeks(Weeks.TWO),
        Three_Weeks(Weeks.THREE),
        One_Month(Months.ONE),
        Two_Months(Months.TWO),
        Three_Months(Months.THREE),
        Half_Year(Months.SIX),
        One_Year(Years.ONE)
    }
}
