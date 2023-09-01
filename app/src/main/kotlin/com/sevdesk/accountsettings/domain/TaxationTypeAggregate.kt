package com.sevdesk.accountsettings.domain

import kotlin.reflect.KProperty

class TaxationTypeAggregate private constructor(private val events: List<AccountSettingsEvent>) {
    companion object {
        operator fun invoke(events: List<AccountSettingsEvent>): TaxationTypeAggregate = TaxationTypeAggregate(
            events = events.filter {
                it is AccountSettingsEvent.AccountSettingsCreatedEvent ||
                        it is AccountSettingsEvent.AccountSettingsUpdateTaxationTypeEvent
            }
        )
    }

    operator fun getValue(accountSettingsAggregate: AccountSettingsAggregate, property: KProperty<*>): TaxationType {
        return if (events.any { it is AccountSettingsEvent.AccountSettingsUpdateTaxationTypeEvent }) {
            (events.last() as AccountSettingsEvent.AccountSettingsUpdateTaxationTypeEvent).taxationType
        } else {
            (events.last() as AccountSettingsEvent.AccountSettingsCreatedEvent).taxationType
        }
    }

}