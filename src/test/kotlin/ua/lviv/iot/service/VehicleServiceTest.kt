package ua.lviv.iot.service

import java.time.Duration
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import ua.lviv.iot.application.repository.VehicleRepositoryOutPort
import ua.lviv.iot.application.service.VehicleService
import ua.lviv.iot.application.service.VehicleServiceInPort
import ua.lviv.iot.domain.Vehicle
import ua.lviv.iot.domain.enum.VehicleType


@SpringBootTest
class VehicleServiceTest {

    @Mock
    lateinit var vehicleRepository: VehicleRepositoryOutPort

    @InjectMocks
    lateinit var vehicleService: VehicleService

    @Test
    fun findAllEntities() {

        val vehicle1 = Vehicle("6512ea250ca2fc0f9ec2738b", "KA6906VN", VehicleType.CAR, Duration.ofHours(2), true)
        val vehicle2 = Vehicle("6512ea250ca2fc0f9ec2738c", "BC4556KM", VehicleType.BUS, Duration.ofHours(1), true)

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
        val vehicle = Vehicle("6512ea250ca2fc0f9ec2738b", "KA6906VN", VehicleType.CAR, Duration.ofHours(2), true)

        `when`(vehicle.id?.let { vehicleRepository.findById(it) }).thenReturn(Mono.just(vehicle))

        val resultMono = vehicle.id?.let { vehicleService.findEntityById(it) }

        if (resultMono != null) {
            StepVerifier.create(resultMono)
                .expectNext(vehicle)
                .expectComplete()
                .verify()
        }
    }

    @Test
    fun addEntity() {
        val vehicle = Vehicle("6512ea250ca2fc0f9ec2738b", "KA6906VN", VehicleType.CAR, Duration.ofHours(2), true)
        `when`(vehicleRepository.save(vehicle)).thenReturn(Mono.just(vehicle))

        val resultMono = vehicleService.addEntity(vehicle)

        StepVerifier.create(resultMono)
            .expectNext(vehicle)
            .expectComplete()
            .verify()
    }

    @Test
    fun updateEntity() {
        val vehicle = Vehicle("6512ea250ca2fc0f9ec2738b", "KA6906VN", VehicleType.CAR, Duration.ofHours(2), true)

        `when`(vehicleRepository.update(vehicle)).thenReturn(Mono.just(vehicle))

        val resultMono = vehicleService.updateEntity(vehicle)

        StepVerifier.create(resultMono)
            .expectNext(vehicle)
            .expectComplete()
            .verify()
    }

    @Test
    fun deleteEntity() {
        val vehicle = Vehicle("6512ea250ca2fc0f9ec2738b", "KA6906VN", VehicleType.CAR, Duration.ofHours(2), true)
        vehicle.id = "6512ea250ca2fc0f9ec2738b"

        `when`(vehicleRepository.deleteById(vehicle.id!!)).thenReturn(Mono.empty())

        val resultMono = vehicleService.deleteEntity(vehicle.id!!)

        StepVerifier.create(resultMono)
            .expectComplete()
            .verify()

    }

    @Test
    fun findVehicleByNumber() {
        val vehicle = Vehicle("6512ea250ca2fc0f9ec2738b", "KA6906VN", VehicleType.CAR, Duration.ofHours(2), true)
        `when`(vehicleRepository.findVehicleByNumber(vehicle.number)).thenReturn(Mono.just(vehicle))

        val resultMono = vehicleService.findVehicleByNumber(vehicle.number)

        StepVerifier.create(resultMono)
            .expectNext(vehicle)
            .expectComplete()
            .verify()
    }
}
