package tech.vermorken.contextmigration

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventBus
import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventhandling.EventMessage
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("eventstore-migration")
class EventStoreMigration(
    val eventBus: EventBus
) {

    /**
     * Simple re-publish all events, leaving all fields unchanged.
     */
    @EventHandler
    fun handle(eventMessage: EventMessage<Any>) = eventBus.publish(eventMessage)

}