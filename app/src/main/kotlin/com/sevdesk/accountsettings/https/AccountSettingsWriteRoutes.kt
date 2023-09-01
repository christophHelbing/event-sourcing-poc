package com.sevdesk.accountsettings.https

import arrow.core.leftNel
import arrow.core.raise.either
import arrow.core.right
import com.sevdesk.accountsettings.AccountSettingsWriteService
import com.sevdesk.accountsettings.domain.AccountSettingsCommand
import com.sevdesk.accountsettings.domain.ProfitAssessment
import com.sevdesk.accountsettings.domain.TaxationType
import com.sevdesk.common.CommonFailure
import com.sevdesk.common.URN
import com.sevdesk.common.receiveDto
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

private const val FIRST_NAME = "firstname"
private const val LAST_NAME = "lastname"
private const val TAXATION_TYPE = "taxationType"

fun Application.installAccountSettingsWriteRoutes() {
    val accountSettingsService by inject<AccountSettingsWriteService>()

    routing {
        route("/accountsettings") {
            post {
                either {
                    val dto = receiveDto<CreateAccountSettingsDto>().bind()
                    accountSettingsService.handleCommand(
                        AccountSettingsCommand.CreateAccountSettingsCommand(
                            userId = URN(dto.userId),
                            firstname = dto.firstname,
                            lastname = dto.lastname,
                            taxationType = dto.taxationType,
                            profitAssessment = dto.profitAssessment,
                        )
                    ).bind()
                }.fold({
                    call.respond(HttpStatusCode.BadRequest, it)
                }) {
                    call.respond(HttpStatusCode.Accepted)
                }
            }

            patch {
                either {
                    val dto = receiveDto<UpdateAccountSettings>().bind()
                    val command = when (dto.changes.keys.first()) {
                        FIRST_NAME -> AccountSettingsCommand.UpdateFirstnameCommand(
                            userId = URN(dto.userId),
                            firstname = dto.changes[FIRST_NAME]!!,
                        ).right()

                        LAST_NAME -> AccountSettingsCommand.UpdateLastnameCommand(
                            userId = URN(dto.userId),
                            lastname = dto.changes[LAST_NAME]!!
                        ).right()

                        TAXATION_TYPE -> AccountSettingsCommand.UpdateTaxationTypeCommand(
                            userId = URN(dto.userId),
                            taxationType = TaxationType.valueOf(dto.changes[TAXATION_TYPE]!!),
                        ).right()

                        else -> CommonFailure.NotFoundFailure(
                            "Map does not contain one of the accepted changable attributes"
                        ).leftNel()
                    }.bind()
                    accountSettingsService.handleCommand(command)
                }.fold({
                    call.respond(HttpStatusCode.BadRequest, it)
                }) {
                    call.respond(HttpStatusCode.Accepted)
                }
            }
        }
    }
}
@Serializable
data class UpdateAccountSettings(
    val userId: String,
    val changes: Map<String, String>,
)

@Serializable
data class CreateAccountSettingsDto(
    val userId: String,
    val firstname: String,
    val lastname: String,
    val taxationType: TaxationType,
    val profitAssessment: ProfitAssessment,
)
