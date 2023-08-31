/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk.invoice

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatten
import com.sevdesk.common.Failure
import com.sevdesk.common.deserializeTo
import com.sevdesk.common.serializeToString
import com.sevdesk.invoice.domain.InvoiceEvent
import com.sevdesk.invoice.domain.InvoiceEvent.Companion.resolveEventName
import com.sevdesk.invoice.domain.URN
import com.sevdesk.persistence.Event
import com.sevdesk.persistence.EventRepository
import java.time.OffsetDateTime
import java.util.*

class EventStore(private val eventRepository: EventRepository) {

    private val inMemoryEvents = mutableListOf<InvoiceEvent>()

    init {
        eventRepository.findByEventTypes(InvoiceEvent.names()).also {
            it.onRight { events ->
                val domainEvents = events.map { event ->
                    when (event.eventName.resolveEventName()) {
                        InvoiceEvent.InvoiceEventName.InvoiceCreatedEvent ->
                            event.payload.deserializeTo<InvoiceEvent.InvoiceCreatedEvent>()

                        InvoiceEvent.InvoiceEventName.InvoicePaidEvent ->
                            event.payload.deserializeTo<InvoiceEvent.InvoicePaidEvent>()

                        InvoiceEvent.InvoiceEventName.InvoiceAmountUpdateEvent ->
                            event.payload.deserializeTo<InvoiceEvent.InvoiceAmountUpdateEvent>()

                        InvoiceEvent.InvoiceEventName.InvoiceCommitEvent ->
                            event.payload.deserializeTo<InvoiceEvent.InvoiceCommitEvent>()
                    }
                }
                inMemoryEvents.clear()
                inMemoryEvents.addAll(domainEvents)
            }
        }
    }

    fun handleMutation(
        aggregateId: URN,
        createNewEvents: (List<InvoiceEvent>) -> Either<NonEmptyList<Failure>, List<InvoiceEvent>>
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

    private fun getEventsByAggregateId(aggregateId: URN): List<InvoiceEvent> {
        return inMemoryEvents.filter { it.aggregateId == aggregateId }
    }

    override fun toString(): String {
        return "-----------\nEventStore:\n${inMemoryEvents.joinToString(",\n")}\n-----------"
    }

    fun doSnapshot() {
        val condensedEvents = inMemoryEvents
            .groupBy { it.aggregateId }
            .map { (id, events) ->
                val reducedPaidEvents = events.filterIsInstance<InvoiceEvent.InvoicePaidEvent>()
                    .reduce { acc, t ->
                        InvoiceEvent.InvoicePaidEvent(
                            aggregateId = t.aggregateId,
                            amount = acc.amount + t.amount
                        )
                    }
                id to events.filterIsInstance<InvoiceEvent.InvoiceCreatedEvent>() + reducedPaidEvents
            }.toMap()
        inMemoryEvents.clear()
        inMemoryEvents.addAll(condensedEvents.values.flatten())
    }

}
