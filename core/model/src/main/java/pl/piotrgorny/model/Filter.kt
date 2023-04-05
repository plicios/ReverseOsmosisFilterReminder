package pl.piotrgorny.model

import org.joda.time.Days
import org.joda.time.LocalDate
import org.joda.time.Months
import org.joda.time.ReadablePeriod
import org.joda.time.Weeks
import org.joda.time.Years
import java.util.*

data class Filter(
    val type: Type,
    val installationDate: LocalDate,
    val lifeSpan: LifeSpan
) {
    constructor(type: Type) : this(
        type, LocalDate(), LifeSpan.One_Day
    )

    fun getExpirationDate() : Date = (LocalDate(installationDate) + lifeSpan.period).toDate()

    sealed class Type {
        sealed class Sediment(val micronValue: Int) : Type() {
            object SedimentPS_20 : Sediment(20)
            object SedimentPS_10 : Sediment(10)
            object SedimentPS_5 : Sediment(5)
            object SedimentPS_1 : Sediment(1)

            override val name: String
                get() = "Sediment:$micronValue"

            override val typeClass: Class<out Type>
                get() = Sediment::class.java
        }
        object Carbon : Type() {
            override val name: String
                get() = "BioCeramic"
        }
        object SemiPermeableMembrane : Type() {
            override val name: String
                get() = "BioCeramic"
        }
        object InlineCarbon : Type() {
            override val name: String
                get() = "InlineCarbon"
        }
        object Mineralizing : Type() {
            override val name: String
                get() = "Mineralizing"
        }
        object BioCeramic : Type() {
            override val name: String
                get() = "BioCeramic"
        }

        object Ionizing : Type() {
            override val name: String
                get() = "Ionizing"
        }

        abstract val name: String

        open val typeClass: Class<out Type> = javaClass

        companion object {
            fun valueOf(value: String) : Type =
                when(value) {
                    BioCeramic.name -> BioCeramic
                    Carbon.name -> Carbon
                    InlineCarbon.name -> InlineCarbon
                    Ionizing.name -> Ionizing
                    Mineralizing.name -> Mineralizing
                    Sediment.SedimentPS_1.name -> Sediment.SedimentPS_1
                    Sediment.SedimentPS_10.name -> Sediment.SedimentPS_10
                    Sediment.SedimentPS_20.name -> Sediment.SedimentPS_20
                    Sediment.SedimentPS_5.name -> Sediment.SedimentPS_5
                    SemiPermeableMembrane.name -> SemiPermeableMembrane
                    else -> throw IllegalArgumentException("Not supported filter type: $value")
                }

            fun values() : List<Type> = listOf(
                Sediment.SedimentPS_20,
                Sediment.SedimentPS_10,
                Sediment.SedimentPS_5,
                Sediment.SedimentPS_1,
                Carbon,
                SemiPermeableMembrane,
                InlineCarbon,
                Mineralizing,
                BioCeramic,
                Ionizing
            )
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Type

            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }
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
