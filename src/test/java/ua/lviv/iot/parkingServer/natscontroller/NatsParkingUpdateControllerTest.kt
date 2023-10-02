package ua.lviv.iot.parkingServer.natscontroller

import io.nats.client.Connection
import java.time.Duration
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ua.lviv.iot.ParkingOuterClass
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
        val parking = Parking("Kyiv", "lol", 125)
        parkingRepository.save(parking).block()
        val request = ParkingOuterClass.UpdateParkingRequest.newBuilder()
            .setParkingId(parking.id)
            .setParking(parkingConverter.parkingToProto(parking))
            .build()

        val response = controller.generateReplyForNatsRequest(request).block()

        val reply = connection.requestWithTimeout(
            NatsSubject.PARKING_UPDATE,
            request.toByteArray(),
            Duration.ofSeconds(10)
        ).get().data

        val receivedResponse = ParkingOuterClass.UpdateParkingResponse.parseFrom(reply)
        Assertions.assertThat(response).isEqualTo(receivedResponse)
        parkingRepository.deleteById(parking.id).block()
    }
}
