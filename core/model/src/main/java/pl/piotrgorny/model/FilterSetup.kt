package pl.piotrgorny.model

import pl.piotrgorny.common.remove

data class FilterSetup(
    val id: Long,
    val name: String,
    val type: Type,
    val filters: List<Filter>
) {
    constructor(name: String, type: Type, filters: List<Filter>) : this(
        -1, name, type, filters
    )

    constructor(type: Type, filters: List<Filter>) : this(
        "", type, filters
    )

    val missingFilterTypes: List<Class<out Filter.Type>>
        get() = type.filterTypes.remove(filters.map { it.type.typeClass })

    val obsoleteFilters: List<Filter>
        get() = when(type) {
            Type.Custom -> emptyList()
            else -> {
                val obsoleteFilters = mutableListOf<Filter>()
                val filterTypes = type.filterTypes.toMutableList()
                filters.forEach {
                    if(filterTypes.contains(it.type.typeClass)){
                        filterTypes.remove(it.type.typeClass)
                    } else {
                        obsoleteFilters.add(it)
                    }
                }
                obsoleteFilters
            }
        }

    sealed class Type {
        open class RO4 : Type() {
            override val filterTypes: List<Class<out Filter.Type>>
                get() = listOf(Filter.Type.Sediment::class.java, Filter.Type.Carbon::class.java, Filter.Type.Sediment::class.java, Filter.Type.SemiPermeableMembrane::class.java)
            override val name: String
                get() = "RO4"
        }
        open class RO5 : RO4() {
            override val filterTypes: List<Class<out Filter.Type>>
                get() = super.filterTypes + Filter.Type.InlineCarbon::class.java
            override val name: String
                get() = "RO5"
        }
        open class RO6 : RO5() {
            override val filterTypes: List<Class<out Filter.Type>>
                get() = super.filterTypes + Filter.Type.Mineralizing::class.java
            override val name: String
                get() = "RO6"
        }
        open class RO7 : RO6() {
            override val filterTypes: List<Class<out Filter.Type>>
                get() = super.filterTypes + Filter.Type.BioCeramic::class.java
            override val name: String
                get() = "RO7"
        }
        open class RO8 : RO7() {
            override val filterTypes: List<Class<out Filter.Type>>
                get() = super.filterTypes + Filter.Type.Ionizing::class.java
            override val name: String
                get() = "RO8"
        }
        object RO9 : RO8() {
            override val filterTypes: List<Class<out Filter.Type>>
                get() = super.filterTypes + Filter.Type.InlineCarbon::class.java
            override val name: String
                get() = "RO9"
        }

        object Aquarius : Type() {
            override val filterTypes: List<Class<out Filter.Type>>
                get() = listOf(Filter.Type.Sediment::class.java, Filter.Type.Carbon::class.java, Filter.Type.SemiPermeableMembrane::class.java)
            override val name: String
                get() = "Aquarius"
        }
        object Custom : Type() {
            override val filterTypes: List<Class<out Filter.Type>>
                get() = emptyList()
            override val name: String
                get() = "Custom"
        }

        abstract val filterTypes: List<Class<out Filter.Type>>
        abstract val name: String

        companion object {
            fun valueOf(value: String) =
                when(value){
                    Aquarius.name -> Aquarius
                    Custom.name -> Custom
                    RO9.name -> RO9
                    RO8().name -> RO8()
                    RO7().name -> RO7()
                    RO6().name -> RO6()
                    RO5().name -> RO5()
                    RO4().name -> RO4()
                    else -> Custom
                }
            fun values() : List<Type> = listOf(
                RO4(),
                RO5(),
                RO6(),
                RO7(),
                RO8(),
                RO9,
                Custom,
                Aquarius
            )
        }
    }
}
