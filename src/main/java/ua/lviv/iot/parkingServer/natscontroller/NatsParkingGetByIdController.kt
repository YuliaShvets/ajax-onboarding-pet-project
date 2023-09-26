package ua.lviv.iot.parkingServer.natscontroller

import com.example.ParkingOuterClass.GetByIdParkingRequest
import com.example.ParkingOuterClass.GetByIdParkingResponse
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ua.lviv.iot.nats.NatsSubject
import ua.lviv.iot.parkingServer.converter.ParkingConverter
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@Component
class NatsParkingGetByIdController(
    private val converter: ParkingConverter,
    private val service: ParkingServiceInterface,
    override val connection: Connection,
) : NatsController<GetByIdParkingRequest, GetByIdParkingResponse> {

    override val subject: String = NatsSubject.PARKING_GET_BY_ID

    override val parser: Parser<GetByIdParkingRequest> = GetByIdParkingRequest.parser()

    override fun generateReplyForNatsRequest(request: GetByIdParkingRequest): Mono<GetByIdParkingResponse> {
        val parkingId = request.parkingId

        return service.findEntityById(parkingId)
            .map { parking ->
                val protoParking = converter.parkingToProto(parking)
                GetByIdParkingResponse.newBuilder()
                    .setParking(protoParking)
                    .build()
            }
    }
}
