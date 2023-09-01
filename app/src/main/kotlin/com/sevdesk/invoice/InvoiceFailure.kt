/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk.invoice

import com.sevdesk.common.Failure
import kotlinx.serialization.Serializable

sealed interface InvoiceFailure : Failure {
    @Serializable
    data class InvalidAggregate(override val message: String) : InvoiceFailure
    @Serializable
    data class InvoiceAlreadyPaid(override val message: String) : InvoiceFailure
    @Serializable
    data class InvoiceNotCommittedYet(override val message: String): InvoiceFailure
    @Serializable
    data class InvoiceAlreadyCommitted(override val message: String): InvoiceFailure
}
