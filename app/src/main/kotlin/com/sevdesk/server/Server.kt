package com.sevdesk.server

import arrow.fx.coroutines.Resource
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun <TEngine : ApplicationEngine, TConfiguration : ApplicationEngine.Configuration> server(
    factory: ApplicationEngineFactory<TEngine, TConfiguration>,
    configure: TConfiguration.() -> Unit = {},
    preWait: Duration = 30.seconds,
    grace: Duration = 1.seconds,
    timeout: Duration = 5.seconds,
): Resource<ApplicationEngine> =
    Resource({
        embeddedServer(factory, environment = applicationEngineEnvironment {
            config = HoconApplicationConfig(ConfigFactory.load())
            connector {
                port = config.port
                host = config.host
            }
            watchPaths = listOf("classes")
        }, configure = configure).apply { start() }
    }, { engine, _ ->
        if (!engine.environment.developmentMode) {
            engine.environment.log.info(
                "prewait delay of ${preWait.inWholeMilliseconds}ms, turn it off using io.ktor.development=true"
            )
            delay(preWait.inWholeMilliseconds)
        }
        engine.environment.log.info("Shutting down HTTP server...")
        engine.stop(grace.inWholeMilliseconds, timeout.inWholeMilliseconds)
        engine.environment.log.info("HTTP server shutdown!")
    })
