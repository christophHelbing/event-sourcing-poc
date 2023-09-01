package com.sevdesk.accountsettings.https

import arrow.core.raise.either
import com.sevdesk.accountsettings.AccountSettingsWriteService
import com.sevdesk.accountsettings.domain.AccountSettingsCommand
import com.sevdesk.accountsettings.domain.ProfitAssessment
import com.sevdesk.accountsettings.domain.TaxationType
import com.sevdesk.common.URN
import com.sevdesk.common.receiveDto
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

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

            // TODO Refactor the put endpoints to one endpoint which consume a map to change the certain attribute
            put {
                either {
                    val dto = receiveDto<UpdateFirstnameDto>().bind()
                    accountSettingsService.handleCommand(
                        AccountSettingsCommand.UpdateFirstnameCommand(
                            userId = URN(dto.userId),
                            firstname = dto.firstname,
                        )
                    )
                }.fold({
                    call.respond(HttpStatusCode.BadRequest, it)
                }) {
                    call.respond(HttpStatusCode.Accepted)
                }
            }

            put("/lastname") {
                either {
                    val dto = receiveDto<UpdateLastnameDto>().bind()
                    accountSettingsService.handleCommand(
                        AccountSettingsCommand.UpdateLastnameCommand(
                            userId = URN(dto.userId),
                            lastname = dto.lastname,
                        )
                    )
                }.fold({
                    call.respond(HttpStatusCode.BadRequest, it)
                }) {
                    call.respond(HttpStatusCode.Accepted)
                }
            }

            put("/taxationtype") {
                either {
                    val dto = receiveDto<UpdateTaxationTypeDto>().bind()
                    accountSettingsService.handleCommand(
                        AccountSettingsCommand.UpdateTaxationTypeCommand(
                            userId = URN(dto.userId),
                            taxationType = TaxationType.valueOf(dto.taxationType),
                        )
                    )
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
data class UpdateTaxationTypeDto (
    val userId: String,
    val taxationType: String,
)

@Serializable
data class UpdateFirstnameDto(
    val userId: String,
    val firstname: String,
)

@Serializable
data class UpdateLastnameDto(
    val userId: String,
    val lastname: String,
)

@Serializable
data class CreateAccountSettingsDto(
    val userId: String,
    val firstname: String,
    val lastname: String,
    val taxationType: TaxationType,
    val profitAssessment: ProfitAssessment,
)
