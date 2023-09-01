package com.sevdesk.accountsettings.domain

import com.sevdesk.common.URN

sealed interface AccountSettingsEvent {
    enum class AccountSettingsEventName {
        AccountSettingsCreatedEvent,
        AccountSettingsUpdateFirstnameEvent,
        AccountSettingsUpdateLastnameEvent,
        AccountSettingsUpdatedTaxationTypeEvent,
        AccountSettingsUpdatedProfitAssessmentEvent,
    }

    val eventId: URN
    val aggregateId: URN
    val eventName: AccountSettingsEventName

    data class AccountSettingsCreatedEvent(
        override val eventId: URN = URN("urn:event:${java.util.UUID.randomUUID()}"),
        override val aggregateId: URN,
        val firstname: String,
        val lastname: String,
        val taxationType: TaxationType,
        val profitAssessment: ProfitAssessment,
    ) : AccountSettingsEvent {
        override val eventName: AccountSettingsEventName = AccountSettingsEventName.AccountSettingsCreatedEvent
    }

    data class AccountSettingsUpdateFirstnameEvent(
        override val eventId: URN = URN("urn:event:${java.util.UUID.randomUUID()}"),
        override val aggregateId: URN,
        val firstname: String,
    ) : AccountSettingsEvent {
        override val eventName: AccountSettingsEventName = AccountSettingsEventName.AccountSettingsUpdateFirstnameEvent
    }

    data class AccountSettingsUpdateLastnameEvent(
        override val eventId: URN = URN("urn:event:${java.util.UUID.randomUUID()}"),
        override val aggregateId: URN,
        val lastname: String,
    ) : AccountSettingsEvent {
        override val eventName: AccountSettingsEventName = AccountSettingsEventName.AccountSettingsUpdateLastnameEvent
    }

    data class AccountSettingsUpdateTaxationTypeEvent(
        override val eventId: URN = URN("urn:event:${java.util.UUID.randomUUID()}"),
        override val aggregateId: URN,
        val taxationType: TaxationType,
    ) : AccountSettingsEvent {
        override val eventName: AccountSettingsEventName =
            AccountSettingsEventName.AccountSettingsUpdatedTaxationTypeEvent
    }

    data class AccountSettingsUpdateProfitAssessmentEvent(
        override val eventId: URN = URN("urn:event:${java.util.UUID.randomUUID()}"),
        override val aggregateId: URN,
        val profitAssessment: ProfitAssessment,
    ) : AccountSettingsEvent {
        override val eventName: AccountSettingsEventName =
            AccountSettingsEventName.AccountSettingsUpdatedProfitAssessmentEvent
    }

    companion object {
        fun names(): List<String> = AccountSettingsEventName.entries.map { it.name }
        fun String.resolveEventName(): AccountSettingsEventName =
            AccountSettingsEventName.valueOf(this)
    }
}