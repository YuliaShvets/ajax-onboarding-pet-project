package ua.lviv.iot.parkingServer.kafka

import io.nats.client.Connection
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import ua.lviv.iot.ParkingSpotOuterClass.CreateParkingSpotRequest
import ua.lviv.iot.ParkingSpotOuterClass.CreateParkingSpotResponse
import ua.lviv.iot.ReactorKafkaParkingSpotServiceGrpc
import ua.lviv.iot.nats.NatsSubject

@Component
class KafkaGrpcService(
    private val connection: Connection
) : ReactorKafkaParkingSpotServiceGrpc.KafkaParkingSpotServiceImplBase() {
    private val responseSink: Sinks.Many<CreateParkingSpotResponse> = Sinks.many().multicast().onBackpressureBuffer()

    @PostConstruct
    fun listenToEvents() {
        val dispatcher = connection.createDispatcher { message ->
            responseSink.tryEmitNext(CreateParkingSpotResponse.parseFrom(message.data))
        }
        dispatcher.subscribe(NatsSubject.ADDED_AVAILABLE_PARKING_SPOT)
    }

    override fun createParkingSpot(
        request: Flux<CreateParkingSpotRequest>
    ): Flux<CreateParkingSpotResponse> {
        return responseSink.asFlux()
    }

}
