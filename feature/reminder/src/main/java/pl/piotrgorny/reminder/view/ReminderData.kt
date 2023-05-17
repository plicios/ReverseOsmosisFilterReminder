package pl.piotrgorny.reminder.view

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import pl.piotrgorny.common.toStringFromPattern
import pl.piotrgorny.model.FilterReminder
import pl.piotrgorny.reminder.extensions.print

@Composable
fun ReminderData(filterReminder: FilterReminder, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier) {
        val constraints = filterRowDecoupledConstraints()
        ConstraintLayout(constraints,
            modifier = Modifier.padding(start = 15.dp, bottom = 15.dp)) {

            Text(text = "Type:", modifier = Modifier.layoutId("typeLabel"))
            Text(text = "Alarm date:", modifier = Modifier.layoutId("alarmDateLabel"))

            Text(text = filterReminder.type.print(), modifier = Modifier.layoutId("type"))
            Text(text = filterReminder.alarmDate.toStringFromPattern(), modifier = Modifier.layoutId("alarmDate"))
        }
    }
}

private fun filterRowDecoupledConstraints() = ConstraintSet {
    val typeLabel = createRefFor("typeLabel")
    val type = createRefFor("type")
    val alarmDateLabel = createRefFor("alarmDateLabel")
    val alarmDate = createRefFor("alarmDate")

    val labelEndBarrier = createEndBarrier(typeLabel, alarmDateLabel)

    constrain(typeLabel) {
        top.linkTo(parent.top, 15.dp)
        start.linkTo(parent.start)
    }
    constrain(alarmDateLabel){
        top.linkTo(type.bottom)
        start.linkTo(parent.start)
    }

    constrain(type){
        top.linkTo(typeLabel.top)
        linkTo(labelEndBarrier, parent.end, 5.dp, 5.dp, bias = 0f)
        width = Dimension.preferredWrapContent
    }
    constrain(alarmDate){
        top.linkTo(alarmDateLabel.top)
        linkTo(labelEndBarrier, parent.end, 5.dp, 5.dp, bias = 0f)
        width = Dimension.preferredWrapContent
    }
}