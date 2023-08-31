package com.sevdesk.invoice.domain

import kotlin.reflect.KProperty

class InvoiceCommitted(private val events: List<InvoiceEvent>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean =
        events.any { it is InvoiceEvent.InvoiceCommitEvent }
}
