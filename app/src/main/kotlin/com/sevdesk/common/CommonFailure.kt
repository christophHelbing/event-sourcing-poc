package com.sevdesk.common

import kotlinx.serialization.Serializable

sealed interface CommonFailure : Failure {
    @Serializable
    data class EncapsulatedException(
        override val message: String
    ) : Failure

    @Serializable
    data class NotFoundFailure(
        override val message: String
    ) : Failure
}