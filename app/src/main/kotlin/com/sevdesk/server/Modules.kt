package com.sevdesk.server

import com.sevdesk.accountsettings.accountSettingsModule
import com.sevdesk.accountsettings.https.installAccountSettingsReadRoutes
import com.sevdesk.accountsettings.https.installAccountSettingsWriteRoutes
import com.sevdesk.invoice.installInvoiceRoutes
import com.sevdesk.invoice.invoiceModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.Koin
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

object GlobalKoinContext {
    var koin: Koin? = null

    fun koin() = koin!!
}

fun Application.modules() {
    configure(isDevelopmentMode = booleanConfigValue(ConfigurationValue.DevelopmentMode))
    install(Koin) {
        slf4jLogger()
        val app = modules(
            invoiceModule(),
            accountSettingsModule(),
        )
        GlobalKoinContext.koin = app.koin
    }
//    connectToDatabase()
//    executeFlywayMigration()
    installRoutes()
}

private fun Application.installRoutes() {
    installInvoiceRoutes()
    installAccountSettingsWriteRoutes()
    installAccountSettingsReadRoutes()
}
