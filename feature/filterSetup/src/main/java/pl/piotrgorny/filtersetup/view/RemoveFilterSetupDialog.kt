package pl.piotrgorny.filtersetup.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.piotrgorny.filtersetup.R
import pl.piotrgorny.filtersetup.viewModel.AddOrModifyFilterSetupViewModel
import pl.piotrgorny.model.Filter
import pl.piotrgorny.ui.dialog.Dialog

@Composable
fun RemoveFilterSetupDialog(
    onDismiss: () -> Unit,
    onRemove: () -> Unit
) {
    Dialog(
        title = stringResource(id = R.string.sure_to_remove_filter_setup),
        confirmButtonTitle = stringResource(id = R.string.remove),
        onDismiss = onDismiss,
        onConfirm = { onRemove() }
    )
}