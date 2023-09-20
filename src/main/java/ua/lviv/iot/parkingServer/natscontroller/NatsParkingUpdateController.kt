package ua.lviv.iot.parkingServer.natscontroller

import com.example.ParkingOuterClass
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.converter.ParkingConverter
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.natscontroller.NatsController
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@Component
class NatsParkingUpdateController(
    private val converter: ParkingConverter,
    private val service: ParkingServiceInterface,
    override val connection: Connection,
) : NatsController<ParkingOuterClass.ParkingRequest, ParkingOuterClass.ParkingResponse> {
    override val subject: String = "parking.update"
    override val parser: Parser<ParkingOuterClass.ParkingRequest> = ParkingOuterClass.ParkingRequest.parser()

    override fun generateReplyForNatsRequest(request: ParkingOuterClass.ParkingRequest): ParkingOuterClass.ParkingResponse {
        val parking: Parking = converter.protoRequestToParking(request)
        parking.id = subject
        service.updateEntity(parking)
        return converter.parkingToProtoResponse(parking)
    }
}
