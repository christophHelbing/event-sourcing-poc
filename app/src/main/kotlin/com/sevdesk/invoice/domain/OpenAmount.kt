package com.sevdesk.invoice.domain

import kotlin.reflect.KProperty

class OpenAmount(private val events: List<InvoiceEvent>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Currency {
        return Currency(
            (events
                .filterIsInstance<InvoiceEvent.InvoiceCreatedEvent>()
                .sumOf { it.amount.amount } +
                    events.filterIsInstance<InvoiceEvent.InvoiceAmountUpdateEvent>()
                        .sumOf { it.amount.amount }) -
                    events.filterIsInstance<InvoiceEvent.InvoicePaidEvent>()
                        .sumOf { it.amount.amount }
        )
    }
}
