package ua.lviv.iot.parkingServer.natscontroller

import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ua.lviv.iot.ParkingOuterClass.CreateParkingRequest
import ua.lviv.iot.ParkingOuterClass.CreateParkingResponse
import ua.lviv.iot.nats.NatsSubject
import ua.lviv.iot.parkingServer.converter.ParkingConverter
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@Component
class NatsParkingCreateController(
    private val converter: ParkingConverter,
    private val service: ParkingServiceInterface,
    override val connection: Connection
) : NatsController<CreateParkingRequest, CreateParkingResponse> {

    override val subject: String = NatsSubject.PARKING_ADD

    override val parser: Parser<CreateParkingRequest> =
        CreateParkingRequest.parser()

    override fun generateReplyForNatsRequest(request: CreateParkingRequest): Mono<CreateParkingResponse> {
        return service.addEntity(converter.protoRequestToParking(request))
            .map { converter.parkingToProtoResponse(it) }
    }
}
