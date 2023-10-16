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
class NatsParkingGetByIdController(
    private val converter: ParkingConverter,
    private val service: ParkingServiceInPort,
    override val connection: Connection,
) : NatsController<ParkingOuterClass.GetByIdParkingRequest, ParkingOuterClass.GetByIdParkingResponse> {

    override val subject: String = NatsSubject.PARKING_GET_BY_ID

    override val parser: Parser<ParkingOuterClass.GetByIdParkingRequest> = ParkingOuterClass.GetByIdParkingRequest.parser()

    override fun generateReplyForNatsRequest(request: ParkingOuterClass.GetByIdParkingRequest): Mono<ParkingOuterClass.GetByIdParkingResponse> {
        val parkingId = request.parkingId

        return service.findEntityById(parkingId)
            .map { converter.parkingToProto(it) }
            .map {
                ParkingOuterClass.GetByIdParkingResponse.newBuilder()
                    .setParking(it)
                    .build()
            }
    }
}
