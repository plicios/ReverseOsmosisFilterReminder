package pl.piotrgorny.ui.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.piotrgorny.ui.dropdown.Dropdown
import pl.piotrgorny.ui.dropdown.SimpleDropDownItem

@Composable
fun Dialog(
    title: String = "",
    confirmButtonTitle: String = "Confirm",
    cancelButtonTitle: String = "Cancel",
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colors.surface
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = title)
                Spacer(modifier = Modifier.height(20.dp))
                content()
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) {
                        Text(text = cancelButtonTitle)
                    }
                    TextButton(onClick = onConfirm) {
                        Text(text = confirmButtonTitle)
                    }
                }
            }
        }
    }
}