package ua.lviv.iot.parkingServer.datastorage

import org.springframework.stereotype.Component
import ua.lviv.iot.parkingServer.model.Vehicle
import ua.lviv.iot.parkingServer.model.enums.VehicleType
import java.time.Duration

@Component
class VehicleFileStore : AbstractFileStore<Vehicle>() {
    override fun getRecordName(): String = "vehicle"

    override fun convert(values: List<String>): Vehicle {
        return Vehicle(
            id = values[0].toLong(),
            number = values[1],
            typeOfVehicle = VehicleType.valueOf(values[2]),
            durationOfUseOfParkingSpot = Duration.ofHours(values[3].toLong()),
            isTicketReceived = values[4].toBoolean()
        )
    }
}