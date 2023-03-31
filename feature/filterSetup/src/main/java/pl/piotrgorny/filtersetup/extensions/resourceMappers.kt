package pl.piotrgorny.filtersetup.extensions

import pl.piotrgorny.model.Filter


fun Filter.Type.print() : String { //TODO to be changed to string res
    return when(this){
        Filter.Type.Sediment.SedimentPS_20 -> "Sediment Filter 20 micron"
        Filter.Type.Sediment.SedimentPS_10 -> "Sediment Filter 10 micron"
        Filter.Type.Sediment.SedimentPS_5 -> "Sediment Filter 5 micron"
        Filter.Type.Sediment.SedimentPS_1 -> "Sediment Filter 1 micron"
        Filter.Type.Carbon -> "Carbon filter"
        Filter.Type.SemiPermeableMembrane -> "Semipermeable membrane"
        Filter.Type.InlineCarbon -> "Activated carbon post filter"
        Filter.Type.Mineralizing -> "Mineralizing filter"
        Filter.Type.BioCeramic -> "Bio-ceramic filter"
        Filter.Type.Ionizing -> "Ionizing filter"
    }
}

fun Filter.LifeSpan.print() : String {
    return when(this){
        Filter.LifeSpan.One_Day -> "One day"
        Filter.LifeSpan.Two_Days -> "Two days"
        Filter.LifeSpan.One_Week -> "One week"
        Filter.LifeSpan.Two_Weeks -> "Two weeks"
        Filter.LifeSpan.Three_Weeks -> "Three weeks"
        Filter.LifeSpan.One_Month -> "One month"
        Filter.LifeSpan.Two_Months -> "Two month"
        Filter.LifeSpan.Three_Months -> "Three month"
        Filter.LifeSpan.Half_Year -> "Half of the year"
        Filter.LifeSpan.One_Year -> "One year"
    }
}