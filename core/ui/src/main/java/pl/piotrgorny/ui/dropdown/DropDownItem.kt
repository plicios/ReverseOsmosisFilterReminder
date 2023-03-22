package pl.piotrgorny.ui.dropdown

abstract class DropDownItem<T> {
    abstract val title: String
    abstract val itemValue: T
}