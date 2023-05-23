package pl.piotrgorny.ui.date

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import org.joda.time.LocalDate
import pl.piotrgorny.ui.textfield.ValidationOutlinedTextField
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("SimpleDateFormat")
@Composable
fun DateField(
    label: String = "Date",
    initialDate: Date? = null,
    minDate: Date? = null,
    maxDate: Date? = null,
    datePattern: String = "dd-MM-yyyy",
    onDateChange: (Date) -> Unit = {},
    error: String? = null
) {
    var date by remember {
        mutableStateOf(initialDate?.let{LocalDate(it)})
    }
    Box{
        val datePickerInitialDate = date ?: LocalDate()
        val mDatePickerDialog = DatePickerDialog(
            LocalContext.current, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val localDate = LocalDate(year, month + 1, dayOfMonth)
                date = localDate
                onDateChange(localDate.toDate())
            },
            datePickerInitialDate.year,
            datePickerInitialDate.monthOfYear - 1,
            datePickerInitialDate.dayOfMonth
        ).also {dialog ->
            maxDate?.let{dialog.datePicker.maxDate = it.time}
            minDate?.let{dialog.datePicker.minDate = it.time}
        }
        val source = remember {
            MutableInteractionSource()
        }
        ValidationOutlinedTextField(
            value = date?.toString(datePattern) ?: "Input date",
            onValueChange = {},
            error = error,
            readOnly = true,
            label = label,
            interactionSource = source,
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        if(source.collectIsPressedAsState().value){
            mDatePickerDialog.show()
        }
    }
}
