package ua.lviv.iot.parkingServer.natscontroller

import com.example.ParkingOuterClass
import io.nats.client.Connection
import java.time.Duration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ua.lviv.iot.nats.NatsSubject
import ua.lviv.iot.parkingServer.model.Parking
import ua.lviv.iot.parkingServer.repository.ParkingRepository

@SpringBootTest
class NatsParkingDeleteControllerTest {

    @Autowired
    lateinit var parkingRepository: ParkingRepository

    @Autowired
    lateinit var connection: Connection

    @Test
    fun generateReplyForNatsRequest() {
        val parking = Parking("Kyiv", "Forum", 123)
        parkingRepository.save(parking)
        val sizeBeforeDeletion = parkingRepository.findAll().size
        val request = ParkingOuterClass.DeleteParkingRequest.newBuilder()
            .setParkingId(parking.id)
            .build()

        connection.requestWithTimeout(
            NatsSubject.PARKING_DELETE,
            request.toByteArray(),
            Duration.ofSeconds(10)
        ).get().data

        val sizeAfterDeletion = parkingRepository.findAll().size
        assertThat(sizeBeforeDeletion).isGreaterThan(sizeAfterDeletion)
    }
}
