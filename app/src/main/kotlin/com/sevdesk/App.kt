/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk

import arrow.core.flattenOrAccumulate
import com.sevdesk.common.URN
import com.sevdesk.invoice.EventStore
import com.sevdesk.invoice.InvoiceService
import com.sevdesk.invoice.domain.Currency
import com.sevdesk.invoice.domain.InvoiceCommand
import com.sevdesk.persistence.EventRepository

fun main() {
    val eventStore = EventStore(EventRepository.instance())
    with(InvoiceService(eventStore)) {
        listOf(
            InvoiceCommand.CreateInvoiceCommand(URN("urn:invoice:1"), Currency.of(100)),
            InvoiceCommand.CreateInvoiceCommand(URN("urn:invoice:2"), Currency.of(200)),
            InvoiceCommand.UpdateInvoiceAmountCommand(
                URN("urn:invoice:2"),
                amount = Currency.of(100)
            ),
            InvoiceCommand.UpdateInvoiceAmountCommand(
                URN("urn:invoice:1"),
                amount = Currency.of(100)
            ),
            InvoiceCommand.SentInvoiceCommand(URN("urn:invoice:1")),
            InvoiceCommand.SentInvoiceCommand(URN("urn:invoice:2")),
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
