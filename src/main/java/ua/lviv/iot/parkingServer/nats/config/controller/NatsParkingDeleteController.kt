package ua.lviv.iot.parkingServer.nats.config.controller

import com.example.ParkingOuterClass.ParkingRequest
import com.example.ParkingOuterClass.ParkingResponse
import com.google.protobuf.Parser
import io.nats.client.Connection
import ua.lviv.iot.parkingServer.converter.ParkingConverter
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

class NatsParkingDeleteController(
    private val converter: ParkingConverter,
    private val service: ParkingServiceInterface,
    override val connection: Connection,
) : NatsController<ParkingRequest, ParkingResponse> {
    override val subject: String = "parking.delete"
    override val parser: Parser<ParkingRequest> = ParkingRequest.parser()

    override fun generateReplyForNatsRequest(request: ParkingRequest): ParkingResponse {
        val parking: Parking = converter.protoRequestToParking(request)
        parking.id = subject.split(".").last()
        service.deleteEntity(parking.id)
        return ParkingResponse.newBuilder().build()
    }

}
