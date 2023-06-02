## Context Migration Example
This is a small sample Spring Boot sample application that can migrate events from one context to another within Axon Server, with the goal of changing the serialized format of you events.

By configuring two different AxonServerEventStores, we can also configure different Serializers for them. In this example, the event store we read events from uses XStream, while we write our events using Jackson. When migrating events without changing their serialized form, a single AxonServerEventStore would suffice, from which you can create a StreamableMessageSource for the old context to read from.

Alternatively, this same concept can be used to filter or migrate events in another way, some examples of this may be:
- Filter out obsolete events
- Encrypt sensitive personal data that was previously not encrypted
- Split or merge an aggregate

## TokenStore
In this sample, there is no TokenStore configured, resulting in an InMemoryTokenStore being used. In a real implementation of this, it is necessary to configure your own TokenStore to avoid **all** events from being migrated every time this application starts.

## SampleEventPublisher
Running this application with the _publish-sample-events_ Spring profile will publish a few sample events to the old context, using the old XStreamSerializer. These are the events that would be migrated and as such can be omitted if there are already events to migrate in place.