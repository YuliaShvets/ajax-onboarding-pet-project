package ua.lviv.iot.infrastructure.grpc

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import ua.lviv.iot.ParkingSpotOuterClass.CreateParkingSpotRequest
import ua.lviv.iot.ParkingSpotOuterClass.CreateParkingSpotResponse
import ua.lviv.iot.ReactorParkingSpotServiceGrpc
import ua.lviv.iot.infrastructure.nats.NatsListener

@Component
class GrpcService(
    val natsListener: NatsListener
) : ReactorParkingSpotServiceGrpc.ParkingSpotServiceImplBase() {

    @PostConstruct
    fun listenToEvents() {
        natsListener.listenToEvents()
    }

    override fun createParkingSpot(
        request: Flux<CreateParkingSpotRequest>
    ): Flux<CreateParkingSpotResponse> {
        return natsListener.responseSink.asFlux()
    }
}
