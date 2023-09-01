package com.sevdesk.persistence

import com.sevdesk.common.URN
import java.time.OffsetDateTime

data class Event (
    val id: URN,
    val eventName: String,
    val creationDate: OffsetDateTime,
    val payload: String,
)
