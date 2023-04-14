/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk.invoice

import com.sevdesk.common.Failure

sealed class InvoiceFailure : Failure {
    data class InvalidAggregate(override val message: String) : InvoiceFailure()
    data class InvoiceAlreadyPaid(override val message: String) : InvoiceFailure()
}
