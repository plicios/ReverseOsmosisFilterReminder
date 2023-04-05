package pl.piotrgorny.common

fun <T> Collection<T>.remove(list: List<T>) : List<T> =
    this.toMutableList().apply {
        remove(list)
    }.toList()

fun <T> MutableList<T>.remove(list: List<T>) {
    list.forEach { remove(it) }
}