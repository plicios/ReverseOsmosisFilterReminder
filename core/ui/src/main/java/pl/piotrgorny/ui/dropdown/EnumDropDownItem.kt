package pl.piotrgorny.ui.dropdown

data class EnumDropDownItem<T: Enum<*>>(override val itemValue: T) : DropDownItem<T>() {
    override val title: String
        get() = itemValue.name
}