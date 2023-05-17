package pl.piotrgorny.filtersetup.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import pl.piotrgorny.common.toStringFromPattern
import pl.piotrgorny.filtersetup.extensions.print
import pl.piotrgorny.model.Filter

@Composable
fun FilterData(filter: Filter, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier) {
        val constraints = filterRowDecoupledConstraints()
        ConstraintLayout(constraints,
            modifier = Modifier.padding(start = 15.dp, bottom = 15.dp)) {

            Text(text = "Type:", modifier = Modifier.layoutId("typeLabel"))
            Text(text = "Installation date:", modifier = Modifier.layoutId("installationDateLabel"))
            Text(text = "Expiration date:", modifier = Modifier.layoutId("expirationDateLabel"))
            Text(text = "Filter lifespan:", modifier = Modifier.layoutId("lifeSpanLabel"))

            Text(text = filter.type.print(), modifier = Modifier.layoutId("type"))
            Text(text = filter.installationDate.toStringFromPattern(), modifier = Modifier.layoutId("installationDate"))
            Text(text = filter.expirationDate.toStringFromPattern(), modifier = Modifier.layoutId("expirationDate"))
            Text(text = filter.lifeSpan.print(), modifier = Modifier.layoutId("lifeSpan"))
        }
    }
}

private fun filterRowDecoupledConstraints() = ConstraintSet {
    val typeLabel = createRefFor("typeLabel")
    val type = createRefFor("type")
    val installationDateLabel = createRefFor("installationDateLabel")
    val installationDate = createRefFor("installationDate")
    val expirationDateLabel = createRefFor("expirationDateLabel")
    val expirationDate = createRefFor("expirationDate")
    val lifeSpanLabel = createRefFor("lifeSpanLabel")
    val lifeSpan = createRefFor("lifeSpan")

    val labelEndBarrier = createEndBarrier(typeLabel, installationDateLabel, expirationDateLabel, lifeSpanLabel)

    constrain(typeLabel) {
        top.linkTo(parent.top, 15.dp)
        start.linkTo(parent.start)
    }
    constrain(installationDateLabel){
        top.linkTo(type.bottom)
        start.linkTo(parent.start)
    }
    constrain(expirationDateLabel){
        top.linkTo(installationDate.bottom)
        start.linkTo(parent.start)
    }
    constrain(lifeSpanLabel){
        top.linkTo(expirationDate.bottom)
        start.linkTo(parent.start)
    }

    constrain(type){
        top.linkTo(typeLabel.top)
        linkTo(labelEndBarrier, parent.end, 5.dp, 5.dp, bias = 0f)
        width = Dimension.preferredWrapContent
    }
    constrain(installationDate){
        top.linkTo(installationDateLabel.top)
        linkTo(labelEndBarrier, parent.end, 5.dp, 5.dp, bias = 0f)
        width = Dimension.preferredWrapContent
    }
    constrain(expirationDate){
        top.linkTo(expirationDateLabel.top)
        linkTo(labelEndBarrier, parent.end, 5.dp, 5.dp, bias = 0f)
        width = Dimension.preferredWrapContent
    }
    constrain(lifeSpan){
        top.linkTo(lifeSpanLabel.top)
        linkTo(labelEndBarrier, parent.end, 5.dp, 5.dp, bias = 0f)
        width = Dimension.preferredWrapContent
    }
}
