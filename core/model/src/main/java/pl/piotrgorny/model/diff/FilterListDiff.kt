package pl.piotrgorny.model.diff

import pl.piotrgorny.model.Filter

data class FilterListDiff(
    override val newElementsCallback: suspend (Filter) -> Unit,
    override val modifiedElementsCallback: suspend (Filter) -> Unit,
    override val removedElementsCallback: suspend (Long) -> Unit,

    ): ListDiff<Filter>() {
    override val getElementId: (Filter) -> Long? = {
        it.id
    }
}