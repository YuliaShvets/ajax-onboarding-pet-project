package ua.lviv.iot.parkingServer.natscontroller

import com.example.ParkingOuterClass
import io.nats.client.Connection
import java.time.Duration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.test.context.SpringBootTest
import ua.lviv.iot.parkingServer.converter.ParkingConverter
import ua.lviv.iot.parkingServer.model.Parking

@SpringBootTest
class NatsParkingCreateControllerTest {

    @Autowired
    lateinit var connection: Connection

    @Autowired
    lateinit var parkingConverter: ParkingConverter

    @Test
    fun generateReplyForNatsRequest() {
        val parking = Parking("Kyiv", "Forum", 123)
        val expected = ParkingOuterClass.ParkingRequest.newBuilder()
            .setParking(parkingConverter.parkingToProto(parking))
            .build()

        val actual = ParkingOuterClass.ParkingResponse.parseFrom(
            connection
                .requestWithTimeout(
                    "parking.add",
                    expected.toByteArray(),
                    Duration.ofSeconds(10)
                )
                .get()
                .data
        )
        assertThat(actual.parking).isEqualTo(expected.parking)
    }
}
