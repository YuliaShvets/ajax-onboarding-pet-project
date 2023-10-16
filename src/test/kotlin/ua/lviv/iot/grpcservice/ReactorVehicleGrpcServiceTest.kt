package ua.lviv.iot.grpcservice

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
import ua.lviv.iot.VehicleOuterClass.UpdateVehicleRequest
import ua.lviv.iot.VehicleOuterClass.CreateVehicleRequest
import ua.lviv.iot.VehicleOuterClass.CreateVehicleResponse
import ua.lviv.iot.application.proto.converter.VehicleConverter
import ua.lviv.iot.application.repository.VehicleRepositoryOutPort
import ua.lviv.iot.domain.Vehicle
import ua.lviv.iot.domain.enum.VehicleType

@SpringBootTest
class ReactorVehicleGrpcServiceTest {

    @Autowired
    private lateinit var vehicleConverter: VehicleConverter

    @Autowired
    private lateinit var vehicleRepository: VehicleRepositoryOutPort

    private lateinit var stub: ReactorVehicleGrpcServiceStub

    @Autowired
    private lateinit var channel: ManagedChannel

    private lateinit var firstTestVehicle: Vehicle

    private lateinit var secondTestVehicle: Vehicle


    @BeforeEach
    fun setUp() {
        firstTestVehicle = Vehicle(
            id = "6512ea250ca2fc0f9ec2738b",
            number = "KA6706VN",
            typeOfVehicle = VehicleType.BUS,
            durationOfUseOfParkingSpot = Duration.ofSeconds(3600),
            isTicketReceived = true
        )
        secondTestVehicle = Vehicle(
            id = "6512ea250ca2fc0f9ec2738c",
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
        val expected = CreateVehicleResponse.newBuilder()
            .setVehicle(vehicleConverter.vehicleToProto(firstTestVehicle))
            .build()

        val request = Mono.just(
           CreateVehicleRequest.newBuilder()
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
    fun updateVehicle() {
        val addedVehicle = vehicleRepository.save(secondTestVehicle).block()!!
        firstTestVehicle.id = addedVehicle.id

        val vehicle = vehicleConverter
            .vehicleToProto(firstTestVehicle)

        val expected =
            CreateVehicleResponse
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
        addedVehicle.id?.let { vehicleRepository.deleteById(it).block() }
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
