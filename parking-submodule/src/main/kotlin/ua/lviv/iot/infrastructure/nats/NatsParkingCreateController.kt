package ua.lviv.iot.infrastructure.nats

import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ua.lviv.iot.ParkingOuterClass
import ua.lviv.iot.infrastructure.converter.proto.ParkingConverter
import ua.lviv.iot.application.service.ParkingInPort
import ua.lviv.iot.config.nats.NatsController
import ua.lviv.iot.nats.NatsSubject

@Component
class NatsParkingCreateController(
    private val converter: ParkingConverter,
    private val service: ParkingInPort,
    override val connection: Connection
) : NatsController<ParkingOuterClass.CreateParkingRequest, ParkingOuterClass.CreateParkingResponse> {

    override val subject: String = NatsSubject.PARKING_ADD

    override val parser: Parser<ParkingOuterClass.CreateParkingRequest> =
        ParkingOuterClass.CreateParkingRequest.parser()

    override fun generateReplyForNatsRequest(request: ParkingOuterClass.CreateParkingRequest): Mono<ParkingOuterClass.CreateParkingResponse> {
        return service.addEntity(converter.protoRequestToParking(request))
            .map { converter.parkingToProtoResponse(it) }
    }
}
