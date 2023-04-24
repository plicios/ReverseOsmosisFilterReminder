package pl.piotrgorny.filtersetup.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.piotrgorny.filtersetup.viewModel.AddOrModifyFilterSetupViewModel
import pl.piotrgorny.model.Filter
import pl.piotrgorny.ui.dialog.Dialog

@Composable
fun RemoveFilterSetupDialog(
    onDismiss: () -> Unit,
    onRemove: () -> Unit
) {
    Dialog(
        title = "Sure to remove filter setup?",
        confirmButtonTitle = "Remove",
        onDismiss = onDismiss,
        onConfirm = { onRemove() }
    )
}