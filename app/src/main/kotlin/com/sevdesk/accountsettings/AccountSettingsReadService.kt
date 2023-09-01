package com.sevdesk.accountsettings

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import com.sevdesk.accountsettings.domain.AccountSettings
import com.sevdesk.accountsettings.domain.AccountSettingsAggregate
import com.sevdesk.common.Failure
import com.sevdesk.common.URN

class AccountSettingsReadService(private val eventStore: AccountSettingsEventStore) {
    fun getAccountSettings(userId: URN): Either<NonEmptyList<Failure>, AccountSettings> =
        println("Try to read the account setting for userId: $userId")
            .run {
                eventStore.getProjection(userId) {events ->
                    AccountSettingsAggregate(events).flatMap {
                        it.getAccountSettings()
                    }
                }
            }
}