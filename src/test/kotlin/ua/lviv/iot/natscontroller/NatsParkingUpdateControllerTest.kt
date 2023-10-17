package ua.lviv.iot.natscontroller

import io.nats.client.Connection
import java.time.Duration
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ua.lviv.iot.ParkingOuterClass
import ua.lviv.iot.ParkingOuterClass.CreateParkingResponse
import ua.lviv.iot.infrastructure.converter.proto.ParkingConverter
import ua.lviv.iot.application.repository.ParkingRepositoryOutPort
import ua.lviv.iot.domain.Parking
import ua.lviv.iot.nats.NatsSubject

@SpringBootTest
class NatsParkingUpdateControllerTest {

    @Autowired
    lateinit var connection: Connection

    @Autowired
    lateinit var parkingConverter: ParkingConverter

    @Autowired
    lateinit var parkingRepository: ParkingRepositoryOutPort


    @Test
    fun generateReplyForNatsRequest() {
        val parkingFromDataBase = parkingRepository.findAll().collectList().block()!!.first()
        val parking = Parking("6512ea250ca2fc0f9ec2738b", "Kyiv", "Forum", 123)
        val expected = ParkingOuterClass.UpdateParkingRequest.newBuilder()
            .setParkingId(parkingFromDataBase.id)
            .setParking(parkingConverter.parkingToProto(parking))
            .build()

       val future =  connection.requestWithTimeout(
            NatsSubject.PARKING_UPDATE,
            expected.toByteArray(),
            Duration.ofSeconds(10)
        ).get().data

        val actual = CreateParkingResponse.parseFrom(future)
        Assertions.assertThat(actual.parking).isEqualTo(expected.parking)
    }
}
