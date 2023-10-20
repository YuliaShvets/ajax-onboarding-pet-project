package ua.lviv.iot.infrastructure.grpc

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import ua.lviv.iot.ParkingSpotOuterClass.CreateParkingSpotRequest
import ua.lviv.iot.ParkingSpotOuterClass.CreateParkingSpotResponse
import ua.lviv.iot.ReactorParkingSpotServiceGrpc
import ua.lviv.iot.infrastructure.nats.MessageBusListener

@Component
class GrpcService(
    val messageBusListener: MessageBusListener
) : ReactorParkingSpotServiceGrpc.ParkingSpotServiceImplBase() {

    @PostConstruct
    fun listenToEvents() {
        messageBusListener.listenToEvents()
    }

    override fun createParkingSpot(
        request: Flux<CreateParkingSpotRequest>
    ): Flux<CreateParkingSpotResponse> {
        return messageBusListener.responseSink.asFlux()
    }
}
