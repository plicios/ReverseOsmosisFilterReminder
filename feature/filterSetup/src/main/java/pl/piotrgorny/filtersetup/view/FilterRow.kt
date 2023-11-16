package pl.piotrgorny.filtersetup.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.joda.time.LocalDate
import pl.piotrgorny.filtersetup.R
import pl.piotrgorny.model.Filter


@Composable
fun FilterRow(
    filter: Filter,
    readOnly: Boolean = false,
    onFilterEditPress: (Filter) -> Unit = {},
    onFilterRemovePress: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = 10.dp) {
        Row(modifier = Modifier.background(if(filter.isExpired) MaterialTheme.colors.error else MaterialTheme.colors.surface)) {
            FilterData(filter = filter, modifier = Modifier.weight(1f))
            if(!readOnly) {
                Column {
                    IconButton(
                        modifier = Modifier.layoutId("edit"),
                        onClick = { onFilterEditPress(filter) }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = stringResource(id = R.string.edit_filter)
                        )
                    }
                    IconButton(
                        modifier = Modifier.layoutId("remove"),
                        onClick = onFilterRemovePress
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = stringResource(id = R.string.remove_filter)
                        )
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun FilterRowPreview(){
    FilterRow(filter = Filter(Filter.Type.InlineCarbon, LocalDate(), Filter.LifeSpan.HalfYear))
}