/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk.invoice.domain

sealed interface InvoiceCommand {

    val invoiceId: URN

    data class PayInvoicePartiallyCommand(override val invoiceId: URN, val amount: Currency) :
        InvoiceCommand

    data class CreateInvoiceCommand(override val invoiceId: URN, val amount: Currency) :
        InvoiceCommand

    data class UpdateInvoiceAmountCommand(
        override val invoiceId: URN,
        val amount: Currency
    ) : InvoiceCommand

    data class SentInvoiceCommand(override val invoiceId: URN) : InvoiceCommand
}
