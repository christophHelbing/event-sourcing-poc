package com.sevdesk.accountsettings.https

import arrow.core.raise.either
import com.sevdesk.accountsettings.AccountSettingsReadService
import com.sevdesk.accountsettings.domain.ProfitAssessment
import com.sevdesk.accountsettings.domain.TaxationType
import com.sevdesk.common.URN
import com.sevdesk.common.getParameter
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

fun Application.installAccountSettingsReadRoutes() {
    val accountSettingsReadService by inject<AccountSettingsReadService>()
    routing {
        route("/accountsettings/{userId}") {
            get {
                either {
                    val userId = getParameter("userId").bind()
                    accountSettingsReadService.getAccountSettings(URN(userId)).bind()
                }.fold({
                    call.respond(HttpStatusCode.BadRequest, it)
                }) {
                    call.respond(
                        AccountSettingsDto(
                            userId = it.userId.value,
                            firstname = it.firstname,
                            lastname = it.lastname,
                            taxationType = it.taxationType,
                            profitAssessment = it.profitAssessment,
                        )
                    )
                }
            }
        }
    }
}

@Serializable
data class AccountSettingsDto(
    val userId: String,
    val firstname: String,
    val lastname: String,
    val taxationType: TaxationType,
    val profitAssessment: ProfitAssessment,
)