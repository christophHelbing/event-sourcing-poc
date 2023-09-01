package com.sevdesk.accountsettings

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.flatMap
import com.sevdesk.accountsettings.domain.AccountSettingsAggregate
import com.sevdesk.accountsettings.domain.AccountSettingsCommand
import com.sevdesk.common.Failure

class AccountSettingsWriteService(val eventStore: AccountSettingsEventStore) {

    fun handleCommand(command: AccountSettingsCommand): Either<NonEmptyList<Failure>, Unit> =
        println("handling command ${command.javaClass.simpleName} with id ${command.userId}")
            .run {
                eventStore.handleMutation(command.userId) { events ->
                    AccountSettingsAggregate(events)
                        .flatMap {
                            it.handle(command)
                        }
                }
            }
}