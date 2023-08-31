package com.sevdesk.invoice

import com.sevdesk.persistence.EventRepository
import org.koin.dsl.module

fun invoiceModule() = module {
    single { EventStore(EventRepository.instance()) }
    single { InvoiceService(get()) }
}
