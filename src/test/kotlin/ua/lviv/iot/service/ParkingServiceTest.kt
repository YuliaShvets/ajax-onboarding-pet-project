package ua.lviv.iot.service

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import ua.lviv.iot.application.service.ParkingService
import ua.lviv.iot.domain.Parking
import ua.lviv.iot.infrastructure.database.repository.ParkingRepository

@SpringBootTest
class ParkingServiceTest {

    @Mock
    lateinit var parkingRepository: ParkingRepository

    @InjectMocks
    lateinit var parkingService: ParkingService

    @Test
    fun findAllEntities() {

        val parking1 = Parking("6512ea250ca2fc0f9ec2738b", "Kyiv", "Forum", 123)
        val parking2 = Parking("6512ea250ca2fc0f9ec2738c", "Lviv", "Forum", 123)

        `when`(parkingRepository.findAll()).thenReturn(Flux.just(parking1, parking2))

        val resultFlux = parkingService.findAllEntities()

        StepVerifier.create(resultFlux)
            .expectNext(parking1)
            .expectNext(parking2)
            .expectComplete()
            .verify()
    }

    @Test
    fun findEntityById() {
        val parking = Parking("6512ea250ca2fc0f9ec2738b", "Lviv", "Forum", 123)
        `when`(parking.id?.let { parkingRepository.findById(it) }).thenReturn(Mono.just(parking))

        val resultMono = parking.id?.let { parkingService.findEntityById(it) }

        if (resultMono != null) {
            StepVerifier.create(resultMono)
                .expectNext(parking)
                .expectComplete()
                .verify()
        }
    }

    @Test
    fun addEntity() {
        val parking = Parking("6512ea250ca2fc0f9ec2738b", "Lviv", "Forum", 123)

        `when`(parkingRepository.save(parking)).thenReturn(Mono.just(parking))

        val resultMono = parkingService.addEntity(parking)

        StepVerifier.create(resultMono)
            .expectNext(parking)
            .expectComplete()
            .verify()
    }

    @Test
    fun updateEntity() {
        val parking = Parking("6512ea250ca2fc0f9ec2738b", "Lviv", "Forum", 123)

        `when`(parkingRepository.update(parking)).thenReturn(Mono.just(parking))

        val resultMono = parkingService.updateEntity(parking)

        StepVerifier.create(resultMono)
            .expectNext(parking)
            .expectComplete()
            .verify()
    }

    @Test
    fun deleteEntity() {
        val parking = Parking("6512ea250ca2fc0f9ec2738b", "Lviv", "Forum", 123)
        parking.id = "6512ea250ca2fc0f9ec2738b"

        `when`(parkingRepository.deleteById(parking.id!!)).thenReturn(Mono.empty())

        val resultMono = parkingService.deleteEntity(parking.id!!)

        StepVerifier.create(resultMono)
            .expectComplete()
            .verify()

    }

    @Test
    fun findParkingByLocation() {
        val parking = Parking("6512ea250ca2fc0f9ec2738b", "Lviv", "Forum", 123)
        `when`(parkingRepository.findParkingByLocation(parking.location)).thenReturn(Flux.just(parking))

        val resultFlux = parkingService.findParkingByLocation(parking.location)

        StepVerifier.create(resultFlux)
            .expectNext(parking)
            .expectComplete()
            .verify()

    }

    @Test
    fun findParkingByCountOfParkingSpotsGreaterThan() {
        val parking = Parking("6512ea250ca2fc0f9ec2738b", "Lviv", "Forum", 123)
        val count = 150
        `when`(parkingRepository.findParkingByCountOfParkingSpotsGreaterThan(count)).thenReturn(Flux.just(parking))

        val resultFlux = parkingService.findParkingByCountOfParkingSpotsGreaterThan(count)

        StepVerifier.create(resultFlux)
            .expectNext(parking)
            .expectComplete()
            .verify()

    }

    @Test
    fun findAllByTradeNetwork() {
        val parking = Parking("6512ea250ca2fc0f9ec2738b", "Lviv", "Forum", 123)
        `when`(parkingRepository.findAllByTradeNetwork(parking.tradeNetwork)).thenReturn(Flux.just(parking))

        val resultFlux = parkingService.findAllByTradeNetwork(parking.tradeNetwork)

        StepVerifier.create(resultFlux)
            .expectNext(parking)
            .expectComplete()
            .verify()
    }
}
