package ua.lviv.iot.parkingServer.converter

import com.example.VehicleOuterClass
import java.time.Duration
import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.model.enums.VehicleType

@Component
class VehicleConverter {
    fun vehicleToProto(vehicle: Vehicle): VehicleOuterClass.Vehicle {
        return VehicleOuterClass.Vehicle.newBuilder()
            .setNumber(vehicle.number)
            .setTypeOfVehicle(
                when (vehicle.typeOfVehicle) {
                    VehicleType.CAR -> VehicleOuterClass.VehicleType.CAR
                    VehicleType.BUS -> VehicleOuterClass.VehicleType.BUS
                    VehicleType.MOTORCYCLE -> VehicleOuterClass.VehicleType.MOTORCYCLE
                }
            )
            .setDurationOfUseOfParkingSpot(
                com.google.protobuf.Duration.newBuilder()
                    .setSeconds(vehicle.durationOfUseOfParkingSpot.seconds)
                    .build()
            )
            .setIsTicketReceived(vehicle.isTicketReceived)
            .build()
    }

    fun protoToVehicle(vehicleProto: VehicleOuterClass.Vehicle): Vehicle {
        return Vehicle(
            number = vehicleProto.number,
            typeOfVehicle = when (vehicleProto.typeOfVehicle) {
                VehicleOuterClass.VehicleType.CAR -> VehicleType.CAR
                VehicleOuterClass.VehicleType.BUS -> VehicleType.BUS
                VehicleOuterClass.VehicleType.MOTORCYCLE -> VehicleType.MOTORCYCLE
                else -> VehicleType.CAR
            },
            durationOfUseOfParkingSpot = Duration.ofSeconds(vehicleProto.durationOfUseOfParkingSpot.seconds),
            isTicketReceived = vehicleProto.isTicketReceived
        )
    }
}
