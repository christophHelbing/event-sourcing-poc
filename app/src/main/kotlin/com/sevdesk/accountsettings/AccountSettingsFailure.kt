package com.sevdesk.accountsettings

import com.sevdesk.common.Failure
import kotlinx.serialization.Serializable

sealed interface AccountSettingsFailure : Failure {
    @Serializable
    data class InvalidAggregate(override val message: String) : AccountSettingsFailure
}