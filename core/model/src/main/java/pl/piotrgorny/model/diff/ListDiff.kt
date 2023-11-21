package pl.piotrgorny.model.diff

abstract class ListDiff<T> {
    protected abstract val newElementsCallback: suspend (T) -> Unit
    protected abstract val modifiedElementsCallback: suspend (T) -> Unit
    protected abstract val removedElementsCallback: suspend (Long) -> Unit

    protected abstract val getElementId: (T) -> Long?

    suspend fun compare(list1: List<T>, list2: List<T>) {
        list2.minus(list1).forEach { getElementId(it)?.let{ id -> removedElementsCallback(id) } }
        list1.minus(list2).forEach {element ->
            if(list2.any { getElementId(element) == getElementId(it) }) {
                modifiedElementsCallback(element)
            } else {
                newElementsCallback(element)
            }
        }
    }
}

