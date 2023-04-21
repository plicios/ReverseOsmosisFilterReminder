package pl.piotrgorny.filtersetup.view

import androidx.compose.runtime.Composable
import pl.piotrgorny.model.Filter
import pl.piotrgorny.ui.dialog.Dialog

@Composable
fun RemoveFilterDialog(
    onDismiss: () -> Unit,
    onRemove: () -> Unit
) {
    Dialog(
        title = "Sure to remove filter?",
        confirmButtonTitle = "Remove",
        onDismiss = onDismiss,
        onConfirm = { onRemove() }
    )
}