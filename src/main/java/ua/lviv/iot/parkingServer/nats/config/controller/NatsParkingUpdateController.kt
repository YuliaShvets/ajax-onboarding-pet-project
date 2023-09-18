package ua.lviv.iot.parkingServer.nats.config.controller

import com.example.ParkingOuterClass
import com.google.protobuf.Parser
import io.nats.client.Connection
import ua.lviv.iot.parkingServer.converter.ParkingConverter
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

class NatsParkingUpdateController(
    private val converter: ParkingConverter,
    private val service: ParkingServiceInterface,
    override val connection: Connection,
) : NatsController<ParkingOuterClass.ParkingRequest, ParkingOuterClass.ParkingResponse> {
    override val subject: String = "parking.update"
    override val parser: Parser<ParkingOuterClass.ParkingRequest> = ParkingOuterClass.ParkingRequest.parser()

    override fun generateReplyForNatsRequest(request: ParkingOuterClass.ParkingRequest): ParkingOuterClass.ParkingResponse {
        val parking: Parking = converter.protoRequestToParking(request)
        parking.id = subject.split(".").last()
        service.updateEntity(parking)
        return converter.parkingToProtoResponse(parking)
    }

}
