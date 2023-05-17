package pl.piotrgorny.reminder.extensions

import pl.piotrgorny.model.FilterReminder


fun FilterReminder.Type.print() : String { //TODO to be changed to string res
    return when(this){
        FilterReminder.Type.BuyNew -> "Buy new"
        FilterReminder.Type.Replace -> "Replace"
    }
}
