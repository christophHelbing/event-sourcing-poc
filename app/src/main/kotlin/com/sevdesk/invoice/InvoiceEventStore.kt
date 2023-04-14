/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk.invoice

import arrow.core.Either
import com.sevdesk.common.Failure
import com.sevdesk.invoice.domain.InvoiceEvent
import com.sevdesk.invoice.domain.URN

class EventStore {

    private val inMemoryEvents = mutableListOf<InvoiceEvent>()

    fun handleMutation(
            aggregateId: URN,
            createNewEvents: (List<InvoiceEvent>) -> Either<Failure, List<InvoiceEvent>>
    ): Either<Failure, Unit> {
        return createNewEvents(getEventsByAggregateId(aggregateId))
                .map {
                    inMemoryEvents.addAll(it)
                }
    }

    private fun getEventsByAggregateId(aggregateId: URN): List<InvoiceEvent> {
        return inMemoryEvents.filter { it.aggregateId == aggregateId }
    }

}
