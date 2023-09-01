package com.sevdesk.accountsettings.domain

import kotlin.reflect.KProperty

class FirstnameAggregate private constructor(private val events: List<AccountSettingsEvent>) {

    companion object {
        operator fun invoke(events: List<AccountSettingsEvent>): FirstnameAggregate = FirstnameAggregate(
            events = events.filter {
                it is AccountSettingsEvent.AccountSettingsCreatedEvent ||
                        it is AccountSettingsEvent.AccountSettingsUpdateFirstnameEvent
            }
        )
    }

    operator fun getValue(accountSettingsAggregate: AccountSettingsAggregate, property: KProperty<*>): String {
        return if (events.any { it is AccountSettingsEvent.AccountSettingsUpdateFirstnameEvent }) {
            (events.last() as AccountSettingsEvent.AccountSettingsUpdateFirstnameEvent).firstname
        } else {
            (events.last() as AccountSettingsEvent.AccountSettingsCreatedEvent).firstname
        }
    }
}
