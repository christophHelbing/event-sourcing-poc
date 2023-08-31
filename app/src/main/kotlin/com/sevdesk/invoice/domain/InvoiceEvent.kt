/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk.invoice.domain

sealed interface InvoiceEvent {
    enum class InvoiceEventName {
        InvoiceCreatedEvent,
        InvoicePaidEVent,
    }

    val eventId: URN
    val aggregateId: URN
    val amount: Currency
    val eventName: InvoiceEventName

    data class InvoiceCreatedEvent(
        override val eventId: URN = URN("urn:event:${java.util.UUID.randomUUID()}"),
        override val aggregateId: URN,
        override val amount: Currency,
    ) : InvoiceEvent {
        override val eventName: InvoiceEventName = InvoiceEventName.InvoiceCreatedEvent
    }

    data class InvoicePaidEvent(
        override val eventId: URN = URN("urn:event:${java.util.UUID.randomUUID()}"),
        override val aggregateId: URN,
        override val amount: Currency,
    ) : InvoiceEvent {
        override val eventName: InvoiceEventName = InvoiceEventName.InvoicePaidEVent
    }

    companion object {
        fun names(): List<String> = InvoiceEventName.values().map { it.name }
        fun String.resolveEventName(): InvoiceEventName = InvoiceEventName.valueOf(this)
    }
}
