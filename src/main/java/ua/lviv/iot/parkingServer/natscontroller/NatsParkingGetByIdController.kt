package ua.lviv.iot.parkingServer.natscontroller

import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ua.lviv.iot.ParkingOuterClass
import ua.lviv.iot.ParkingOuterClass.GetByIdParkingResponse
import ua.lviv.iot.nats.NatsSubject
import ua.lviv.iot.parkingServer.converter.ParkingConverter
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@Component
class NatsParkingGetByIdController(
    private val converter: ParkingConverter,
    private val service: ParkingServiceInterface,
    override val connection: Connection,
) : NatsController<ParkingOuterClass.GetByIdParkingRequest, GetByIdParkingResponse> {

    override val subject: String = NatsSubject.PARKING_GET_BY_ID

    override val parser: Parser<ParkingOuterClass.GetByIdParkingRequest> = ParkingOuterClass.GetByIdParkingRequest.parser()

    override fun generateReplyForNatsRequest(request: ParkingOuterClass.GetByIdParkingRequest): Mono<GetByIdParkingResponse> {
        val parkingId = request.parkingId

        return service.findEntityById(parkingId)
            .map { converter.parkingToProto(it) }
            .map {
                GetByIdParkingResponse.newBuilder()
                    .setParking(it)
                    .build()
            }
    }
}
