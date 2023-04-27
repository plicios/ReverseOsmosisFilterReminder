package pl.piotrgorny.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FilterSetupTest {
    private fun obsoleteFiltersTest(input: FilterSetup, output: List<Filter>) {
        assertThat(input.obsoleteFilters).containsExactlyElementsIn(output)
    }

    private fun missingFilterTypesTest(input: FilterSetup, output: List<Filter.Type>) {
        assertThat(input.missingFilterTypes).containsExactlyElementsIn(output)
    }

    @Test
    fun obsoleteFilterTestEmptyFiltersInput(){
        obsoleteFiltersTest(
            FilterSetup(FilterSetup.Type.RO8(), listOf()),
            listOf()
        )
    }

    @Test
    fun obsoleteFilterTestCustomTypeInput(){
        obsoleteFiltersTest(
            FilterSetup(FilterSetup.Type.Custom, listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )),
            listOf()
        )
    }

    @Test
    fun obsoleteFilterTestRO4TypeInput(){
        obsoleteFiltersTest(
            FilterSetup(FilterSetup.Type.RO4(), listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )),
            listOf(
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )
        )
    }

    @Test
    fun obsoleteFilterTestRO5TypeInput(){
        obsoleteFiltersTest(
            FilterSetup(FilterSetup.Type.RO5(), listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )),
            listOf(
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )
        )
    }

    @Test
    fun obsoleteFilterTestRO6TypeInput(){
        obsoleteFiltersTest(
            FilterSetup(FilterSetup.Type.RO6(), listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )),
            listOf(
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )
        )
    }

    @Test
    fun obsoleteFilterTestRO7TypeInput(){
        obsoleteFiltersTest(
            FilterSetup(FilterSetup.Type.RO7(), listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )),
            listOf(
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )
        )
    }

    @Test
    fun obsoleteFilterTestRO8TypeInput(){
        obsoleteFiltersTest(
            FilterSetup(FilterSetup.Type.RO8(), listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )),
            listOf(
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )
        )
    }

    @Test
    fun obsoleteFilterTestAquariusTypeInput(){
        obsoleteFiltersTest(
            FilterSetup(FilterSetup.Type.Aquarius, listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )),
            listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )
        )
    }

    @Test
    fun missingFilterTestCustomEmptyFiltersInput(){
        missingFilterTypesTest(
            FilterSetup(FilterSetup.Type.Custom, listOf()),
            listOf()
        )
    }

    @Test
    fun missingFilterTestRO4EmptyFiltersInput(){
        missingFilterTypesTest(
            FilterSetup(FilterSetup.Type.RO4(), listOf()),
            listOf(
                Filter.Type.Sediment.SedimentPS_1,
                Filter.Type.Carbon,
                Filter.Type.Sediment.SedimentPS_1,
                Filter.Type.SemiPermeableMembrane
            )
        )
    }

    @Test
    fun missingFilterTestRO5EmptyFiltersInput(){
        missingFilterTypesTest(
            FilterSetup(FilterSetup.Type.RO5(), listOf()),
            listOf(
                Filter.Type.Sediment.SedimentPS_1,
                Filter.Type.Carbon,
                Filter.Type.Sediment.SedimentPS_1,
                Filter.Type.SemiPermeableMembrane,
                Filter.Type.InlineCarbon
            )
        )
    }

    @Test
    fun missingFilterTestRO6EmptyFiltersInput(){
        missingFilterTypesTest(
            FilterSetup(FilterSetup.Type.RO6(), listOf()),
            listOf(
                Filter.Type.Sediment.SedimentPS_1,
                Filter.Type.Carbon,
                Filter.Type.Sediment.SedimentPS_1,
                Filter.Type.SemiPermeableMembrane,
                Filter.Type.InlineCarbon,
                Filter.Type.Mineralizing
            )
        )
    }

    @Test
    fun missingFilterTestRO7EmptyFiltersInput(){
        missingFilterTypesTest(
            FilterSetup(FilterSetup.Type.RO7(), listOf()),
            listOf(
                Filter.Type.Sediment.SedimentPS_1,
                Filter.Type.Carbon,
                Filter.Type.Sediment.SedimentPS_1,
                Filter.Type.SemiPermeableMembrane,
                Filter.Type.InlineCarbon,
                Filter.Type.Mineralizing,
                Filter.Type.BioCeramic
            )
        )
    }

    @Test
    fun missingFilterTestRO8EmptyFiltersInput(){
        missingFilterTypesTest(
            FilterSetup(FilterSetup.Type.RO8(), listOf()),
            listOf(
                Filter.Type.Sediment.SedimentPS_1,
                Filter.Type.Carbon,
                Filter.Type.Sediment.SedimentPS_1,
                Filter.Type.SemiPermeableMembrane,
                Filter.Type.InlineCarbon,
                Filter.Type.Mineralizing,
                Filter.Type.BioCeramic,
                Filter.Type.Ionizing
            )
        )
    }

    @Test
    fun missingFilterTestRO9EmptyFiltersInput(){
        missingFilterTypesTest(
            FilterSetup(FilterSetup.Type.RO9, listOf()),
            listOf(
                Filter.Type.Sediment.SedimentPS_1,
                Filter.Type.Carbon,
                Filter.Type.Sediment.SedimentPS_1,
                Filter.Type.SemiPermeableMembrane,
                Filter.Type.InlineCarbon,
                Filter.Type.Mineralizing,
                Filter.Type.BioCeramic,
                Filter.Type.Ionizing,
                Filter.Type.InlineCarbon
            )
        )
    }

    @Test
    fun missingFilterTestRO4Input(){
        missingFilterTypesTest(
            FilterSetup(FilterSetup.Type.RO4(), listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )),
            listOf(
                Filter.Type.SemiPermeableMembrane
            )
        )
    }

    @Test
    fun missingFilterTestRO5Input(){
        missingFilterTypesTest(
            FilterSetup(FilterSetup.Type.RO5(), listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )),
            listOf(
                Filter.Type.SemiPermeableMembrane
            )
        )
    }

    @Test
    fun missingFilterTestRO6Input(){
        missingFilterTypesTest(
            FilterSetup(FilterSetup.Type.RO6(), listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )),
            listOf(
                Filter.Type.SemiPermeableMembrane,
                Filter.Type.Mineralizing
            )
        )
    }

    @Test
    fun missingFilterTestRO7Input(){
        missingFilterTypesTest(
            FilterSetup(FilterSetup.Type.RO7(), listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )),
            listOf(
                Filter.Type.SemiPermeableMembrane,
                Filter.Type.Mineralizing,
                Filter.Type.BioCeramic
            )
        )
    }

    @Test
    fun missingFilterTestRO8Input(){
        missingFilterTypesTest(
            FilterSetup(FilterSetup.Type.RO8(), listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )),
            listOf(
                Filter.Type.SemiPermeableMembrane,
                Filter.Type.Mineralizing,
                Filter.Type.BioCeramic
            )
        )
    }

    @Test
    fun missingFilterTestRO9Input(){
        missingFilterTypesTest(
            FilterSetup(FilterSetup.Type.RO9, listOf(
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Sediment.SedimentPS_1),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Ionizing),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.Carbon),
                Filter(Filter.Type.InlineCarbon),
                Filter(Filter.Type.Sediment.SedimentPS_20),
                Filter(Filter.Type.Sediment.SedimentPS_5),
                Filter(Filter.Type.Sediment.SedimentPS_1)
            )),
            listOf(
                Filter.Type.SemiPermeableMembrane,
                Filter.Type.Mineralizing,
                Filter.Type.BioCeramic,
                Filter.Type.InlineCarbon
            )
        )
    }
}