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
import com.sevdesk.invoice.domain.InvoiceEvent
import com.sevdesk.invoice.domain.URN
import java.math.BigInteger

class EventStore {

    private val inMemoryEvents = mutableListOf<InvoiceEvent>()

    fun handleMutation(
        aggregateId: URN,
        createNewEvents: (List<InvoiceEvent>) -> Either<NonEmptyList<Failure>, List<InvoiceEvent>>
    ): Either<NonEmptyList<Failure>, Unit> {
        return createNewEvents(getEventsByAggregateId(aggregateId))
            .map {
                inMemoryEvents.addAll(it)
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
