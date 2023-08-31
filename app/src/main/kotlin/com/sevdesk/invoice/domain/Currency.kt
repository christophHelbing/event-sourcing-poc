/*
 * Copyright (c) 2023, sevDesk
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */
package com.sevdesk.invoice.domain

import java.math.BigInteger

@JvmInline
value class Currency(val amount: BigInteger) {
    operator fun compareTo(other: Currency): Int = amount.compareTo(other.amount)

    operator fun plus(other: Currency): Currency = Currency(amount.plus(other.amount))

    companion object {
        fun of(amount: Int) = Currency(BigInteger.valueOf(amount.toLong()))
    }
}
