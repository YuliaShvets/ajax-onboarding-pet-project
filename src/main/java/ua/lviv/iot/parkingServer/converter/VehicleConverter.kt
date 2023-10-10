package ua.lviv.iot.parkingServer.converter

import java.time.Duration
import org.springframework.stereotype.Component
import ua.lviv.iot.VehicleOuterClass
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.model.enums.VehicleType

@Component
class VehicleConverter {
    fun vehicleToProto(vehicle: Vehicle): VehicleOuterClass.Vehicle {
        return VehicleOuterClass.Vehicle.newBuilder().apply {
            number = vehicle.number
            typeOfVehicle = when (vehicle.typeOfVehicle) {
                VehicleType.CAR -> VehicleOuterClass.VehicleType.CAR
                VehicleType.BUS -> VehicleOuterClass.VehicleType.BUS
                VehicleType.MOTORCYCLE -> VehicleOuterClass.VehicleType.MOTORCYCLE
                else -> VehicleOuterClass.VehicleType.UNKNOWN
            }

            durationOfUseOfParkingSpotBuilder.setSeconds(vehicle.durationOfUseOfParkingSpot.seconds)
            isTicketReceived = vehicle.isTicketReceived
        }.build()
    }

    fun protoToVehicle(vehicleProto: VehicleOuterClass.Vehicle): Vehicle {
        return Vehicle(
            number = vehicleProto.number,
            typeOfVehicle = when (vehicleProto.typeOfVehicle) {
                VehicleOuterClass.VehicleType.CAR -> VehicleType.CAR
                VehicleOuterClass.VehicleType.BUS -> VehicleType.BUS
                VehicleOuterClass.VehicleType.MOTORCYCLE -> VehicleType.MOTORCYCLE
                else -> VehicleType.UNKNOWN
            },
            durationOfUseOfParkingSpot = Duration.ofSeconds(vehicleProto.durationOfUseOfParkingSpot.seconds),
            isTicketReceived = vehicleProto.isTicketReceived
        )
    }
}
