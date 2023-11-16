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
        Filter.LifeSpan.OneWeek -> stringResource(id = R.string.one_week)
        Filter.LifeSpan.TwoWeeks -> stringResource(id = R.string.two_weeks)
        Filter.LifeSpan.ThreeWeeks -> stringResource(id = R.string.three_weeks)
        Filter.LifeSpan.OneMonth -> stringResource(id = R.string.one_month)
        Filter.LifeSpan.TwoMonths -> stringResource(id = R.string.two_months)
        Filter.LifeSpan.ThreeMonths -> stringResource(id = R.string.three_months)
        Filter.LifeSpan.HalfYear -> stringResource(id = R.string.half_year)
        Filter.LifeSpan.OneYear -> stringResource(id = R.string.one_year)
        Filter.LifeSpan.TwoYears -> stringResource(id = R.string.two_years)
        Filter.LifeSpan.ThreeYears -> stringResource(id = R.string.three_years)
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