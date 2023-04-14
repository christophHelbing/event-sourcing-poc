/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk.invoice.domain

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.left
import arrow.core.nonEmptyListOf
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate
import arrow.core.right
import com.sevdesk.common.Failure
import com.sevdesk.invoice.InvoiceFailure

class InvoiceAggregate private constructor(events: List<InvoiceEvent>) {

    companion object {
        operator fun invoke(events: List<InvoiceEvent>): Either<NonEmptyList<Failure>, InvoiceAggregate> = either {
            zipOrAccumulate(
                {
                    ensure(events.isEmpty() || events.first() is InvoiceEvent.InvoiceCreatedEvent) {
                        InvoiceFailure.InvalidAggregate(
                            "Event stream must start with either an empty list or an InvoiceCreatedEvent"
                        )
                    }
                },
                {} // todo - this is not clean and we should introduce a better way at some point. Zip expects at least two components for merging
            ) { _, _ -> InvoiceAggregate(events) }
        }
    }

    private val openAmount by lazy {
        Currency(events
            .filterIsInstance<InvoiceEvent.InvoiceCreatedEvent>().sumOf { it.amount.amount } - events
            .filterIsInstance<InvoiceEvent.InvoicePaidEvent>().sumOf { it.amount.amount }
        )
    }

    fun handle(command: InvoiceCommand): Either<NonEmptyList<Failure>, List<InvoiceEvent>> {
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

    private fun payInvoice(command: InvoiceCommand.PayInvoicePartiallyCommand):
            Either<NonEmptyList<Failure>, List<InvoiceEvent>> =
        if (command.amount > openAmount) {
            nonEmptyListOf(
                InvoiceFailure
                    .InvoiceAlreadyPaid("Invoice already paid!")
            ).left()
        } else {
            listOf(InvoiceEvent.InvoicePaidEvent(aggregateId = command.invoiceId, amount = command.amount))
                .right()
        }
}
