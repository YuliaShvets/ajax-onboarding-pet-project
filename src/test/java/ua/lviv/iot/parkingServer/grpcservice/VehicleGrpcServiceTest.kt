package ua.lviv.iot.parkingServer.grpcservice

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.time.Duration
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import ua.lviv.iot.VehicleGrpcServiceGrpc
import ua.lviv.iot.VehicleOuterClass
import ua.lviv.iot.parkingServer.converter.VehicleConverter
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.model.enums.VehicleType
import ua.lviv.iot.parkingServer.repository.VehicleRepository

@SpringBootTest
class VehicleGrpcServiceTest(
    @Value("\${grpc.server.port}")
    var grpcPort: Int
) {

    @Autowired
    private lateinit var vehicleConverter: VehicleConverter

    @Autowired
    private lateinit var vehicleRepository: VehicleRepository

    private lateinit var stub: VehicleGrpcServiceGrpc.VehicleGrpcServiceBlockingStub

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
        channel = ManagedChannelBuilder
            .forTarget("localhost:$grpcPort")
            .usePlaintext()
            .build()
        stub = VehicleGrpcServiceGrpc.newBlockingStub(channel)
    }

    @Test
    fun createVehicle() {
        val vehicleRequest = VehicleOuterClass.VehicleRequest.newBuilder()
            .setVehicle(vehicleConverter.vehicleToProto(firstTestVehicle))
            .build()

        val response = stub.createVehicle(vehicleRequest)

        Assertions.assertThat(response.vehicle.number).isEqualTo(firstTestVehicle.number)
        Assertions.assertThat(response.vehicle.typeOfVehicle.name).isEqualTo(firstTestVehicle.typeOfVehicle.name)
        Assertions.assertThat(response.vehicle.durationOfUseOfParkingSpot.seconds)
            .isEqualTo(firstTestVehicle.durationOfUseOfParkingSpot.seconds)
        Assertions.assertThat(response.vehicle.isTicketReceived).isEqualTo(firstTestVehicle.isTicketReceived)
    }

    @Test
    fun getVehicleById() {
        vehicleRepository.save(firstTestVehicle).block()
        val request = VehicleOuterClass.GetByIdVehicleRequest.newBuilder()
            .setVehicleId(firstTestVehicle.id)
            .build()
        val response = stub.getVehicleById(request)
        Assertions.assertThat(response.vehicle.number).isEqualTo("KA6706VN")
        vehicleRepository.deleteById(firstTestVehicle.id).block()
    }

    @Test
    fun updateVehicle() {
        vehicleRepository.save(secondTestVehicle).block()
        val request = VehicleOuterClass.UpdateVehicleRequest.newBuilder()
            .setVehicleId(secondTestVehicle.id)
            .setVehicle(vehicleConverter.vehicleToProto(secondTestVehicle))
            .build()

        val response = stub.updateVehicle(request)
        Assertions.assertThat(response.vehicle.number).isEqualTo("KA6706VW")
        vehicleRepository.deleteById(secondTestVehicle.id).block()
    }

    @Test
    fun deleteVehicle() {
        vehicleRepository.save(secondTestVehicle).block()
        val sizeBeforeDeletion = vehicleRepository.findAll().collectList().block()!!.size
        val request = VehicleOuterClass.DeleteVehicleRequest.newBuilder()
            .setVehicleId(secondTestVehicle.id)
            .build()

        stub.deleteVehicle(request)
        val sizeAfterDeletion = vehicleRepository.findAll().collectList().block()!!.size
        Assertions.assertThat(sizeBeforeDeletion).isGreaterThan(sizeAfterDeletion)
    }
}
