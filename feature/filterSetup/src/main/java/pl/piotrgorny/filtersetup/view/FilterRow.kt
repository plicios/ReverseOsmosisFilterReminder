package pl.piotrgorny.filtersetup.view

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import org.joda.time.LocalDate
import pl.piotrgorny.filtersetup.extensions.print
import pl.piotrgorny.model.Filter
import java.util.*

@Composable
fun FilterRow(filter: Filter, onFilterEditPress: (Filter) -> Unit = {}) {
    Card(modifier = Modifier.fillMaxWidth().padding(15.dp), elevation = 10.dp) {
        BoxWithConstraints {
            val constraints = filterRowDecoupledConstraints()
            ConstraintLayout(constraints,
                modifier = Modifier.padding(start = 15.dp, bottom = 15.dp).fillMaxWidth()) {

                Text(text = "Type:", modifier = Modifier.layoutId("typeLabel"))
                Text(text = "Installation date:", modifier = Modifier.layoutId("installationDateLabel"))
                Text(text = "Expiration date:", modifier = Modifier.layoutId("expirationDateLabel"))
                Text(text = "Filter lifespan:", modifier = Modifier.layoutId("lifeSpanLabel"))

                Text(text = filter.type.print(), modifier = Modifier.layoutId("type"))
                Text(text = filter.installationDate.print(), modifier = Modifier.layoutId("installationDate"))
                Text(text = filter.getExpirationDate().print(), modifier = Modifier.layoutId("expirationDate"))
                Text(text = filter.lifeSpan.print(), modifier = Modifier.layoutId("lifeSpan"))

                IconButton(modifier = Modifier.layoutId("edit"), onClick = { onFilterEditPress(filter) }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "edit filter"
                    )
                }
            }
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
    val edit = createRefFor("edit")

    val labelEndBarrier = createEndBarrier(typeLabel, installationDateLabel, expirationDateLabel, lifeSpanLabel)

    constrain(typeLabel) {
        top.linkTo(parent.top, 15.dp)
        start.linkTo(parent.start)
    }
    constrain(installationDateLabel){
        top.linkTo(type.bottom)
    }
    constrain(expirationDateLabel){
        top.linkTo(installationDate.bottom)
    }
    constrain(lifeSpanLabel){
        top.linkTo(expirationDate.bottom)
    }

    constrain(type){
        top.linkTo(typeLabel.top)
        linkTo(labelEndBarrier, edit.start, 5.dp, 5.dp, bias = 0f)
        width = Dimension.preferredWrapContent
    }
    constrain(installationDate){
        top.linkTo(installationDateLabel.top)
        start.linkTo(labelEndBarrier, 5.dp)
    }
    constrain(expirationDate){
        top.linkTo(expirationDateLabel.top)
        start.linkTo(labelEndBarrier, 5.dp)
    }
    constrain(lifeSpan){
        top.linkTo(lifeSpanLabel.top)
        start.linkTo(labelEndBarrier, 5.dp)
    }

    constrain(edit){
        top.linkTo(parent.top)
        end.linkTo(parent.end)
    }
}

@Preview
@Composable
fun FilterRowPreview(){
    FilterRow(filter = Filter(Filter.Type.InlineCarbon, LocalDate(), Filter.LifeSpan.One_Day))
}