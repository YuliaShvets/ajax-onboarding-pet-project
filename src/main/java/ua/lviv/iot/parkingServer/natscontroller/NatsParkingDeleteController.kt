package ua.lviv.iot.parkingServer.natscontroller

import com.example.ParkingOuterClass.DeleteParkingRequest
import com.example.ParkingOuterClass.DeleteParkingResponse
import com.google.protobuf.Parser
import io.nats.client.Connection
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ua.lviv.iot.nats.NatsSubject
import ua.lviv.iot.parkingServer.service.interfaces.ParkingServiceInterface

@Component
class NatsParkingDeleteController(
    private val service: ParkingServiceInterface,
    override val connection: Connection,
) : NatsController<DeleteParkingRequest, DeleteParkingResponse> {

    override val subject: String = NatsSubject.PARKING_DELETE

    override val parser: Parser<DeleteParkingRequest> = DeleteParkingRequest.parser()

    override fun generateReplyForNatsRequest(request: DeleteParkingRequest): Mono<DeleteParkingResponse> {
        service.deleteEntity(request.parkingId).block()
        return Mono.just(DeleteParkingResponse.getDefaultInstance())
    }
}
