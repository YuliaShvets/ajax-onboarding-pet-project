package ua.lviv.iot.service

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
import ua.lviv.iot.infrastructure.converter.proto.ParkingSpotConverter
import ua.lviv.iot.application.repository.ParkingSpotRepositoryOutPort
import ua.lviv.iot.application.service.ParkingSpotService
import ua.lviv.iot.domain.ParkingSpot
import ua.lviv.iot.domain.enum.ParkingSpotSize

@SpringBootTest
class ParkingSpotServiceTest {

    @Mock
    lateinit var parkingSpotRepository: ParkingSpotRepositoryOutPort

    @InjectMocks
    lateinit var parkingSpotService: ParkingSpotService

    @Mock
    lateinit var parkingSpotKafkaProducer: ReactiveKafkaProducerTemplate<String, GeneratedMessageV3>

    @Mock
    lateinit var parkingSpotConverter: ParkingSpotConverter

    @Test
    fun findAllEntities() {

        val parkingSpot1 = ParkingSpot("6512ea250ca2fc0f9ec2738b",true, ParkingSpotSize.MOTORBIKE)
        val parkingSpot2 = ParkingSpot("6512ea250ca2fc0f9ec2738c", false, ParkingSpotSize.LARGE)

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
        val parkingSpot = ParkingSpot("6512ea250ca2fc0f9ec2738b", true, ParkingSpotSize.MOTORBIKE)
        `when`(parkingSpot.id?.let { parkingSpotRepository.findById(it) }).thenReturn(Mono.just(parkingSpot))

        val resultMono = parkingSpot.id?.let { parkingSpotService.findEntityById(it) }

        if (resultMono != null) {
            StepVerifier.create(resultMono)
                .expectNext(parkingSpot)
                .expectComplete()
                .verify()
        }
    }

    @Test
    fun addEntity() {
        val parkingSpot = ParkingSpot("6512ea250ca2fc0f9ec2738b", true, ParkingSpotSize.MOTORBIKE)

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
        val parkingSpot = ParkingSpot("6512ea250ca2fc0f9ec2738b", true, ParkingSpotSize.MOTORBIKE)

        `when`(parkingSpotRepository.update(parkingSpot)).thenReturn(Mono.just(parkingSpot))

        val resultMono = parkingSpotService.updateEntity(parkingSpot)

        StepVerifier.create(resultMono)
            .expectNext(parkingSpot)
            .expectComplete()
            .verify()
    }

    @Test
    fun deleteEntity() {
        val parkingSpot = ParkingSpot("6512ea250ca2fc0f9ec2738b", true, ParkingSpotSize.MOTORBIKE)

        `when`(parkingSpot.id?.let { parkingSpotRepository.deleteById(it) }).thenReturn(Mono.empty())

        val resultMono = parkingSpot.id?.let { parkingSpotService.deleteEntity(it) }

        if (resultMono != null) {
            StepVerifier.create(resultMono)
                .expectComplete()
                .verify()
        }

    }

    @Test
    fun findParkingSpotByAvailability() {
        val parkingSpot = ParkingSpot("6512ea250ca2fc0f9ec2738b", true, ParkingSpotSize.MOTORBIKE)
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
