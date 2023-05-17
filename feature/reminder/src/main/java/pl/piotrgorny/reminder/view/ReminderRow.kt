package pl.piotrgorny.reminder.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.piotrgorny.model.FilterReminder

@Composable
fun ReminderRow(
    filterReminder: FilterReminder
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        elevation = 10.dp) {
        Row {
            ReminderData(filterReminder = filterReminder, modifier = Modifier.weight(1f))
        }
    }
}