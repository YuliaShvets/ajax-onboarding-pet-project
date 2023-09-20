package ua.lviv.iot.parkingServer.natscontroller

import ua.lviv.iot.parkingServer.model.Parking
import com.example.ParkingOuterClass.ParkingRequest
import com.example.ParkingOuterClass.ParkingResponse
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.converter.ParkingConverter
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@Component
class NatsParkingCreateController(
    private val converter: ParkingConverter,
    private val service: ParkingServiceInterface,
    override val connection: Connection
) : NatsController<ParkingRequest, ParkingResponse> {
    override val subject: String = "parking.add"
    override val parser: Parser<ParkingRequest> =
        ParkingRequest.parser()

    override fun generateReplyForNatsRequest(
        request: ParkingRequest
    ): ParkingResponse {
        val parking: Parking = service.addEntity(converter.protoRequestToParking(request))
        return converter.parkingToProtoResponse(parking)
    }
}
