package pl.piotrgorny.common

fun <T> Iterable<T>.remove(list: List<T>) : List<T> =
    this.toMutableList().apply {
        remove(list)
    }

fun <T> MutableList<T>.remove(list: List<T>) {
    list.forEach { remove(it) }
}

fun <T> Iterable<T>.replace(newValue: T, selector: (T) -> Boolean) =
    map { if(selector(it)) newValue else it }

fun <T> MutableList<T>.replace(newValue: T, selector: (T) -> Boolean) {
    val indexes = mutableListOf<Int>()
    this.forEachIndexed { index, elem->
        if(selector(elem)) {
            indexes.add(index)
        }
    }
    indexes.forEach {
        this[it] = newValue
    }
}

fun <T> Iterable<T>.replaceFirst(newValue: T, selector: (T) -> Boolean) : List<T> {
    var replacedElement = false
    return map {
        if(selector(it) && !replacedElement) {
            replacedElement = true
            newValue
        } else it
    }
}

fun <T> MutableList<T>.replaceFirst(newValue: T, selector: (T) -> Boolean) {
    val index = this.indexOfFirst(selector)
    this[index] = newValue
}

fun <T> Iterable<T>.replaceLast(newValue: T, selector: (T) -> Boolean) {
    var replacedElement = false
    reversed().map { if(selector(it) && !replacedElement) {
        replacedElement = true
        newValue
    } else it }.reversed()
}

fun <T> MutableList<T>.replaceLast(newValue: T, selector: (T) -> Boolean) {
    val index = this.indexOfLast(selector)
    this[index] = newValue
}
