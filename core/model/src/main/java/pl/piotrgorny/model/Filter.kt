package pl.piotrgorny.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.joda.time.LocalDate
import org.joda.time.Months
import org.joda.time.ReadablePeriod
import org.joda.time.Weeks
import org.joda.time.Years

@Parcelize
data class Filter(
    val type: Type,
    val installationDate: LocalDate,
    val lifeSpan: LifeSpan
) : Parcelable {
    constructor(type: Type) : this(
        type, LocalDate(), LifeSpan.Half_Year
    )

    val expirationDate : LocalDate
        get() = installationDate.plus(lifeSpan.period)

    val isExpired : Boolean
        get() = expirationDate.isBefore(LocalDate())
    @Parcelize
    sealed class Type : Parcelable {
        @Parcelize
        sealed class Sediment(val micronValue: Int) : Type() {
            object SedimentPS_20 : Sediment(20)
            object SedimentPS_10 : Sediment(10)
            object SedimentPS_5 : Sediment(5)
            object SedimentPS_1 : Sediment(1)

            object Any : Sediment(-1) {
                override fun equals(other: kotlin.Any?): Boolean {
                    if(other is Sediment) return true
                    return super.equals(other)
                }
            }

            override val name: String
                get() = "Sediment:$micronValue"

            override fun equals(other: kotlin.Any?): Boolean {
                if(other is Any) return true
                return super.equals(other)
            }
        }
        object Carbon : Type() {
            override val name: String
                get() = "Carbon"
        }
        object SemiPermeableMembrane : Type() {
            override val name: String
                get() = "SemiPermeableMembrane"
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
                    Sediment.Any.name -> Sediment.Any
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
            if (other !is Type) return false

            other as Type

            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }
    }

    enum class LifeSpan(val period: ReadablePeriod) {
        One_Week(Weeks.ONE),
        Two_Weeks(Weeks.TWO),
        Three_Weeks(Weeks.THREE),
        One_Month(Months.ONE),
        Two_Months(Months.TWO),
        Three_Months(Months.THREE),
        Half_Year(Months.SIX),
        One_Year(Years.ONE),
        Two_Years(Years.TWO),
        Three_Years(Years.THREE)
    }
}
