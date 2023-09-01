package com.sevdesk.accountsettings

import arrow.core.Either
import arrow.core.NonEmptyList
import com.sevdesk.accountsettings.domain.AccountSettings
import com.sevdesk.accountsettings.domain.AccountSettingsEvent
import com.sevdesk.accountsettings.domain.AccountSettingsEvent.Companion.resolveEventName
import com.sevdesk.common.Failure
import com.sevdesk.common.URN
import com.sevdesk.common.deserializeTo
import com.sevdesk.common.serializeToString
import com.sevdesk.persistence.Event

import com.sevdesk.persistence.EventRepository
import java.time.OffsetDateTime

class AccountSettingsEventStore(private val eventRepository: EventRepository) {

    private val inMemoryEvents = mutableListOf<AccountSettingsEvent>()

    init {
        eventRepository.findByEventTypes(AccountSettingsEvent.names()).also {
            it.onRight { events ->
                val domainEvents = events.map { event ->
                    when (event.eventName.resolveEventName()) {
                        AccountSettingsEvent.AccountSettingsEventName.AccountSettingsCreatedEvent ->
                            event.payload.deserializeTo<AccountSettingsEvent.AccountSettingsCreatedEvent>()

                        AccountSettingsEvent.AccountSettingsEventName.AccountSettingsUpdateFirstnameEvent ->
                            event.payload.deserializeTo<AccountSettingsEvent.AccountSettingsUpdateFirstnameEvent>()

                        AccountSettingsEvent.AccountSettingsEventName.AccountSettingsUpdateLastnameEvent ->
                            event.payload.deserializeTo<AccountSettingsEvent.AccountSettingsUpdateLastnameEvent>()

                        AccountSettingsEvent.AccountSettingsEventName.AccountSettingsUpdatedTaxationTypeEvent ->
                            event.payload.deserializeTo<AccountSettingsEvent.AccountSettingsUpdateTaxationTypeEvent>()

                        AccountSettingsEvent.AccountSettingsEventName.AccountSettingsUpdatedProfitAssessmentEvent ->
                            event.payload.deserializeTo<AccountSettingsEvent.AccountSettingsUpdateProfitAssessmentEvent>()
                    }
                }
                inMemoryEvents.clear()
                inMemoryEvents.addAll(domainEvents)
            }
        }
    }

    fun handleMutation(
        aggregateId: URN,
        createNewEvents: (List<AccountSettingsEvent>) -> Either<NonEmptyList<Failure>, List<AccountSettingsEvent>>
    ): Either<NonEmptyList<Failure>, Unit> {
        return createNewEvents(getEventsByAggregateId(aggregateId))
            .map {
                inMemoryEvents.addAll(it)
                val events = it.map { invoiceEvent ->
                    Event(
                        id = invoiceEvent.eventId,
                        eventName = invoiceEvent.eventName.name,
                        creationDate = OffsetDateTime.now(),
                        payload = invoiceEvent.serializeToString(),
                    )
                }
                eventRepository.saveEvents(events)
            }
    }

    fun getProjection(
        aggregateId: URN,
        buildProjection: (List<AccountSettingsEvent>) -> Either<NonEmptyList<Failure>, AccountSettings>
        ): Either<NonEmptyList<Failure>, AccountSettings> {
        return buildProjection(getEventsByAggregateId(aggregateId))
    }

    private fun getEventsByAggregateId(aggregateId: URN): List<AccountSettingsEvent> {
        return inMemoryEvents.filter { it.aggregateId == aggregateId }
    }
}