package pl.piotrgorny.data

import pl.piotrgorny.model.FilterSetup

interface FilterSetupRepository {
    suspend fun getFilterSetups(): List<FilterSetup>
    suspend fun addFilterSetup(filterSetup: FilterSetup)
}