package pl.piotrgorny.filtersetup.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pl.piotrgorny.filtersetup.R
import pl.piotrgorny.model.Filter
import pl.piotrgorny.ui.dialog.Dialog

@Composable
fun RemoveFilterDialog(
    onDismiss: () -> Unit,
    onRemove: () -> Unit
) {
    Dialog(
        title = stringResource(id = R.string.sure_to_remove_filter),
        confirmButtonTitle = stringResource(id = R.string.remove),
        onDismiss = onDismiss,
        onConfirm = { onRemove() }
    )
}