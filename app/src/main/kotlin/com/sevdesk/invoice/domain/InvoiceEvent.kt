/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk.invoice.domain

sealed interface InvoiceEvent {
    val eventId: URN
    val aggregateId: URN
    val amount: Currency

    data class InvoiceCreatedEvent(
            override val eventId: URN = URN("urn:event:${java.util.UUID.randomUUID()}"),
            override val aggregateId: URN,
            override val amount: Currency,
    ) : InvoiceEvent

    data class InvoicePaidEvent(
            override val eventId: URN = URN("urn:event:${java.util.UUID.randomUUID()}"),
            override val aggregateId: URN,
            override val amount: Currency,
    ) : InvoiceEvent
}
