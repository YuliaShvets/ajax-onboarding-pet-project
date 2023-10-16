package ua.lviv.iot.natscontroller

import io.nats.client.Connection
import java.time.Duration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ua.lviv.iot.ParkingOuterClass.CreateParkingRequest
import ua.lviv.iot.ParkingOuterClass.CreateParkingResponse
import ua.lviv.iot.application.proto.converter.ParkingConverter
import ua.lviv.iot.domain.Parking
import ua.lviv.iot.nats.NatsSubject


@SpringBootTest
class NatsParkingCreateControllerTest {

    @Autowired
    lateinit var connection: Connection

    @Autowired
    lateinit var parkingConverter: ParkingConverter

    @Test
    fun generateReplyForNatsRequest() {
        val parking = Parking(id = "6512ea250ca2fc0f9ec2738b", "Kyiv", "Forum", 123)
        val expected = CreateParkingRequest.newBuilder()
            .setParking(parkingConverter.parkingToProto(parking))
            .build()

        val actual = CreateParkingResponse.parseFrom(
            connection
                .requestWithTimeout(
                    NatsSubject.PARKING_ADD,
                    expected.toByteArray(),
                    Duration.ofSeconds(10)
                )
                .get()
                .data
        )
        assertThat(actual.parking).isEqualTo(expected.parking)
    }
}
