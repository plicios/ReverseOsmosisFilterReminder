package pl.piotrgorny.ui.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.piotrgorny.ui.ErrorMessage

@Composable
fun ValidationOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    helper: String = "",
    error: String?,
    readOnly: Boolean = false,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Column {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            isError = error != null,
            label = {
                label?.let {
                    Text(text = it)
                }
            },
            trailingIcon = {
              trailingIcon?.let {
                  it()
              }
            },
            readOnly = readOnly,
            colors = colors,
            interactionSource = interactionSource
        )
        error?.let {
            ErrorMessage(error)
        } ?: Text(text = helper)
    }
}