/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk.invoice

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.raise.Raise
import com.sevdesk.common.Failure
import com.sevdesk.invoice.domain.InvoiceCommand
import com.sevdesk.invoice.domain.InvoiceAggregate

class InvoiceService(
        private val eventStore: EventStore,
) {

    fun handleCommand(command: InvoiceCommand): Either<Failure, Unit> =
            println("handling command ${command.javaClass.simpleName} with id ${command.invoiceId}")
                    .run {
                        eventStore.handleMutation(command.invoiceId) { events ->
                            InvoiceAggregate.fromEvents(events)
                                    .flatMap { it.handle(command) }
                        }
                    }

}
