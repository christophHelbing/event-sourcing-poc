package com.sevdesk.common

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.nel
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.util.pipeline.PipelineContext

fun PipelineContext<Unit, ApplicationCall>.getParameter(parameterName: String): Either<NonEmptyList<Failure>, String> =
    Either.catch { call.parameters[parameterName]!! }
        .mapLeft {
            CommonFailure.EncapsulatedException(
                message = it.localizedMessage
            ).nel()
        }

suspend inline fun <reified T : Any> PipelineContext<Unit, ApplicationCall>.receiveDto(): Either<NonEmptyList<Failure>, T> =
    Either.catch { call.receive<T>() }
        .mapLeft {
            CommonFailure.EncapsulatedException(
                message = it.localizedMessage
            ).nel()
        }

fun PipelineContext<Unit, ApplicationCall>.getQueryParameter(parameterName: String): Either<NonEmptyList<Failure>, String> =
    either {
        val queryParameter = call.request.queryParameters[parameterName]
        ensureNotNull(queryParameter) {
            CommonFailure.NotFoundFailure("Query parameter $parameterName was not found").nel()
        }
        queryParameter
    }
