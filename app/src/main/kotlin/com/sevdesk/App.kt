/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk

import arrow.core.flattenOrAccumulate
import com.sevdesk.invoice.EventStore
import com.sevdesk.invoice.InvoiceService
import com.sevdesk.invoice.domain.Currency
import com.sevdesk.invoice.domain.InvoiceCommand
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
        ).map { handleCommand(it) }
            .flattenOrAccumulate()
            .fold({
                println("Having failures!")
                println(it)
            }) {
                println("Success")
            }
    }
}

/*
 */
