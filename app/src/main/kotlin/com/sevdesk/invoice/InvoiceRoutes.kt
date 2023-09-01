package com.sevdesk.invoice

import com.sevdesk.common.URN
import com.sevdesk.invoice.domain.Currency
import com.sevdesk.invoice.domain.InvoiceCommand
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject
import java.math.BigInteger

@Serializable
data class CreateInvoiceDto(
    val id: String,
    val amount: Int,
)

fun Application.installInvoiceRoutes() {
    val invoiceService by inject<InvoiceService>()
    routing {
        route("/invoice") {
            post {
                val dto = call.receive<CreateInvoiceDto>()
                invoiceService.handleCommand(
                    InvoiceCommand.CreateInvoiceCommand(
                        URN(dto.id), Currency(
                            BigInteger.valueOf(dto.amount.toLong())
                        )
                    )
                ).fold({
                    call.respond(HttpStatusCode.BadRequest, it)
                }) {
                    call.respond(HttpStatusCode.Accepted)
                }
            }
            put {
            }
        }
    }
}
