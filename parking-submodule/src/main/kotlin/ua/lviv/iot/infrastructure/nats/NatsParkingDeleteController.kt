package ua.lviv.iot.infrastructure.nats

import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ua.lviv.iot.ParkingOuterClass
import ua.lviv.iot.application.service.ParkingServiceInPort
import ua.lviv.iot.config.nats.NatsController
import ua.lviv.iot.nats.NatsSubject

@Component
class NatsParkingDeleteController(
    private val service: ParkingServiceInPort,
    override val connection: Connection,
) : NatsController<ParkingOuterClass.DeleteParkingRequest, ParkingOuterClass.DeleteParkingResponse> {

    override val subject: String = NatsSubject.PARKING_DELETE

    override val parser: Parser<ParkingOuterClass.DeleteParkingRequest> = ParkingOuterClass.DeleteParkingRequest.parser()

    override fun generateReplyForNatsRequest(request: ParkingOuterClass.DeleteParkingRequest): Mono<ParkingOuterClass.DeleteParkingResponse> {
        return service.deleteEntity(request.parkingId)
            .thenReturn(ParkingOuterClass.DeleteParkingResponse.getDefaultInstance())
    }
}
