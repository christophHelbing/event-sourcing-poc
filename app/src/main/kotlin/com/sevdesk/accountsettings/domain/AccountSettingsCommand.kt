package com.sevdesk.accountsettings.domain

import com.sevdesk.common.URN

sealed interface AccountSettingsCommand {
    val userId: URN

    data class CreateAccountSettingsCommand(
        override val userId: URN,
        val firstname: String,
        val lastname: String,
        val taxationType: TaxationType,
        val profitAssessment: ProfitAssessment
    ) : AccountSettingsCommand

    data class UpdateFirstnameCommand(override val userId: URN, val firstname: String) : AccountSettingsCommand
    data class UpdateLastnameCommand(override val userId: URN, val lastname: String) : AccountSettingsCommand
    data class UpdateTaxationTypeCommand(override val userId: URN, val taxationType: TaxationType) :
        AccountSettingsCommand

    data class UpdateProfitAssessmentCommand(override val userId: URN, val profitAssessment: ProfitAssessment) :
        AccountSettingsCommand
}
