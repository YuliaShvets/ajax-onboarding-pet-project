package ua.lviv.iot.natscontroller

import io.nats.client.Connection
import java.time.Duration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ua.lviv.iot.ParkingOuterClass
import ua.lviv.iot.application.repository.ParkingRepositoryOutPort
import ua.lviv.iot.domain.Parking
import ua.lviv.iot.nats.NatsSubject


@SpringBootTest
class NatsParkingDeleteControllerTest {

    @Autowired
    lateinit var parkingRepository: ParkingRepositoryOutPort

    @Autowired
    lateinit var connection: Connection

    @Test
    fun generateReplyForNatsRequest() {
        val parking = Parking("6512ea250ca2fc0f9ec2738b", "Kyiv", "Forum", 123)
        parkingRepository.save(parking).block()
        val sizeBeforeDeletion = parkingRepository.findAll().collectList().block()!!.size
        val parkingFromDataBase = parkingRepository.findAll().collectList().block()!!.first()
        val request = ParkingOuterClass.DeleteParkingRequest.newBuilder()
            .setParkingId(parkingFromDataBase.id)
            .build()

        connection.requestWithTimeout(
            NatsSubject.PARKING_DELETE,
            request.toByteArray(),
            Duration.ofSeconds(10)
        ).get().data

        val sizeAfterDeletion = parkingRepository.findAll().collectList().block()!!.size
        assertThat(sizeBeforeDeletion).isGreaterThan(sizeAfterDeletion)
    }

}
