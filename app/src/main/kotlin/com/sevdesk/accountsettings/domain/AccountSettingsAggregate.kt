package com.sevdesk.accountsettings.domain

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate
import arrow.core.right
import com.sevdesk.accountsettings.AccountSettingsFailure
import com.sevdesk.common.Failure
import com.sevdesk.common.URN

class AccountSettingsAggregate private constructor( events: List<AccountSettingsEvent>) {
    private val firstName by FirstnameAggregate(events)
    private val lastname by LastnameAggregate(events)
    private val taxationType by TaxationTypeAggregate(events)

    companion object {
        operator fun invoke(events: List<AccountSettingsEvent>): Either<NonEmptyList<Failure>, AccountSettingsAggregate> =
            either {
                zipOrAccumulate({
                    ensure(events.isEmpty() || events.first() is AccountSettingsEvent.AccountSettingsCreatedEvent) {
                        AccountSettingsFailure.InvalidAggregate(
                            "Event stream for AccountSettings have to start with an AccountSettingsCreatedEvent"
                        )
                    }
                }, {}) { _, _ -> AccountSettingsAggregate(events) }
            }
    }

    fun handle(command: AccountSettingsCommand): Either<NonEmptyList<Failure>, List<AccountSettingsEvent>> {
        return when (command) {
            is AccountSettingsCommand.CreateAccountSettingsCommand -> {
                listOf(
                    AccountSettingsEvent.AccountSettingsCreatedEvent(
                        aggregateId = command.userId,
                        firstname = command.firstname,
                        lastname = command.lastname,
                        taxationType = command.taxationType,
                        profitAssessment = command.profitAssessment,
                    )
                ).right()
            }

            is AccountSettingsCommand.UpdateFirstnameCommand -> {
                listOf(
                    AccountSettingsEvent.AccountSettingsUpdateFirstnameEvent(
                        aggregateId = command.userId,
                        firstname = command.firstname,
                    )
                ).right()
            }
            is AccountSettingsCommand.UpdateLastnameCommand -> {
                listOf(
                    AccountSettingsEvent.AccountSettingsUpdateLastnameEvent(
                        aggregateId = command.userId,
                        lastname = command.lastname,
                    )
                ).right()
            }
            is AccountSettingsCommand.UpdateProfitAssessmentCommand -> {
                listOf(
                    AccountSettingsEvent.AccountSettingsUpdateProfitAssessmentEvent(
                        aggregateId = command.userId,
                        profitAssessment = command.profitAssessment,
                    )
                ).right()
            }
            is AccountSettingsCommand.UpdateTaxationTypeCommand -> {
                listOf(
                    AccountSettingsEvent.AccountSettingsUpdateTaxationTypeEvent(
                        aggregateId = command.userId,
                        taxationType = command.taxationType,
                    )
                ).right()
            }
        }
    }

    fun getAccountSettings(): Either<NonEmptyList<Failure>, AccountSettings> =
        AccountSettings(
            userId = URN("1"),
            firstname = firstName,
            lastname = lastname,
            taxationType = taxationType,
            profitAssessment = ProfitAssessment.EUR,
        ).right()
}