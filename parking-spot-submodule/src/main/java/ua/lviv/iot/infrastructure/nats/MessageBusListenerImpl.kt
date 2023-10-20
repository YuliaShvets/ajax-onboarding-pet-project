package ua.lviv.iot.infrastructure.nats

import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Sinks
import ua.lviv.iot.ParkingSpotOuterClass
import ua.lviv.iot.nats.NatsSubject

@Component
class MessageBusListenerImpl(
    private val connection: Connection,
) : MessageBusListener {

    override val responseSink: Sinks.Many<ParkingSpotOuterClass.CreateParkingSpotResponse> =
        Sinks.many().multicast().onBackpressureBuffer()

    override fun listenToEvents() {
        val dispatcher = connection.createDispatcher { message ->
            responseSink.tryEmitNext(ParkingSpotOuterClass.CreateParkingSpotResponse.parseFrom(message.data))
        }
        dispatcher.subscribe(NatsSubject.ADDED_AVAILABLE_PARKING_SPOT)
    }
}
