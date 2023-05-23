package pl.piotrgorny.ui.dropdown

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import pl.piotrgorny.ui.textfield.ValidationOutlinedTextField

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> Dropdown(
    label: String = "Label",
    options: List<T> = emptyList(),
    optionToString: (T) -> String = { it.toString() },
    defaultValue: T? = null,
    onSelectedOptionChange: (T) -> Unit = {},
    error: String? = null
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
        ValidationOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = label,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            error = error,
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
