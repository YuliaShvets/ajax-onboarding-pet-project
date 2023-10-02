package ua.lviv.iot.parkingServer.natscontroller

import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ua.lviv.iot.ParkingOuterClass
import ua.lviv.iot.ParkingOuterClass.ParkingRequest
import ua.lviv.iot.nats.NatsSubject
import ua.lviv.iot.parkingServer.converter.ParkingConverter
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@Component
class NatsParkingCreateController(
    private val converter: ParkingConverter,
    private val service: ParkingServiceInterface,
    override val connection: Connection
) : NatsController<ParkingRequest, ParkingOuterClass.ParkingResponse> {

    override val subject: String = NatsSubject.PARKING_ADD

    override val parser: Parser<ParkingRequest> =
        ParkingRequest.parser()

    override fun generateReplyForNatsRequest(request: ParkingRequest): Mono<ParkingOuterClass.ParkingResponse> {
        return service.addEntity(converter.protoRequestToParking(request))
            .map { converter.parkingToProtoResponse(it) }
    }
}
