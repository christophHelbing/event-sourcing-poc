/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk.invoice

import com.sevdesk.common.Failure

sealed interface InvoiceFailure : Failure {
    data class InvalidAggregate(override val message: String) : InvoiceFailure
    data class InvoiceAlreadyPaid(override val message: String) : InvoiceFailure
    data class InvoiceNotCommittedYet(override val message: String): InvoiceFailure
    data class InvoiceAlreadyCommitted(override val message: String): InvoiceFailure
}
