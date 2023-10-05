package ua.lviv.iot.parkingServer.grpcservice

import io.grpc.ManagedChannel
import java.time.Duration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import ua.lviv.iot.ReactorVehicleGrpcServiceGrpc
import ua.lviv.iot.ReactorVehicleGrpcServiceGrpc.ReactorVehicleGrpcServiceStub
import ua.lviv.iot.VehicleOuterClass
import ua.lviv.iot.VehicleOuterClass.DeleteVehicleRequest
import ua.lviv.iot.VehicleOuterClass.GetByIdVehicleRequest
import ua.lviv.iot.VehicleOuterClass.UpdateVehicleRequest
import ua.lviv.iot.VehicleOuterClass.VehicleRequest
import ua.lviv.iot.VehicleOuterClass.VehicleResponse
import ua.lviv.iot.parkingServer.converter.VehicleConverter
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.model.enums.VehicleType
import ua.lviv.iot.parkingServer.repository.VehicleRepository

@SpringBootTest
class ReactorVehicleGrpcServiceTest {

    @Autowired
    private lateinit var vehicleConverter: VehicleConverter

    @Autowired
    private lateinit var vehicleRepository: VehicleRepository

    private lateinit var stub: ReactorVehicleGrpcServiceStub

    @Autowired
    private lateinit var channel: ManagedChannel

    private lateinit var firstTestVehicle: Vehicle

    private lateinit var secondTestVehicle: Vehicle


    @BeforeEach
    fun setUp() {
        firstTestVehicle = Vehicle(
            number = "KA6706VN",
            typeOfVehicle = VehicleType.BUS,
            durationOfUseOfParkingSpot = Duration.ofSeconds(3600),
            isTicketReceived = true
        )
        secondTestVehicle = Vehicle(
            number = "KA6706VW",
            typeOfVehicle = VehicleType.BUS,
            durationOfUseOfParkingSpot = Duration.ofSeconds(3600),
            isTicketReceived = true
        )
    }

    @BeforeEach
    fun start() {
        stub = ReactorVehicleGrpcServiceGrpc.newReactorStub(channel)
    }

    @Test
    fun createVehicle() {
        val expected = VehicleResponse.newBuilder()
            .setVehicle(vehicleConverter.vehicleToProto(firstTestVehicle))
            .build()

        val request = Mono.just(
            VehicleRequest.newBuilder()
                .setVehicle(vehicleConverter.vehicleToProto(firstTestVehicle))
                .build()
        )
        val actual = stub.createVehicle(request)

        StepVerifier.create(actual)
            .assertNext {
                assertThat(expected.vehicle).isEqualTo(it.vehicle)
            }
            .verifyComplete()
    }


    @Test
    fun getVehicleById() {
        vehicleRepository.save(firstTestVehicle).block()
        val expected = VehicleResponse.newBuilder()
            .setVehicle(vehicleConverter.vehicleToProto(firstTestVehicle))
            .build()

        val request = Mono.just(
            GetByIdVehicleRequest.newBuilder()
                .setVehicleId(firstTestVehicle.id)
                .build()
        )

        val actual = stub.getVehicleById(request)

        StepVerifier.create(actual)
            .assertNext {
                assertThat(expected.vehicle.number).isEqualTo(it.vehicle.number)
            }
            .verifyComplete()
        vehicleRepository.deleteById(firstTestVehicle.id).block()
    }

    @Test
    fun updateVehicle() {
        val addedVehicle = vehicleRepository.save(secondTestVehicle).block()!!
        firstTestVehicle.id = addedVehicle.id

        val vehicle = vehicleConverter
            .vehicleToProto(firstTestVehicle)

        val expected =
            VehicleResponse
                .newBuilder()
                .setVehicle(vehicle)
                .build()

        val request =
            Mono.just(
                UpdateVehicleRequest.newBuilder()
                    .setVehicle(vehicle)
                    .setVehicleId(addedVehicle.id)
                    .build()
            )
        val actual =
            stub.updateVehicle(request)

        StepVerifier.create(actual)
            .assertNext {
                assertThat(expected.vehicle).isEqualTo(it.vehicle)
            }
            .verifyComplete()
        vehicleRepository.deleteById(addedVehicle.id).block()
    }

    @Test
    fun deleteVehicle() {
        vehicleRepository.save(secondTestVehicle).block()

        val request = Mono.just(
            DeleteVehicleRequest.newBuilder()
                .setVehicleId(secondTestVehicle.id)
                .build()
        )

        val actual = stub.deleteVehicle(request)

        StepVerifier.create(actual)
            .expectNext(VehicleOuterClass.DeleteVehicleResponse.getDefaultInstance())
            .verifyComplete()
    }
}
