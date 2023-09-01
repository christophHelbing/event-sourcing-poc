/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk.invoice.domain

import com.sevdesk.common.URN

sealed interface InvoiceEvent {
    enum class InvoiceEventName {
        InvoiceCreatedEvent,
        InvoiceAmountUpdateEvent,
        InvoiceCommitEvent,
        InvoicePaidEvent,
    }

    val eventId: URN
    val aggregateId: URN
    val eventName: InvoiceEventName

    data class InvoiceCreatedEvent(
        override val eventId: URN = URN("urn:event:${java.util.UUID.randomUUID()}"),
        override val aggregateId: URN,
        val amount: Currency,
    ) : InvoiceEvent {
        override val eventName: InvoiceEventName = InvoiceEventName.InvoiceCreatedEvent
    }

    data class InvoiceAmountUpdateEvent(
        override val eventId: URN = URN("urn:event:${java.util.UUID.randomUUID()}"),
        override val aggregateId: URN,
        val amount: Currency,
    ) : InvoiceEvent {
        override val eventName: InvoiceEventName = InvoiceEventName.InvoiceAmountUpdateEvent
    }

    data class InvoiceCommitEvent(
        override val eventId: URN = URN("urn:event:${java.util.UUID.randomUUID()}"),
        override val aggregateId: URN,
    ): InvoiceEvent {
        override val eventName: InvoiceEventName = InvoiceEventName.InvoiceCommitEvent
    }

    data class InvoicePaidEvent(
        override val eventId: URN = URN("urn:event:${java.util.UUID.randomUUID()}"),
        override val aggregateId: URN,
        val amount: Currency,
    ) : InvoiceEvent {
        override val eventName: InvoiceEventName = InvoiceEventName.InvoicePaidEvent
    }

    companion object {
        fun names(): List<String> = InvoiceEventName.values().map { it.name }
        fun String.resolveEventName(): InvoiceEventName = InvoiceEventName.valueOf(this)
    }
}
