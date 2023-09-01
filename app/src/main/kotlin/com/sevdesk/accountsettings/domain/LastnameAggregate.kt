package com.sevdesk.accountsettings.domain

import kotlin.reflect.KProperty

class LastnameAggregate private constructor(private val events: List<AccountSettingsEvent>){
    companion object {
        operator fun invoke(events: List<AccountSettingsEvent>): LastnameAggregate = LastnameAggregate(
            events = events.filter {
                it is AccountSettingsEvent.AccountSettingsCreatedEvent ||
                        it is AccountSettingsEvent.AccountSettingsUpdateLastnameEvent
            }
        )
    }

    operator fun getValue(accountSettingsAggregate: AccountSettingsAggregate, property: KProperty<*>): String {
        return if (events.any { it is AccountSettingsEvent.AccountSettingsUpdateLastnameEvent }) {
            (events.last() as AccountSettingsEvent.AccountSettingsUpdateLastnameEvent).lastname
        } else {
            (events.last() as AccountSettingsEvent.AccountSettingsCreatedEvent).lastname
        }
    }
}