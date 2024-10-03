package tech.vermorken.contextmigration.eventpublisher

import jakarta.annotation.PostConstruct
import org.axonframework.axonserver.connector.event.axon.AxonServerEventStore
import org.axonframework.eventhandling.GenericDomainEventMessage
import org.axonframework.messaging.MetaData
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.*

const val AGGREGATE_TYPE = "tech.vermorken.MyAggregate"
data class SampleEvent(val id: UUID, val someField: String, val otherField: Int)

/**
 * Enable this to publish some basic events using to the old context using the old serializer.
 * These would be the events that the {@link EventStoreMigration} would migrate to a new context with a new Serializer.
 * If old events are already in place, this can be omitted.
 */
@Component
@Profile("publish-sample-events")
class SampleEventPublisher(@Qualifier("oldEventStore") val eventBus: AxonServerEventStore) {

    @PostConstruct
    fun publishEvents() {
        val aggregateId = UUID.randomUUID().toString()
        for (i in 0..9) {
            eventBus.publish(GenericDomainEventMessage(AGGREGATE_TYPE, aggregateId, i.toLong(), SampleEvent(UUID.randomUUID(), "value${i}", i), MetaData.with("testKey", "testValue")))
        }
    }
}
