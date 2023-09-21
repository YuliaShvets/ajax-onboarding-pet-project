package ua.lviv.iot.parkingServer.natscontroller

import com.example.ParkingOuterClass
import com.example.ParkingOuterClass.UpdateParkingRequest
import io.nats.client.Connection
import java.time.Duration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ua.lviv.iot.nats.NatsSubject
import ua.lviv.iot.parkingServer.converter.ParkingConverter
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.repository.ParkingRepository

@SpringBootTest
class NatsParkingUpdateControllerTest {

    @Autowired
    lateinit var connection: Connection

    @Autowired
    lateinit var parkingConverter: ParkingConverter

    @Autowired
    lateinit var controller: NatsParkingUpdateController

    @Autowired
    lateinit var parkingRepository: ParkingRepository


    @Test
    fun generateReplyForNatsRequest() {
        val parking = Parking("Kyiv", "lol", 123)
        parkingRepository.save(parking)
        val request = UpdateParkingRequest.newBuilder()
            .setParkingId(parking.id)
            .setParking(parkingConverter.parkingToProto(parking))
            .build()

        val response = controller.generateReplyForNatsRequest(request)

        val reply = connection.requestWithTimeout(
            NatsSubject.PARKING_UPDATE,
            request.toByteArray(),
            Duration.ofSeconds(10)
        ).get().data

        val receivedResponse = ParkingOuterClass.UpdateParkingResponse.parseFrom(reply)
        assertEquals(receivedResponse, response)
        println(parking)
        parkingRepository.delete(parking)
    }
}
