package pl.piotrgorny.ui.dropdown

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> Dropdown(
    label: String = "Label",
    options: List<T> = emptyList(),
    optionToString: (T) -> String = { it.toString() },
    defaultValue: T? = null,
    onSelectedOptionChange: (T) -> Unit = {}
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedOptionText by remember {
        mutableStateOf(defaultValue?.let(optionToString) ?: "")
    }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = !expanded
    }) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
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
                    selectedOptionText = optionToString(it)
                    onSelectedOptionChange(it)
                    expanded = false
                }) {
                    Text(text = optionToString(it))
                }
            }
        }
    }
}
