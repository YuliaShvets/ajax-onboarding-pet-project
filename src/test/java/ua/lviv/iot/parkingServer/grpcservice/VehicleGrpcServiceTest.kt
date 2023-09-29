package ua.lviv.iot.parkingServer.grpcservice

import com.example.VehicleGrpcServiceGrpc
import com.example.VehicleGrpcServiceGrpc.VehicleGrpcServiceBlockingStub
import com.example.VehicleOuterClass
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.time.Duration
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
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

    private lateinit var stub: VehicleGrpcServiceBlockingStub

    private lateinit var channel: ManagedChannel

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
        val vehicle = Vehicle(
            number = "KA6706VN",
            typeOfVehicle = VehicleType.BUS,
            durationOfUseOfParkingSpot = Duration.ofSeconds(3600),
            isTicketReceived = true
        )

        val vehicleRequest = VehicleOuterClass.VehicleRequest.newBuilder()
            .setVehicle(vehicleConverter.vehicleToProto(vehicle))
            .build()

        val response = stub.createVehicle(vehicleRequest)

        assertEquals("KA6706VN", response.vehicle.number)
        assertEquals(VehicleOuterClass.VehicleType.BUS, response.vehicle.typeOfVehicle)
        assertEquals(3600, response.vehicle.durationOfUseOfParkingSpot.seconds)
        assertEquals(true, response.vehicle.isTicketReceived)
    }

    @Test
    fun getVehicleById() {
        val vehicle = Vehicle(
            number = "KA6706VN",
            typeOfVehicle = VehicleType.BUS,
            durationOfUseOfParkingSpot = Duration.ofSeconds(3600),
            isTicketReceived = true
        )
        vehicleRepository.save(vehicle).block()
        val request = VehicleOuterClass.GetByIdVehicleRequest.newBuilder()
            .setVehicleId(vehicle.id)
            .build()
        val response = stub.getVehicleById(request)
        assertEquals("KA6706VN", response.vehicle.number)
        vehicleRepository.deleteById(vehicle.id).block()
    }

    @Test
    fun updateVehicle() {
        val updatedVehicle = Vehicle(
            number = "KA6706VW",
            typeOfVehicle = VehicleType.BUS,
            durationOfUseOfParkingSpot = Duration.ofSeconds(3600),
            isTicketReceived = true
        )
        vehicleRepository.save(updatedVehicle).block()
        val request = VehicleOuterClass.UpdateVehicleRequest.newBuilder()
            .setVehicleId(updatedVehicle.id)
            .setVehicle(vehicleConverter.vehicleToProto(updatedVehicle))
            .build()

        val response = stub.updateVehicle(request)
        assertEquals("KA6706VW", response.vehicle.number)
        vehicleRepository.deleteById(updatedVehicle.id).block()
    }

    @Test
    fun deleteVehicle() {
        val vehicle = Vehicle(
            number = "KA6706VW",
            typeOfVehicle = VehicleType.BUS,
            durationOfUseOfParkingSpot = Duration.ofSeconds(3600),
            isTicketReceived = true
        )
        vehicleRepository.save(vehicle).block()
        val sizeBeforeDeletion = vehicleRepository.findAll().collectList().block()!!.size
        val request = VehicleOuterClass.DeleteVehicleRequest.newBuilder()
            .setVehicleId(vehicle.id)
            .build()

        stub.deleteVehicle(request)
        val sizeAfterDeletion = vehicleRepository.findAll().collectList().block()!!.size
        Assertions.assertThat(sizeBeforeDeletion).isGreaterThan(sizeAfterDeletion)
    }
}
