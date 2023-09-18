package ua.lviv.iot.parkingServer.nats.config.controller

import com.example.ParkingOuterClass
import com.example.ParkingOuterClass.GetByIdParkingRequest
import com.example.ParkingOuterClass.GetByIdParkingResponse
import com.google.protobuf.Parser
import io.nats.client.Connection
import ua.lviv.iot.parkingServer.converter.ParkingConverter
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

class NatsParkingGetByIdController(
    private val converter: ParkingConverter,
    private val service: ParkingServiceInterface,
    override val connection: Connection,
) : NatsController<GetByIdParkingRequest, GetByIdParkingResponse> {
    override val subject: String = "parking.get_by_id"
    override val parser: Parser<GetByIdParkingRequest> = GetByIdParkingRequest.parser()

    override fun generateReplyForNatsRequest(
        request:GetByIdParkingRequest
    ): GetByIdParkingResponse {
        val parkingById = service.findEntityById(request.parkingId)
        val protoParking = converter.parkingToProto(parkingById)

        return GetByIdParkingResponse
            .newBuilder()
            .setParking(protoParking)
            .build()
    }
}
