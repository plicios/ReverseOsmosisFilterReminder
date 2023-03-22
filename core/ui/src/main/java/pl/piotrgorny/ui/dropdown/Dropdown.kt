package pl.piotrgorny.ui.dropdown

import androidx.compose.material.*
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> Dropdown(
    label: String = "Label",
    options: List<DropDownItem<T>> = emptyList(),
    defaultValue: String? = null,
    onSelectedOptionChange: (T) -> Unit = {}
){
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedOptionText by remember {
        mutableStateOf(defaultValue ?: options.getOrNull(0)?.title ?: "text")
    }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = !expanded
    }) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(text = label)},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(onClick = {
                    selectedOptionText = it.title
                    onSelectedOptionChange(it.itemValue)
                    expanded = false
                }) {
                    Text(text = it.title)
                }
            }
        }
    }
}
