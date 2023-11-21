package pl.piotrgorny.reminder

import org.joda.time.LocalTime
import pl.piotrgorny.data.Event
import pl.piotrgorny.data.EventsRepository
import pl.piotrgorny.data.ReminderRepository
import pl.piotrgorny.model.Filter
import pl.piotrgorny.model.FilterReminder

class RemindersScheduler(private val eventsRepository: EventsRepository, private val reminderRepository: ReminderRepository) {
    suspend fun observeChanges() {
        eventsRepository.getNewEvent().collect{
            eventAdded(it)
        }
        eventsRepository.getModifiedEvent().collect{
            eventModified(it)
        }
        eventsRepository.getRemovedEvent().collect{
            eventRemoved(it)
        }
    }

    private suspend fun eventAdded(event: Event) {
        reminderRepository.addReminders(event.getReminders())
    }

    private suspend fun eventModified(event: Event) {
        eventRemoved(event.id)
        eventAdded(event)
    }

    private suspend fun eventRemoved(eventId: Long) {
        reminderRepository.removeRemindersByParentId(eventId)
    }

    private suspend fun reminderShowed(reminderId: Long) {
        //TODO handle shown reminders
    }

    private fun Event.getReminders(): List<FilterReminder> = listOf(
            FilterReminder(
                id,
                date.toDateTime(LocalTime(9, 0)).minusDays(7),
                FilterReminder.Type.BuyNew
            ),
            FilterReminder(
                id,
                date.toDateTime(LocalTime(9, 0)).minusDays(3),
                FilterReminder.Type.Replace
            )
        )
}





