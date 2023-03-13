package pl.piotrgorny.model

import java.util.*

data class Filter(
    val name: String,
    val lifeSpan: Int, //TODO Maybe change to some timeSpan type
    val installationDate: Date,
    val type: Type
) {
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
}
