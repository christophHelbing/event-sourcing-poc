/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk.invoice.domain

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.sevdesk.common.Failure
import com.sevdesk.invoice.InvoiceFailure

class InvoiceAggregate private constructor(events: List<InvoiceEvent>) {

    private val openAmount by lazy {
        Currency(events
                .filterIsInstance<InvoiceEvent.InvoiceCreatedEvent>().sumOf { it.amount.amount } - events
                .filterIsInstance<InvoiceEvent.InvoicePaidEvent>().sumOf { it.amount.amount }
        )
    }

    fun handle(command: InvoiceCommand): Either<Failure, List<InvoiceEvent>> {
        return when (command) {
            is InvoiceCommand.PayInvoicePartiallyCommand ->
                payInvoice(command)

            is InvoiceCommand.CreateInvoiceCommand ->
                listOf(InvoiceEvent.InvoiceCreatedEvent(aggregateId = command.invoiceId, amount = command.amount))
                        .also {
                            println("Invoice created!")
                        }
                        .right()
        }
    }

    private fun payInvoice(command: InvoiceCommand.PayInvoicePartiallyCommand): Either<Failure, List<InvoiceEvent>> =
            if (command.amount > openAmount) {
                InvoiceFailure
                        .InvoiceAlreadyPaid("Invoice already paid!")
                        .left()
            } else {
                listOf(InvoiceEvent.InvoicePaidEvent(aggregateId = command.invoiceId, amount = command.amount))
                        .right()
            }

    companion object {
        fun fromEvents(events: List<InvoiceEvent>): Either<Failure, InvoiceAggregate> =
                if (events.isEmpty() || events.first() is InvoiceEvent.InvoiceCreatedEvent) {
                    InvoiceAggregate(events).right()
                } else {
                    InvoiceFailure
                            .InvalidAggregate("Invalid event stream! Expected at least" +
                                    " on event of type InvoiceCreatedEvent!")
                            .left()
                }
    }
}
