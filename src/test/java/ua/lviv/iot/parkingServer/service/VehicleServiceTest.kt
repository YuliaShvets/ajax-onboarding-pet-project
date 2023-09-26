package ua.lviv.iot.parkingServer.service

import java.time.Duration
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.model.enums.VehicleType
import ua.lviv.iot.parkingServer.repository.VehicleRepository


@SpringBootTest
class VehicleServiceTest {

    @Mock
    lateinit var vehicleRepository: VehicleRepository

    @InjectMocks
    lateinit var vehicleService: VehicleService

    @Test
    fun findAllEntities() {

        val vehicle1 = Vehicle("KA6906VN", VehicleType.CAR, Duration.ofHours(2), true)
        val vehicle2 = Vehicle("BC4556KM", VehicleType.BUS, Duration.ofHours(1), true)

        `when`(vehicleRepository.findAll()).thenReturn(Flux.just(vehicle1, vehicle2))

        val resultFlux = vehicleService.findAllEntities()

        StepVerifier.create(resultFlux)
            .expectNext(vehicle1)
            .expectNext(vehicle2)
            .expectComplete()
            .verify()
    }

    @Test
    fun findEntityById() {
        val vehicle = Vehicle("KA6906VN", VehicleType.CAR, Duration.ofHours(2), true)
        vehicle.id = "6512ea250ca2fc0f9ec2738b"
        `when`(vehicleRepository.findById(vehicle.id)).thenReturn(Mono.just(vehicle))

        val resultMono = vehicleService.findEntityById(vehicle.id)

        StepVerifier.create(resultMono)
            .expectNext(vehicle)
            .expectComplete()
            .verify()
    }

    @Test
    fun addEntity() {
        val vehicle = Vehicle("KA6906VN", VehicleType.CAR, Duration.ofHours(2), true)
        `when`(vehicleRepository.save(vehicle)).thenReturn(Mono.just(vehicle))

        val resultMono = vehicleService.addEntity(vehicle)

        StepVerifier.create(resultMono)
            .expectNext(vehicle)
            .expectComplete()
            .verify()
    }

    @Test
    fun updateEntity() {
        val vehicle = Vehicle("KA6906VN", VehicleType.CAR, Duration.ofHours(2), true)

        `when`(vehicleRepository.update(vehicle)).thenReturn(Mono.just(vehicle))

        val resultMono = vehicleService.updateEntity(vehicle)

        StepVerifier.create(resultMono)
            .expectNext(vehicle)
            .expectComplete()
            .verify()
    }

    @Test
    fun deleteEntity() {
        val vehicle = Vehicle("KA6906VN", VehicleType.CAR, Duration.ofHours(2), true)
        vehicle.id = "6512ea250ca2fc0f9ec2738b"

        `when`(vehicleRepository.deleteById(vehicle.id)).thenReturn(Mono.empty())

        val resultMono = vehicleService.deleteEntity(vehicle.id)

        StepVerifier.create(resultMono)
            .expectComplete()
            .verify()

    }

    @Test
    fun findVehicleByNumber() {
        val vehicle = Vehicle("KA6906VN", VehicleType.CAR, Duration.ofHours(2), true)
        `when`(vehicleRepository.findVehicleByNumber(vehicle.number)).thenReturn(Mono.just(vehicle))

        val resultMono = vehicleService.findVehicleByNumber(vehicle.number)

        StepVerifier.create(resultMono)
            .expectNext(vehicle)
            .expectComplete()
            .verify()
    }
}
