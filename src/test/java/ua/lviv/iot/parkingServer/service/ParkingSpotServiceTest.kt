package ua.lviv.iot.parkingServer.service

import com.google.protobuf.GeneratedMessageV3
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import ua.lviv.iot.parkingServer.converter.ParkingSpotConverter
import ua.lviv.iot.parkingServer.model.ParkingSpot
import ua.lviv.iot.parkingServer.model.enums.ParkingSpotSize
import ua.lviv.iot.parkingServer.repository.ParkingSpotRepository

@SpringBootTest
class ParkingSpotServiceTest {

    @Mock
    lateinit var parkingSpotRepository: ParkingSpotRepository

    @InjectMocks
    lateinit var parkingSpotService: ParkingSpotService

    @Mock
    lateinit var parkingSpotKafkaProducer: ReactiveKafkaProducerTemplate<String, GeneratedMessageV3>

    @Mock
    lateinit var parkingSpotConverter: ParkingSpotConverter

    @Test
    fun findAllEntities() {

        val parkingSpot1 = ParkingSpot(true, ParkingSpotSize.MOTORBIKE)
        val parkingSpot2 = ParkingSpot(false, ParkingSpotSize.LARGE)

        `when`(parkingSpotRepository.findAll()).thenReturn(Flux.just(parkingSpot1, parkingSpot2))

        val resultFlux = parkingSpotService.findAllEntities()

        StepVerifier.create(resultFlux)
            .expectNext(parkingSpot1)
            .expectNext(parkingSpot2)
            .expectComplete()
            .verify()
    }

    @Test
    fun findEntityById() {
        val parkingSpot = ParkingSpot(true, ParkingSpotSize.MOTORBIKE)
        parkingSpot.id = "6512ea250ca2fc0f9ec2738b"
        `when`(parkingSpotRepository.findById(parkingSpot.id)).thenReturn(Mono.just(parkingSpot))

        val resultMono = parkingSpotService.findEntityById(parkingSpot.id)

        StepVerifier.create(resultMono)
            .expectNext(parkingSpot)
            .expectComplete()
            .verify()
    }

    @Test
    fun addEntity() {
        val parkingSpot = ParkingSpot(true, ParkingSpotSize.MOTORBIKE)

        `when`(parkingSpotRepository.save(parkingSpot)).thenReturn(Mono.just(parkingSpot))
        `when`(
            parkingSpotKafkaProducer.send(
                "ADDED_AVAILABLE_PARKING_SPOT",
                parkingSpotConverter.parkingSpotToProtoResponse(parkingSpot)
            )
        ).thenReturn(Mono.empty())
        val resultMono = parkingSpotService.addEntity(parkingSpot)

        StepVerifier.create(resultMono)
            .expectNext(parkingSpot)
            .expectComplete()
            .verify()
    }

    @Test
    fun updateEntity() {
        val parkingSpot = ParkingSpot(true, ParkingSpotSize.MOTORBIKE)

        `when`(parkingSpotRepository.update(parkingSpot)).thenReturn(Mono.just(parkingSpot))

        val resultMono = parkingSpotService.updateEntity(parkingSpot)

        StepVerifier.create(resultMono)
            .expectNext(parkingSpot)
            .expectComplete()
            .verify()
    }

    @Test
    fun deleteEntity() {
        val parkingSpot = ParkingSpot(true, ParkingSpotSize.MOTORBIKE)
        parkingSpot.id = "6512ea250ca2fc0f9ec2738b"

        `when`(parkingSpotRepository.deleteById(parkingSpot.id)).thenReturn(Mono.empty())

        val resultMono = parkingSpotService.deleteEntity(parkingSpot.id)

        StepVerifier.create(resultMono)
            .expectComplete()
            .verify()

    }

    @Test
    fun findParkingSpotByAvailability() {
        val parkingSpot = ParkingSpot(true, ParkingSpotSize.MOTORBIKE)
        `when`(parkingSpotRepository.findParkingSpotByAvailability(parkingSpot.isAvailable)).thenReturn(
            Flux.just(
                parkingSpot
            )
        )

        val resultFlux = parkingSpotService.findParkingSpotByAvailability(parkingSpot.isAvailable)

        StepVerifier.create(resultFlux)
            .expectNext(parkingSpot)
            .expectComplete()
            .verify()
    }
}
