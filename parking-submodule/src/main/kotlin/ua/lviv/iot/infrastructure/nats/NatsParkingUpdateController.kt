package ua.lviv.iot.infrastructure.nats

import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ua.lviv.iot.ParkingOuterClass
import ua.lviv.iot.application.proto.converter.ParkingConverter
import ua.lviv.iot.application.service.ParkingServiceInPort
import ua.lviv.iot.config.nats.NatsController
import ua.lviv.iot.nats.NatsSubject

@Component
class NatsParkingUpdateController(
    private val converter: ParkingConverter,
    private val service: ParkingServiceInPort,
    override val connection: Connection,
) : NatsController<ParkingOuterClass.UpdateParkingRequest, ParkingOuterClass.UpdateParkingResponse> {

    override val subject: String = NatsSubject.PARKING_UPDATE

    override val parser: Parser<ParkingOuterClass.UpdateParkingRequest> =
        ParkingOuterClass.UpdateParkingRequest.parser()

    override fun generateReplyForNatsRequest(request: ParkingOuterClass.UpdateParkingRequest): Mono<ParkingOuterClass.UpdateParkingResponse> {
        return Mono.fromCallable {
            val parking =
                converter
                    .protoToParking(request.parking)
            parking.apply { id = request.parkingId }
        }.flatMap { updatedParking ->
            service.updateEntity(updatedParking)
                .map { createdParking ->
                    ParkingOuterClass.UpdateParkingResponse.newBuilder()
                        .setParking(converter.parkingToProto(createdParking))
                        .build()
                }
        }
    }
}
