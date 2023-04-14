/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk

import arrow.core.Either
import arrow.core.Either.Companion.zipOrAccumulate
import arrow.core.flattenOrAccumulate
import arrow.core.mapOrAccumulate
import arrow.core.nonEmptyListOf
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.mapOrAccumulate
import arrow.core.raise.zipOrAccumulate
import com.sevdesk.invoice.domain.InvoiceCommand
import com.sevdesk.invoice.EventStore
import com.sevdesk.invoice.InvoiceService
import com.sevdesk.invoice.domain.Currency
import com.sevdesk.invoice.domain.URN

fun main() {
    with(InvoiceService(EventStore())) {
        listOf(
                InvoiceCommand.CreateInvoiceCommand(URN("urn:invoice:1"), Currency.of(100)),
                InvoiceCommand.CreateInvoiceCommand(URN("urn:invoice:2"), Currency.of(200)),
                InvoiceCommand.PayInvoicePartiallyCommand(URN("urn:invoice:1"), Currency.of(50)),
                InvoiceCommand.PayInvoicePartiallyCommand(URN("urn:invoice:1"), Currency.of(50)),
                InvoiceCommand.PayInvoicePartiallyCommand(URN("urn:invoice:2"), Currency.of(150)),
                InvoiceCommand.PayInvoicePartiallyCommand(URN("urn:invoice:1"), Currency.of(50)),
                InvoiceCommand.PayInvoicePartiallyCommand(URN("urn:invoice:2"), Currency.of(150)),
                InvoiceCommand.PayInvoicePartiallyCommand(URN("urn:invoice:1"), Currency.of(50)),
        ).map { handleCommand(it) }.forEach {
            // todo - very bad code, make it happen using arrow 2.0 stuff
            it.fold({ failure ->
                println("Error: $failure")
            }, {
                println("Success")
            })
        }
    }
}

/*
 */
