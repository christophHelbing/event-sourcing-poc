package com.sevdesk.accountsettings.domain

import com.sevdesk.common.URN

data class AccountSettings (
    val userId: URN,
    val firstname: String,
    val lastname: String,
    val taxationType: TaxationType,
    val profitAssessment: ProfitAssessment
)

enum class TaxationType {
    SOLL,
    IST,
}

enum class ProfitAssessment {
    EUR,
    GUV,
}