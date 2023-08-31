package com.sevdesk.invoice

import com.sevdesk.invoice.domain.Currency
import com.sevdesk.invoice.domain.InvoiceCommand
import com.sevdesk.invoice.domain.URN
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
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
