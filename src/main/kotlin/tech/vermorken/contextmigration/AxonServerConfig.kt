package tech.vermorken.contextmigration

import com.thoughtworks.xstream.XStream
import org.axonframework.axonserver.connector.AxonServerConfiguration
import org.axonframework.axonserver.connector.AxonServerConnectionManager
import org.axonframework.axonserver.connector.event.axon.AxonServerEventStore
import org.axonframework.config.Configuration
import org.axonframework.serialization.Serializer
import org.axonframework.serialization.json.JacksonSerializer
import org.axonframework.serialization.xml.XStreamSerializer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@org.springframework.context.annotation.Configuration
class AxonServerConfig {

    @Bean("eventSerializer")
    @Primary
    fun eventSerializer(): Serializer {
        return JacksonSerializer.builder().lenientDeserialization().build()
    }

    @Bean
    @Primary
    fun newEventStore(eventSerializer: Serializer, configuration: Configuration): AxonServerEventStore {
        return buildAxonServerEventStore(
            configuration,
            eventSerializer,
            "new-context"
        )
    }

    @Bean
    fun oldSerializer(): Serializer {
        val xStream = XStream()
        xStream.allowTypesByWildcard(arrayOf("tech.vermorken.**"))
        return XStreamSerializer.builder()
            .xStream(xStream)
            .lenientDeserialization()
            .build()
    }

    @Bean("oldEventStore")
    fun oldEventStore(@Qualifier("oldSerializer") oldSerializer: Serializer, configuration: Configuration): AxonServerEventStore {
        return buildAxonServerEventStore(
            configuration,
            oldSerializer,
            "old-context"
        )
    }

    private fun buildAxonServerEventStore(
        c: Configuration,
        serializer: Serializer,
        context: String
    ): AxonServerEventStore {
        return AxonServerEventStore.builder()
            .configuration(c.getComponent(AxonServerConfiguration::class.java))
            .platformConnectionManager(c.getComponent(AxonServerConnectionManager::class.java))
            .snapshotFilter(c.snapshotFilter())
            .eventSerializer(serializer)
            .snapshotSerializer(serializer)
            .defaultContext(context)
            .build()
    }
}
