package com.sevdesk.persistence

import arrow.core.Either
import arrow.core.right
import com.sevdesk.common.Failure

interface EventRepository {
    fun saveEvents(events: List<Event>): Either<Failure, Unit>

    fun findByEventTypes(eventType: List<String>): Either<Failure, List<Event>>

    companion object {
        fun instance(): EventRepository = InMemoryEventRepository
    }
}

object InMemoryEventRepository : EventRepository {
    private val eventStore = mutableListOf<Event>()

    override fun saveEvents(events: List<Event>): Either<Failure, Unit> {
        eventStore.addAll(events)
        return Unit.right()
    }

    override fun findByEventTypes(eventType: List<String>): Either<Failure, List<Event>> {
        return eventStore.filter { eventType.contains(it.eventName) }.sortedBy { it.creationDate }
            .right()
    }

}
