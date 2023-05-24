package pl.piotrgorny.filtersetup.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pl.piotrgorny.filtersetup.R
import pl.piotrgorny.model.Filter
import pl.piotrgorny.model.FilterSetup


@Composable
fun Filter.Type.print() : String {
    return when(this){
        is Filter.Type.Sediment -> stringResource(id = R.string.sediment_filter, micronValue)
        Filter.Type.Carbon -> stringResource(id = R.string.carbon_filter)
        Filter.Type.SemiPermeableMembrane -> stringResource(id = R.string.semipermeable_membrane)
        Filter.Type.InlineCarbon -> stringResource(id = R.string.inline_carbon_filter)
        Filter.Type.Mineralizing -> stringResource(id = R.string.mineralizing_filter)
        Filter.Type.BioCeramic -> stringResource(id = R.string.bio_ceramic_filter)
        Filter.Type.Ionizing -> stringResource(id = R.string.ionizing_filter)
    }
}

@Composable
fun Filter.LifeSpan.print() : String {
    return when(this){
        Filter.LifeSpan.One_Week -> stringResource(id = R.string.one_week)
        Filter.LifeSpan.Two_Weeks -> stringResource(id = R.string.two_weeks)
        Filter.LifeSpan.Three_Weeks -> stringResource(id = R.string.three_weeks)
        Filter.LifeSpan.One_Month -> stringResource(id = R.string.one_month)
        Filter.LifeSpan.Two_Months -> stringResource(id = R.string.two_months)
        Filter.LifeSpan.Three_Months -> stringResource(id = R.string.three_months)
        Filter.LifeSpan.Half_Year -> stringResource(id = R.string.half_year)
        Filter.LifeSpan.One_Year -> stringResource(id = R.string.one_year)
        Filter.LifeSpan.Two_Years -> stringResource(id = R.string.two_years)
        Filter.LifeSpan.Three_Years -> stringResource(id = R.string.three_years)
    }
}

@Composable
fun FilterSetup.Type.print() : String {
    return when(this){
        FilterSetup.Type.Aquarius -> stringResource(id = R.string.aquarius)
        FilterSetup.Type.Custom -> stringResource(id = R.string.custom)
        is FilterSetup.Type.RO4 -> this.name
    }
}