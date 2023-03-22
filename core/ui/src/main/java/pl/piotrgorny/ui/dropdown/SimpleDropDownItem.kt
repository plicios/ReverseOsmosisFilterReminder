package pl.piotrgorny.ui.dropdown

data class SimpleDropDownItem<T>(override val itemValue: T) : DropDownItem<T>() {
    override val title: String
        get() = itemValue.toString()
}

