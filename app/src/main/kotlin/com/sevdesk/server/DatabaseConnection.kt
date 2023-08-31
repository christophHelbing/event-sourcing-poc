package com.sevdesk.server

import io.ktor.server.application.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database


fun Application.connectToDatabase() {
    Database.connect(
        url = configValue(ConfigurationValue.DatabaseUrl),
        user = configValue(ConfigurationValue.DatabaseUser),
        password = configValue(ConfigurationValue.DatabasePassword)
    )
}

fun Application.executeFlywayMigration() {
    Flyway.configure().dataSource(
        configValue(ConfigurationValue.DatabaseUrl),
        configValue(ConfigurationValue.DatabaseUser),
        configValue(ConfigurationValue.DatabasePassword)
    ).load()
}
